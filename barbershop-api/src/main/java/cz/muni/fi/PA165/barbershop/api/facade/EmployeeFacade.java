package cz.muni.fi.PA165.barbershop.api.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.TimeFrameDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeFacade extends PersonFacade<EmployeeDTO>{
    List<EmployeeDTO> getAllEmployeesForService(Long serviceId);
    List<TimeFrameDTO> getFreeTimeInPeriod(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO);
    List<EmployeeDTO> findByName(String firstname, String lastname);
}
