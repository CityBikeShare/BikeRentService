package beans;

import core.Bikes;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class BikesBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    private List<Bikes> getBikes() {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getAll", Bikes.class);
        return query.getResultList();
    }

}
