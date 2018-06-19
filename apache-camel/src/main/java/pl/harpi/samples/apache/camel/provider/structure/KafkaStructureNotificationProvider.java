package pl.harpi.samples.apache.camel.provider.structure;

import pl.harpi.samples.apache.camel.provider.NotificationProvider;

import javax.inject.Named;

@Named("KafkaStructureNotificationProvider")
public class KafkaStructureNotificationProvider extends AbstractStructureNotificationProvider implements NotificationProvider {
}
