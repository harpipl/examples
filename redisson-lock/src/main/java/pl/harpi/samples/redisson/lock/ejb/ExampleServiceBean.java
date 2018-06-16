package pl.harpi.samples.redisson.lock.ejb;

import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.harpi.samples.redisson.lock.userexit.UserExitAttribute;
import pl.harpi.samples.redisson.lock.userexit.UserExitHandler;
import pl.harpi.samples.redisson.lock.userexit.UserExitRequest;
import pl.harpi.samples.redisson.lock.userexit.UserExitResponse;

import javax.ejb.LocalBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@LocalBean
public class ExampleServiceBean implements UserExitHandler<UserExitRequest, UserExitResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleServiceBean.class);

    @Inject
    private RedissonStartupBean redissonStartup;

    private String getKey(Map<String, UserExitAttribute> attributes) {
        return String.valueOf(Objects.hash(attributes));
    }

    @Override
    public UserExitResponse invoke(UserExitRequest request) {
        RedissonClient redisson = redissonStartup.getRedisson();

        String clientId = request.getAttributeValueOrDefault(ExampleServiceConstants.ATTR_CLIENT_ID, "UNKNOWN_CLIENT_ID");
        String key = getKey(request.getAttributes());

        RLock lock = redisson.getLock(key);

        boolean locked = false;
        try {
            locked = lock.tryLock(5, 5, TimeUnit.SECONDS);

            // LOGGER.info(String.format("record with key = %s was locked by client = %s", key, clientId));
        } catch (InterruptedException e) {
            LOGGER.error("exception: " + e.getMessage());
        }

        UserExitResponse response = null;
        if (locked) {
            RLiveObjectService service = redisson.getLiveObjectService();
            RedisEntity message = service.get(RedisEntity.class, key);

            if (message == null) {
                RedisEntity persisted = service.persist(new RedisEntity(key, clientId));

                service.asRExpirable(persisted).expire(10, TimeUnit.SECONDS);

                response = new UserExitResponse();
                response.getAttributes().put(ExampleServiceConstants.ATTR_KEY, new UserExitAttribute(ExampleServiceConstants.ATTR_KEY, key, "STRING"));
                response.getAttributes().put(ExampleServiceConstants.ATTR_CLIENT_ID, new UserExitAttribute(ExampleServiceConstants.ATTR_CLIENT_ID, clientId, "STRING"));

                LOGGER.info(String.format("record with key = %s was stored by client = %s", key, clientId));
            } else {
                response = new UserExitResponse();
                response.getAttributes().put(ExampleServiceConstants.ATTR_KEY, new UserExitAttribute(ExampleServiceConstants.ATTR_KEY, message.getKey(), "STRING"));
                response.getAttributes().put(ExampleServiceConstants.ATTR_CLIENT_ID, new UserExitAttribute(ExampleServiceConstants.ATTR_CLIENT_ID, message.getClientId(), "STRING"));

                // LOGGER.info(String.format("record with key = %s and client = %s was fetched by client = %s", key, message.getClientId(), clientId));
            }

            lock.unlock();

            // LOGGER.info(String.format("record with key = %s was unlocked by client = %s", key, clientId));
        }

        return response;
    }
}
