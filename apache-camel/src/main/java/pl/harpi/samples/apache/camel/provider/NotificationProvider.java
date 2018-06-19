package pl.harpi.samples.apache.camel.provider;

import org.apache.camel.Exchange;

public interface NotificationProvider {
    void sendNotification(Exchange exchange);
}
