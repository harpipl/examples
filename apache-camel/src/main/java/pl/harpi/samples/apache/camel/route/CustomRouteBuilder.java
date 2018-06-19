package pl.harpi.samples.apache.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.properties.PropertiesComponent;
import pl.harpi.samples.apache.camel.bean.CalculationMessageRouter;
import pl.harpi.samples.apache.camel.bean.CalculationServiceHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@ContextName("camel-context")
public class CustomRouteBuilder extends RouteBuilder {

    @Inject
    RootSplitter rootSplitter;

    @Override
    public void configure() throws Exception {
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("classpath:application.properties");
        getContext().addComponent("properties", pc);

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

        from("direct:structure-notify-versions")
                .split(body())
                .parallelProcessing()
                .to("direct:structure-notify-single-version");

        from("direct:structure-notify-single-version")
                .to("bean:{{structure.notification.provider}}");
    }
}
