package rest.sources;

import beans.CustomerBean;
import core.Customer;

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
@Path("customer")
@ApplicationScoped
public class CustomerSource {
    @Inject
    private CustomerBean customerBean;

    @GET
    public Response getAllArtists() {
        List<Customer> customers = customerBean.getCustomers();

        return customers == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(customers).build();
    }
}
