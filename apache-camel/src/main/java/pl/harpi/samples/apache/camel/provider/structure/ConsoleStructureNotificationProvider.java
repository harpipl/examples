package pl.harpi.samples.apache.camel.provider.structure;

import pl.harpi.samples.apache.camel.provider.NotificationProvider;

import javax.inject.Named;

@Named("ConsoleStructureNotificationProvider")
public class ConsoleStructureNotificationProvider extends AbstractStructureNotificationProvider implements NotificationProvider {
}
