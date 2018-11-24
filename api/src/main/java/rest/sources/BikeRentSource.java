package rest.sources;

import beans.BikeRentBean;
import core.BikeRent;
import core.Bikes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("bikerent")
@ApplicationScoped
public class BikeRentSource {
    @Inject
    private BikeRentBean bikeRentBean;

    @Operation(
            description = "Get all bike rent",
            tags = "bike rent",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All bike rent",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No bike available to rent",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response getAllBikeRent() {
        List<BikeRent> bikeRents = bikeRentBean.getAllBikeRent();

        return bikeRents == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(bikeRents).build();
    }

    @Operation(
            description = "Get bike rent by id",
            tags = "bike rent",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bike rent by id",
                            content = @Content(schema = @Schema(implementation = BikeRent.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No bike to rent available with this id",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    @Path("{id}")
    public Response getAllBikes(@PathParam("id") int bikeRentId) {
        BikeRent bikeRent = bikeRentBean.getBikeRentById(bikeRentId);
        return bikeRent == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(bikeRent).build();

    }

    @Operation(
            description = "Rent a bike",
            tags = "bike rent",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rent succeeded",
                            content = @Content(schema = @Schema(implementation = BikeRent.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Rent failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PUT
    public Response rentBike(@RequestBody BikeRent bikeRent) {
        if (bikeRentBean.getBikeRentById(bikeRent.getBike_id()) == null || bikeRentBean.getBikeRentById(bikeRent.getUser_id()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        bikeRentBean.updateBikeDebt(bikeRent.getBike_id(), bikeRent.isDebtSettled());
        bikeRentBean.insertBikeRent(bikeRent);

        return Response.ok(bikeRent).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBikeRent(@PathParam("id") int bikeRentId) {
        boolean status = bikeRentBean.deleteBikeRent(bikeRentId);

        return status ? Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
