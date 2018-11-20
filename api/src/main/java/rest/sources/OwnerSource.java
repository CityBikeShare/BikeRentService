package rest.sources;

import beans.OwnerBean;
import core.Bikes;
import core.Owner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("owner")
@ApplicationScoped
public class OwnerSource {
    @Inject
    private OwnerBean ownerBean;

    @Operation(
            description = "Get all owners",
            tags = "owners",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Owners",
                            content = @Content(schema = @Schema(implementation = Owner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No owners available",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response getAllOwners() {
        List<Owner> owners = ownerBean.getOwners();

        return owners == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(owners).build();
    }

    @Path("{id}")
    @GET
    public Response getOwnerById(@PathParam("id") int id) {
        Owner owner = ownerBean.getOwnerById(id);
        return owner == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(owner).build();
    }
}
