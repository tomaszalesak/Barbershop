package cz.muni.fi.PA165.barbershop.api.dto;

import cz.muni.fi.PA165.barbershop.persistence.entity.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * EmployeeDTO
 *
 * @author Jan Sladký
 */
public class EmployeeDTO extends PersonDTO {
    private Role role = Role.EMPLOYEE;
    private BigDecimal salary;
    private List<ServiceDTO> serviceDTOs = new ArrayList<>();

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<ServiceDTO> getServiceDTOs() {
        return serviceDTOs;
    }

    public void setServiceDTOs(List<ServiceDTO> serviceDTOs) {
        this.serviceDTOs = serviceDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDTO)) return false;
        if (!super.equals(o)) return false;

        EmployeeDTO that = (EmployeeDTO) o;

        return getSalary() != null ? getSalary().equals(that.getSalary()) : that.getSalary() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "salary=" + salary +
                "} " + super.toString();
    }
}
