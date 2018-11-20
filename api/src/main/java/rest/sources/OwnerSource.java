package rest.sources;

import beans.OwnerBean;
import core.Owner;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

    @GET
    public Response getAllArtists() {
        List<Owner> owners = ownerBean.getOwners();

        return owners == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(owners).build();
    }
}
