package cz.muni.fi.PA165.barbershop.api.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.WorkingHoursDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tomáš Zálešák
 */
public interface WorkingHoursFacade {

    Long create(WorkingHoursDTO workingHoursDTO);

    WorkingHoursDTO findById(Long id);

    List<WorkingHoursDTO> findAll();

    void update(WorkingHoursDTO workingHoursDTO);

    void delete(WorkingHoursDTO workingHoursDTO);

    List<WorkingHoursDTO> getWorkingHoursForEmployee(Long employeeId);

    List<WorkingHoursDTO> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO);
}
