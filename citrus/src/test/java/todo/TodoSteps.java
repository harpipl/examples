package todo;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.message.MessageType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TodoSteps {

    /** Test runner filled with actions by step definitions */
    @CitrusResource
    private TestRunner runner;

    @Given("^Todo list is empty$")
    public void empty_todos() {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .send()
                .delete("/api/todolist"));

        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .receive()
                .response(HttpStatus.OK));
    }

    @When("^(?:I|user) adds? entry \"([^\"]*)\"$")
    public void add_entry(String todoName) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .send()
                .post("/todolist")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .payload("title=" + todoName));

        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .receive()
                .response(HttpStatus.FOUND));
    }

    @When("^(?:I|user) removes? entry \"([^\"]*)\"$")
    public void remove_entry(String todoName) throws UnsupportedEncodingException {
        String encoding = URLEncoder.encode(todoName, "UTF-8");
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .send()
                .delete("/api/todo?title=" + encoding));

        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.PLAINTEXT));
    }

    @Then("^(?:the )?number of todo entries should be (\\d+)$")
    public void verify_todos(int todoCnt) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .send()
                .get("/api/todolist/count"));

        runner.http(httpActionBuilder -> httpActionBuilder
                .client("todoListClient")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.PLAINTEXT)
                .payload(String.valueOf(todoCnt)));
    }

    @Then("^(?:the )?todo list should be empty$")
    public void verify_empty_todos() {
        verify_todos(0);
    }

}
