package pl.harpi.samples.apache.camel.rest;

import lombok.Data;
import pl.harpi.samples.apache.camel.model.Root;

@Data
public class CalculationRequest {
    private Root root;
}
