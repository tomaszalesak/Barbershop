package cz.muni.fi.PA165.barbershop.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service entity
 *
 * @author Jakub Zich
 */
@Entity
public class MyService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private BigDecimal price;

    @Column()
    @ManyToMany(mappedBy = "services")
    private List<Reservation> reservations = new ArrayList<>();

    @Column()
    @ManyToMany(mappedBy = "services")
    private List<Employee> employees = new ArrayList<>();

    public MyService() {
    }

    public MyService(String name, Integer durationMinutes, BigDecimal price) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.removeService(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.removeService(this);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name +
                " price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyService)) return false;

        MyService service = (MyService) o;

        if (getName() != null ? !getName().equals(service.getName()) : service.getName() != null) return false;
        if (getDurationMinutes() != null ? !getDurationMinutes().equals(service.getDurationMinutes()) : service.getDurationMinutes() != null)
            return false;
        return getPrice() != null ? getPrice().equals(service.getPrice()) : service.getPrice() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDurationMinutes(), getPrice());
    }
}
