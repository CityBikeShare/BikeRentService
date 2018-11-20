package beans;

import core.Owner;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class OwnerBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<Owner> getOwners() {
        TypedQuery<Owner> query = entityManager.createNamedQuery("Owner.getAll", Owner.class);
        return query.getResultList();
    }

    public Owner getOwnerById(int ownerId){
        return entityManager.find(Owner.class, ownerId);
    }

}