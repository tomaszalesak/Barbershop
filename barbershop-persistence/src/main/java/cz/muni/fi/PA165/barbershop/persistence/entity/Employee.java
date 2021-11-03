package cz.muni.fi.PA165.barbershop.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Employee entity
 *
 * @author Jan Sladký
 */
@Entity
public class Employee extends Person {

    private Role role = Role.EMPLOYEE;

    private BigDecimal salary;

    @ManyToMany
    private List<MyService> services = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHours> workingHours = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public Employee() {
    }

    public Employee(String login, String password, String firstname, String lastname, String phoneNumber, String city, String street, String postalCode, BigDecimal salary) {
        super(login, password, firstname, lastname, phoneNumber, city, street, postalCode);
        this.salary = salary.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary.setScale(2, RoundingMode.HALF_UP);
    }

    public void setServices(List<MyService> services) {
        this.services = services;
    }

    public List<MyService> getServices() {
        return services;
    }

    public void removeService(MyService service) {
        services.remove(service);
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        return getSalary() != null ? getSalary().equals(employee.getSalary()) : employee.getSalary() == null;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSalary());
    }
}
