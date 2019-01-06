package beans.external;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import external.Bikes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BikesBean {
    @Inject
    @DiscoverService(value = "bikecatalogservice", version = "1.0.0", environment = "dev")
    private Optional<WebTarget> bikeWebTarget;

    public List<Bikes> getBikes() {
        if (bikeWebTarget.isPresent()) {
            WebTarget t = bikeWebTarget.get();
            return t.path("sources/bikes").request().get(List.class);
        }
        return null;
    }

    public Bikes getBikeById(int bikeId) {
        if (bikeWebTarget.isPresent()) {
            WebTarget t = bikeWebTarget.get();
            return t.path("sources/bikes/bike/" + bikeId).request().get(Bikes.class);
        }
        return null;
    }

    public boolean isBikeAvailable(int bikeId){
        if(bikeWebTarget.isPresent()){
            return getBikeById(bikeId).isAvailable();
        }
        return false;
    }

    public Bikes updateBike(Bikes bike){
        int bikeId = bike.getBike_id();

        if (bikeWebTarget.isPresent()) {
            WebTarget t = bikeWebTarget.get();
            return t.path("sources/bikes/update/" + bikeId).request().put(Entity.entity(bike, MediaType.APPLICATION_JSON), Bikes.class);
        }
        return null;

    }

}
