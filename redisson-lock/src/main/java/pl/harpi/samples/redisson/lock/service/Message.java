package pl.harpi.samples.redisson.lock.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @RId
    private String key;
    private String clientId;
}