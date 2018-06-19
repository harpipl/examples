package pl.harpi.samples.apache.camel.provider.structure;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.harpi.samples.apache.camel.model.Version;
import pl.harpi.samples.apache.camel.provider.NotificationProvider;

public abstract class AbstractStructureNotificationProvider implements NotificationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStructureNotificationProvider.class);

    @Override
    public void sendNotification(Exchange exchange) {
        Version version = exchange.getIn().getBody(Version.class);

        LOGGER.info(String.format("HELLO FROM STRUCTURE NOTIFICATION %s (productId=%s, versionId=%s) !!!", this.getClass().getCanonicalName(), version.getProductId(), version.getVersionId()));
    }
}
