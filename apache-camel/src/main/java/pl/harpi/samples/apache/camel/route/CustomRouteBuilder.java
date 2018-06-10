package pl.harpi.samples.apache.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import pl.harpi.samples.apache.camel.bean.CalculationMessageRouter;
import pl.harpi.samples.apache.camel.bean.CalculationServiceHandler;

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
        from("direct:choice-rest")
                .choice()
                .when().method(CalculationMessageRouter.class).to("direct:calculate-rest-a")
                .otherwise().to("direct:calculate-rest-b")
                .end();

        from("direct:calculate-rest")
                .split().method("rootSplitter", "split")
                .bean(CalculationServiceHandler.class, "calculate")
                .to("direct:calculate-rest-resp")
                .end();

        from("direct:calculate-rest-resp")
                .log("Processing ${id}");

        from("direct:calculate-rest-a")
                .log("Processing ${id}");

        from("direct:calculate-rest-b")
                .log("Processing ${id}");
    }
}
