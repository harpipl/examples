package pl.harpi.samples.redisson.lock.rest;

import pl.harpi.samples.redisson.lock.ejb.ExampleServiceBean;
import pl.harpi.samples.redisson.lock.ejb.ExampleServiceConstants;
import pl.harpi.samples.redisson.lock.userexit.UserExitAttribute;
import pl.harpi.samples.redisson.lock.userexit.UserExitRequest;
import pl.harpi.samples.redisson.lock.userexit.UserExitResponse;

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
public class RedissonResource {
    @Inject
    private ExampleServiceBean service;

    @POST
    @Path("/execute")
    public Response execute(RedissonRequest request) {
        UserExitRequest userExitRequest = new UserExitRequest();
        userExitRequest.addAttribute(new UserExitAttribute(ExampleServiceConstants.ATTR_KEY, request.getKey(), "STRING"));
        userExitRequest.addAttribute(new UserExitAttribute(ExampleServiceConstants.ATTR_CLIENT_ID, request.getValue(), "STRING"));

        UserExitResponse userExitResponse = service.invoke(userExitRequest);

        RedissonResponse response = new RedissonResponse();
        response.setKey(request.getKey());
        response.setValue(userExitResponse.getAttributeValueOrDefault(ExampleServiceConstants.ATTR_KEY, "UNKNOWN_KEY"));
        response.setValue(userExitResponse.getAttributeValueOrDefault(ExampleServiceConstants.ATTR_CLIENT_ID, "UNKNOWN_CLIENT_ID"));

        return Response.status(Response.Status.OK).entity(response).build();
    }
}
