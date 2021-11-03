package cz.muni.fi.PA165.barbershop.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Tomáš Zálešák
 */
public class WorkingHoursDTO {

    private Long id;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private EmployeeDTO employeeDTO;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingHoursDTO)) return false;
        WorkingHoursDTO that = (WorkingHoursDTO) o;
        return Objects.equals(getFromTime(), that.getFromTime()) && Objects.equals(getToTime(), that.getToTime()) && Objects.equals(getEmployeeDTO(), that.getEmployeeDTO());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime(), getEmployeeDTO());
    }

    @Override
    public String toString() {
        return "Review{" +
                "fromTime=" + fromTime +
                "toTime=" + toTime +
                "employee" + employeeDTO +
                "}";
    }
}
