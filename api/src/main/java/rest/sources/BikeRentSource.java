package rest.sources;

import beans.core.BikeRentBean;
import beans.external.BikesBean;
import com.kumuluz.ee.logs.cdi.Log;
import core.BikeRent;
import external.Bikes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("bikerent")
@Log
@ApplicationScoped
public class BikeRentSource {
    @Inject
    private BikeRentBean bikeRentBean;

    @Inject
    private BikesBean bikeBean;

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
    public Response getBikeRentById(@PathParam("id") int bikeRentId) {
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
    @POST
    @Path("rent")
    public Response rentBike(@QueryParam("bikeid") int bikeId, @QueryParam("userid") int userId) {
        if(!bikeBean.isBikeAvailable(bikeId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        BikeRent bikeRent = bikeRentBean.newRent(bikeId, userId);

        Bikes bike = bikeBean.getBikeById(bikeId);
        bike.setAvailable(false);
        bikeBean.updateBike(bike);

        return Response.ok(bikeRent).build();
    }

    @PUT
    @Path("return/{bikeid}")
    public Response returnBike(@PathParam("bikeid") int bikeId){
        Bikes bike = bikeBean.getBikeById(bikeId);
        bike.setAvailable(true);
        bikeBean.updateBike(bike);
        return Response.ok(bikeRentBean.returnBike(bikeId)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBikeRent(@PathParam("id") int bikeRentId) {
        boolean status = bikeRentBean.deleteBikeRent(bikeRentId);

        return status ? Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
