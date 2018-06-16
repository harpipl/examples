package pl.harpi.samples.redisson.lock.rest;

import lombok.Data;

@Data
public class RedissonRequest {
    private String key;
    private String value;
}
