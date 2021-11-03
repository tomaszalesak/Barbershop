package cz.muni.fi.PA165.barbershop.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

/**
 * @author Konstantin Yarovoy
 */
public class ReservationDTO {
    private long id;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private CustomerDTO customerDTO;

    private EmployeeDTO employeeDTO;

    private List<ServiceDTO> serviceDTOs;

    private boolean done;

    private String feedbackText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public List<ServiceDTO> getServiceDTOs() {
        return serviceDTOs;
    }

    public void setServiceDTOs(List<ServiceDTO> serviceDTOs) {
        this.serviceDTOs = serviceDTOs;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;
        ReservationDTO that = (ReservationDTO) o;
        return getFromTime().equals(that.getFromTime()) &&
                getToTime().equals(that.getToTime()) &&
                getCustomerDTO().equals(that.getCustomerDTO()) &&
                getEmployeeDTO().equals(that.getEmployeeDTO()) &&
                getServiceDTOs().equals(that.getServiceDTOs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime(), getCustomerDTO(), getEmployeeDTO(), getServiceDTOs());
    }
}
