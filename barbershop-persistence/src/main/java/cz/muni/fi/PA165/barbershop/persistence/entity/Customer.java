package cz.muni.fi.PA165.barbershop.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer entity
 *
 * @author Jan Sladký
 */
@Entity
public class Customer extends Person {

    private Role role = Role.CUSTOMER;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();


    public Customer() {
    }

    public Customer(String login, String password, String firstname, String lastname, String phoneNumber, String city, String street, String postalCode) {
        super(login, password, firstname, lastname, phoneNumber, city, street, postalCode);
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
