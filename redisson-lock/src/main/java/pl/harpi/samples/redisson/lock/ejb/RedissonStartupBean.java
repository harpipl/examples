package pl.harpi.samples.redisson.lock.ejb;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class RedissonStartupBean {
    private RedissonClient redisson;

    @PostConstruct
    private void startup() {
        Config config = new Config();

        config.useSingleServer()
                .setSubscriptionConnectionPoolSize(410)
                .setAddress("redis://localhost:6379");

        this.redisson = Redisson.create(config);
    }

    @PreDestroy
    private void shutdown() {
        redisson.shutdown();
    }

    public RedissonClient getRedisson() {
        return redisson;
    }
}
