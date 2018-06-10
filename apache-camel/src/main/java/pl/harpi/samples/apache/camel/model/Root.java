package pl.harpi.samples.apache.camel.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Root {
    private int requestCapacity;
    private Product products[];
}
