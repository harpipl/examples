package pl.harpi.samples.redisson.lock.ejb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisEntity {
    @RId
    private String key;
    private String clientId;
}