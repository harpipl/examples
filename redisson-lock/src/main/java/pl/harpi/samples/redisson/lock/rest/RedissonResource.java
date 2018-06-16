package pl.harpi.samples.redisson.lock.rest;

import pl.harpi.samples.redisson.lock.service.RedissonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/samples")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RedissonResource {
    @Inject
    private RedissonService service;

    @POST
    @Path("/fetch")
    public Response fetch(RedissonRequest request) {
        String result = service.fetch(request.getKey(), request.getValue());

        RedissonResponse response = new RedissonResponse();
        response.setMessage(result);

        return Response.status(Response.Status.OK).entity(response).build();
    }
}
