package pl.harpi.samples.apache.camel.route;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.harpi.samples.apache.camel.model.Root;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(CamelCdiRunner.class)
@Beans(
        classes = RootSplitter.class
)
public class CustomRouteTest {
    @Inject
    private CamelContext context;

    @Before
    public void setUpRoute() throws Exception {
        context.addRoutes(new CustomRouteBuilder());
    }

    @Test
    public void choiceRest(CamelContext context) {
        Root root = new Root();
        root.setRequestCapacity(1);

        ProducerTemplate producer = context.createProducerTemplate();
        String result = producer.requestBody("direct:choice-rest", root, String.class);

        assertThat(result, is(equalTo("Root(requestCapacity=1, products=null)")));
    }
}