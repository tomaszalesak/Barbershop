package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.WorkingHoursDTO;
import cz.muni.fi.PA165.barbershop.api.facade.WorkingHoursFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.EmployeeService;
import cz.muni.fi.PA165.barbershop.service.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tomáš Zálešák
 */
@Service
@Transactional
public class WorkingHoursFacadeImpl implements WorkingHoursFacade {

    private WorkingHoursService workingHoursService;
    private EmployeeService employeeService;
    private BeanMappingService beanMappingService;

    @Autowired
    public WorkingHoursFacadeImpl (WorkingHoursService workingHoursService, EmployeeService employeeService, BeanMappingService beanMappingService) {
        this.workingHoursService = workingHoursService;
        this.employeeService = employeeService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long create(WorkingHoursDTO workingHoursDTO) {
        WorkingHours workingHours = beanMappingService.mapTo(workingHoursDTO, WorkingHours.class);
        workingHoursService.create(workingHours);
        return workingHours.getId();
    }

    @Override
    public WorkingHoursDTO findById(Long id) {
        WorkingHours workingHours = workingHoursService.findById(id);
        return (workingHours == null) ? null : beanMappingService.mapTo(workingHours, WorkingHoursDTO.class);
    }

    @Override
    public List<WorkingHoursDTO> findAll() {
        return beanMappingService.mapTo(workingHoursService.findAll(), WorkingHoursDTO.class);
    }

    @Override
    public void update(@NotNull WorkingHoursDTO workingHoursDTO) {
        workingHoursService.update((beanMappingService.mapTo(workingHoursDTO, WorkingHours.class)));
    }

    @Override
    public void delete(@NotNull WorkingHoursDTO workingHoursDTO) {
        workingHoursService.delete((beanMappingService.mapTo(workingHoursDTO, WorkingHours.class)));
    }

    @Override
    public List<WorkingHoursDTO> getWorkingHoursForEmployee(Long employeeId) {
        var employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("cannot find employee with id " + employeeId);
        }
        return beanMappingService.mapTo(employee.getWorkingHours(), WorkingHoursDTO.class);
    }

    @Override
    public List<WorkingHoursDTO> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO) {
       List<WorkingHours> wh = workingHoursService.getWorkingHoursInPeriodForEmployee(from, to, beanMappingService.mapTo(employeeDTO, Employee.class));
        return beanMappingService.mapTo(wh, WorkingHoursDTO.class);
    }
}
