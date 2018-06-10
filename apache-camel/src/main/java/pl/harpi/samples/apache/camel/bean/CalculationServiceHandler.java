package pl.harpi.samples.apache.camel.bean;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.harpi.samples.apache.camel.model.Root;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("calculationServiceHandler")
public class CalculationServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationServiceHandler.class);

    public void calculate(Exchange exchange) {
        Root root = exchange.getIn().getBody(Root.class);

        exchange.getOut().setBody(String.format("answer: %d", root.getRequestCapacity()));

        LOGGER.error("EXCHANGE_ID=" + exchange.getExchangeId());
    }
}
