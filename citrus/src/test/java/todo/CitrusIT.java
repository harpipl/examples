package todo;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        plugin = { "com.consol.citrus.cucumber.CitrusReporter" } )
public class CitrusIT {
}
