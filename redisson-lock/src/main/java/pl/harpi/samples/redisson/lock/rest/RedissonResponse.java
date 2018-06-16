package pl.harpi.samples.redisson.lock.rest;

import lombok.Data;

@Data
public class RedissonResponse {
    private String key;
    private String value;
}
