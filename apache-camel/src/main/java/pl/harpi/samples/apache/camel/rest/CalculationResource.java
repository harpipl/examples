package pl.harpi.samples.apache.camel.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/samples")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculationResource {
    @Inject
    private CamelContext camelContext;

    @POST
    @Path("/calculate")
    public Response calculate(CalculationRequest request) {
        ProducerTemplate producer = camelContext.createProducerTemplate();
        String result = producer.requestBody("direct:calculate-rest", request.getRoot(), String.class);

        CalculationResponse response = new CalculationResponse();
        response.setMessage(result);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @POST
    @Path("/choice")
    public Response choice(CalculationRequest request) {
        ProducerTemplate producer = camelContext.createProducerTemplate();
        String result = producer.requestBody("direct:choice-rest", request.getRoot(), String.class);

        CalculationResponse response = new CalculationResponse();
        response.setMessage(result);

        return Response.status(Response.Status.OK).entity(response).build();
    }
}