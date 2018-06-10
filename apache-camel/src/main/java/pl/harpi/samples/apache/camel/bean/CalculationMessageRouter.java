package pl.harpi.samples.apache.camel.bean;

import pl.harpi.samples.apache.camel.model.Root;

public class CalculationMessageRouter {
    public boolean resolve(Root root) {
        return root.getRequestCapacity() == 1;
    }
}
