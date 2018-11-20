package beans;

import core.Bikes;
import core.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class CustomerBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<Customer> getCustomers() {
        TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.getAll", Customer.class);
        return query.getResultList();
    }

    public Customer getCustomerById(int customerId){
        return entityManager.find(Customer.class, customerId);
    }
}