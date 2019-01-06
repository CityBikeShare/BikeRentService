package beans.core;

import core.BikeRent;
import external.Bikes;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class BikeRentBean {
    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    @Metered(name = "getAllBikeRent")
    public List<BikeRent> getAllBikeRent() {
        TypedQuery<BikeRent> query = entityManager.createNamedQuery("BikeRent.getAll", BikeRent.class);
        return query.getResultList();
    }

    @Metered(name = "getBikeRentById")
    public BikeRent getBikeRentById(int bikeRentId) {
        return entityManager.find(BikeRent.class, bikeRentId);
    }

    @Metered(name = "insertBikeRent")
    @Transactional
    public BikeRent insertBikeRent(BikeRent bikeRent) {
        entityManager.persist(bikeRent);
        entityManager.flush();
        return bikeRent;
    }

    @Metered(name = "newRent")
    @Transactional
    public BikeRent newRent(int bikeId, int userId) {
        BikeRent bikeRent = new BikeRent();

        bikeRent.setBike_id(bikeId);
        bikeRent.setUser_id(userId);
        bikeRent.setDebtSettled(false);
        bikeRent.setRentStart(Date.from(Instant.now()));
        bikeRent.setRentEnd(null);

        entityManager.persist(bikeRent);
        entityManager.flush();

        return bikeRent;
    }

    @Metered(name = "returnBike")
    @Transactional
    public BikeRent returnBike(int bikeId) {
        BikeRent bikeRent = getBikeRentById(bikeId);
        bikeRent.setRentEnd(Date.from(Instant.now()));
        bikeRent = entityManager.merge(bikeRent);

        return bikeRent;
    }

    @Metered(name = "updateBikeDebt")
    @Transactional
    public Bikes updateBikeDebt(int bikeId, boolean state) {
        Bikes bike = entityManager.find(Bikes.class, bikeId);
        bike.setAvailable(state);
        return bike;
    }

    @Metered(name = "deleteBikeRent")
    @Transactional
    public boolean deleteBikeRent(int bikeRentId) {
        try {
            entityManager.remove(entityManager.find(BikeRent.class, bikeRentId));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
