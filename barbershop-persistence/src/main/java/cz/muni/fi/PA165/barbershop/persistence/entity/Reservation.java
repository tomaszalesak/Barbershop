package cz.muni.fi.PA165.barbershop.persistence.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Reservation entity
 *
 * @author Konstantin Yarovoy
 */
@Entity
public class  Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private boolean done;

    @Column
    private String feedbackText;

    @Column(nullable = false)
    private LocalDateTime fromTime;

    @Column(nullable = false)
    private LocalDateTime toTime;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToOne
    @NotNull
    private Employee employee;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<MyService> services;

    public Reservation() {
    }

    public Reservation(Customer customer, Employee employee, LocalDateTime fromTime, LocalDateTime toTime, List<MyService> services) {
        if (fromTime.isAfter(toTime)) {
            throw new IllegalArgumentException("fromTime is earlier than toTime");
        }
        this.done = false;
        this.feedbackText = null;
        this.customer = customer;
        this.employee = employee;
        this.services = new ArrayList<>(services);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime from) {
        this.fromTime = from;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime to) {
        this.toTime = to;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<MyService> getServices() {
        return services;
    }

    public void setServices(List<MyService> services) {
        this.services = new ArrayList<>(services);
    }

    public void removeService(MyService service) {
        services.remove(service);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return getFromTime().equals(that.getFromTime()) &&
                getToTime().equals(that.getToTime()) &&
                getCustomer().equals(that.getCustomer()) &&
                getEmployee().equals(that.getEmployee()) &&
                getServices().equals(that.getServices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime(), getCustomer(), getEmployee());
    }
}
