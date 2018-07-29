package pl.harpi.samples.mssql.lock.rest;

import pl.harpi.samples.mssql.lock.ejb.BusinessServiceBean;

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
public class MsSqlResource {
    @Inject
    private BusinessServiceBean service;

    @POST
    @Path("/execute")
    public Response execute(MsSqlRequest request) {
        MsSqlResponse response = new MsSqlResponse();

        Integer result = service.doSomething(request.getResource(), request.getTimeout(), request.getWorkTime());

        response.setResult(result);

        return Response.status(Response.Status.OK).entity(response).build();
    }
}
