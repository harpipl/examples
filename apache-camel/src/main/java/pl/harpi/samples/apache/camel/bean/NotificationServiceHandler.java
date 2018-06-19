package pl.harpi.samples.apache.camel.bean;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.harpi.samples.apache.camel.model.Root;
import pl.harpi.samples.apache.camel.model.Version;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("notificationServiceHandler")
public class NotificationServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceHandler.class);

    public void notifyVersion(Exchange exchange) {
        Version version = exchange.getIn().getBody(Version.class);

        LOGGER.info(String.format("PRODUCT_ID: %s, VERSION_ID: %s", version.getProductId(), version.getVersionId()));
    }
}
