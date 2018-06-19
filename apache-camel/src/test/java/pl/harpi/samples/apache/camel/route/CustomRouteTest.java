package pl.harpi.samples.apache.camel.route;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.harpi.samples.apache.camel.model.Root;
import pl.harpi.samples.apache.camel.model.Version;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(CamelCdiRunner.class)
public class CustomRouteTest {
    @Inject
    private CamelContext context;

    @Test
    public void choiceRest(CamelContext context) {
        Root root = new Root();
        root.setRequestCapacity(1);

        ProducerTemplate producer = context.createProducerTemplate();
        String result = producer.requestBody("direct:choice-rest", root, String.class);

        assertThat(result, is(equalTo("Root(requestCapacity=1, products=null)")));
    }

    @Test
    public void notifyVersions(CamelContext context) {
        List<Version> versions = new ArrayList<>();

        versions.add(new Version(1, 1));
        versions.add(new Version(1, 2));
        versions.add(new Version(1, 3));
        versions.add(new Version(1, 4));
        versions.add(new Version(1, 5));

        ProducerTemplate producer = context.createProducerTemplate();
        String result = producer.requestBody("direct:structure-notify-versions", versions, String.class);

        // assertThat(result, is(equalTo("Root(requestCapacity=1, products=null)")));
    }
}