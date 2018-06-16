package pl.harpi.samples.redisson.lock.service;

import org.redisson.Redisson;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@LocalBean
public class RedissonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonService.class);

    public String fetch(String key, String clientId) {
        RedissonClient redisson = createInstance();

        RLock lock = redisson.getLock(key);

        boolean locked = false;
        try {
            locked = lock.tryLock(5, 5, TimeUnit.SECONDS);

            LOGGER.info(String.format("record with key = %s was locked by client = %s", key, clientId));
        } catch (InterruptedException e) {
            LOGGER.error("exception: " + e.getMessage());
        }

        String result = null;
        if (locked) {
            RLiveObjectService service = redisson.getLiveObjectService();
            Message message = service.get(Message.class, key);

            if (message == null) {
                Message persisted = service.persist(new Message(key, clientId));

                service.asRExpirable(persisted).expire(10, TimeUnit.SECONDS);

                result = String.format("[key: %s, client: %s]", key, clientId);
                LOGGER.info(String.format("record with key = %s was stored by client = %s", key, clientId));
            } else {
                result = String.format("[key: %s, client: %s]", message.getKey(), message.getClientId());
                LOGGER.info(String.format("record with key = %s and client = %s was fetched by client = %s", key, message.getClientId(), clientId));
            }

            lock.unlock();

            LOGGER.info(String.format("record with key = %s was unlocked by client = %s", key, clientId));
        }

        redisson.shutdown();

        return result;
    }

    private RedissonClient createInstance() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://localhost:6379");

        return Redisson.create(config);
    }
}
