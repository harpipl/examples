package pl.harpi.samples.apache.camel.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private Integer groupId;
    private String name;
}
