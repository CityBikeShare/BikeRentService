package rest.sources;

import beans.CustomerBean;
import core.Bikes;
import core.Customer;
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
@Path("customer")
@ApplicationScoped
public class CustomerSource {
    @Inject
    private CustomerBean customerBean;

    @Operation(
            description = "Get all customers",
            tags = "customers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customers",
                            content = @Content(schema = @Schema(implementation = Customer.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No customers available",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response getAllCustomers() {
        List<Customer> customers = customerBean.getCustomers();

        return customers == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(customers).build();
    }

    @Path("{id}")
    @GET
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerBean.getCustomerById(id);
        return customer == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(customer).build();
    }
}
