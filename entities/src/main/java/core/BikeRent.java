package core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity(name = "BikeRent")
@NamedQueries({
        @NamedQuery(name = "BikeRent.getAll", query = "SELECT br from BikeRent br")
})
public class BikeRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bikerent_id;

    private boolean isDebtSettled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rentStart;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rentEnd;

    private Integer bike_id;        // Bike which is rented
    private Integer user_id;        // User who rents the bike


    public Integer getBikerent_id() {
        return bikerent_id;
    }

    public void setBikerent_id(Integer bikerent_id) {
        this.bikerent_id = bikerent_id;
    }

    public boolean isDebtSettled() {
        return isDebtSettled;
    }

    public void setDebtSettled(boolean debtSettled) {
        isDebtSettled = debtSettled;
    }

    public Date getRentStart() {
        return rentStart;
    }

    public void setRentStart(Date rentStart) {
        this.rentStart = rentStart;
    }

    public Date getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(Date rentEnd) {
        this.rentEnd = rentEnd;
    }

    public Integer getBike_id() {
        return bike_id;
    }

    public void setBike_id(Integer bike_id) {
        this.bike_id = bike_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
