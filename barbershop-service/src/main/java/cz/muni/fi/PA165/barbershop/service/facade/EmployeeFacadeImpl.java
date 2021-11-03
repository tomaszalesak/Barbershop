package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.TimeFrameDTO;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.service.*;
import cz.muni.fi.PA165.barbershop.service.utils.TimeFrame;
import cz.muni.fi.PA165.barbershop.service.utils.TimeFrameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeFacadeImpl extends PersonFacadeImpl<Employee, EmployeeDTO> implements EmployeeFacade {

    ServiceService serviceService;
    ReservationService reservationService;
    WorkingHoursService workingHoursService;
    BeanMappingService beanMappingService;
    EmployeeService employeeService;

    @Autowired
    public EmployeeFacadeImpl(ServiceService serviceService, ReservationService reservationService, WorkingHoursService workingHoursService,
                              BeanMappingService beanMappingService, EmployeeService employeeService) {
        super(Employee.class, EmployeeDTO.class, employeeService, beanMappingService);
        this.serviceService = serviceService;
        this.reservationService = reservationService;
        this.beanMappingService = beanMappingService;
        this.employeeService = employeeService;
        this.workingHoursService = workingHoursService;
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesForService(Long serviceId) {
        var service = serviceService.findServiceById(serviceId);
        if (service == null) {
            throw new IllegalArgumentException("cannot find service with id " + serviceId);
        }
        return beanMappingService.mapTo(service.getEmployees(), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findByName(String firstname, String lastname) {
        return beanMappingService.mapTo(employeeService.findByName(firstname, lastname), EmployeeDTO.class);
    }

    public List<TimeFrameDTO> getFreeTimeInPeriod(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO) {
        var employee = beanMappingService.mapTo(employeeDTO, Employee.class);
        var workingHours = workingHoursService.getWorkingHoursInPeriodForEmployee(from, to, employee);
        var reservations = reservationService.getReservationsInPeriodForEmployee(from, to, employee);
        List<TimeFrame> whFrames = workingHours.stream().map(w -> TimeFrame.of(w.getFromTime(), w.getToTime())).collect(Collectors.toList());
        List<TimeFrame> resFrames = reservations.stream().map(r -> TimeFrame.of(r.getFromTime(), r.getToTime())).collect(Collectors.toList());
        var result = TimeFrameManager.joinTimeFrames(whFrames);
        result = TimeFrameManager.eraseTimeFromTimeFrames(result, resFrames);
        return beanMappingService.mapTo(result, TimeFrameDTO.class);
    }
}
