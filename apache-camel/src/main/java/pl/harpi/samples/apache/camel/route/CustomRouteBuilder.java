package pl.harpi.samples.apache.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import pl.harpi.samples.apache.camel.handler.CalculationServiceHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@ContextName("camel-context")
public class CustomRouteBuilder extends RouteBuilder {
    @Inject
    @Named("rootSplitter")
    RootSplitter rootSplitter;

    @Override
    public void configure() throws Exception {
        from("direct:calculate-rest")
                .split().method("rootSplitter", "split")
                .bean(CalculationServiceHandler.class, "calculate")
                .to("direct:calculate-rest-resp")
                .end();

        from("direct:calculate-rest-resp")
                .log("Processing ${id}");
    }
}
