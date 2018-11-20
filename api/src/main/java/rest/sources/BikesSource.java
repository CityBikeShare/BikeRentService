package rest.sources;

import beans.BikesBean;
import core.Bikes;

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
@Path("bikes")
@ApplicationScoped
public class BikesSource {
    @Inject
    private BikesBean bikesBean;

    @GET
    public Response getAllArtists() {
        List<Bikes> bikes = bikesBean.getBikes();

        return bikes == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(bikes).build();
    }
}
