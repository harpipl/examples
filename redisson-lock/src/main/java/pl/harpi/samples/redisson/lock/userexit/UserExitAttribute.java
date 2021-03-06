package pl.harpi.samples.redisson.lock.userexit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExitAttribute {
    private String name;
    private String value;
    private String type;
}
