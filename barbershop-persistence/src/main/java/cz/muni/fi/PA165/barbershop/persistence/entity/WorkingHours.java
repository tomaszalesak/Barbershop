package cz.muni.fi.PA165.barbershop.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * WorkingHours entity
 *
 * @author Tomáš Zálešák
 */
@Entity
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fromTime;

    @Column(nullable = false)
    private LocalDateTime toTime;

    @ManyToOne
    private Employee employee;

    public WorkingHours() {
    }

    public WorkingHours(LocalDateTime fromTime, LocalDateTime toTime, Employee employee) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingHours)) return false;

        WorkingHours workingHours = (WorkingHours) o;

        return (getFromTime().equals(workingHours.getFromTime()) && getToTime().equals(workingHours.getToTime()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime(), getEmployee());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
