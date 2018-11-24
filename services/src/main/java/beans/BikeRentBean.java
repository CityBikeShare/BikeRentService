package beans;

import core.BikeRent;
import core.Bikes;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BikeRentBean {
    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<BikeRent> getAllBikeRent() {
        TypedQuery<BikeRent> query = entityManager.createNamedQuery("BikeRent.getAll", BikeRent.class);
        return query.getResultList();
    }

    public BikeRent getBikeRentById(int bikeRentId) {
        return entityManager.find(BikeRent.class, bikeRentId);
    }

    @Transactional
    public BikeRent insertBikeRent(BikeRent bikeRent) {
        entityManager.persist(bikeRent);
        entityManager.flush();
        return bikeRent;
    }

    @Transactional
    public Bikes updateBikeDebt(int bikeId, boolean state) {
        Bikes bike = entityManager.find(Bikes.class, bikeId);
        bike.setAvailable(state);
        return bike;
    }

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
