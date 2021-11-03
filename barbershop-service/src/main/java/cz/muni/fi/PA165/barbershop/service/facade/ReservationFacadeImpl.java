package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ReservationDTO;
import cz.muni.fi.PA165.barbershop.api.facade.ReservationFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import cz.muni.fi.PA165.barbershop.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Konstantin Yarovoy
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    private ReservationService reservationService;
    private ServiceService serviceService;
    private EmployeeService employeeService;
    private CustomerService customerService;
    private BeanMappingService beanMappingService;

    @Autowired
    public ReservationFacadeImpl (ReservationService reservationService, ServiceService serviceService, EmployeeService employeeService,
                                  CustomerService customerService, BeanMappingService beanMappingService) {
        this.reservationService = reservationService;
        this.beanMappingService = beanMappingService;
        this.serviceService = serviceService;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @Override
    public Long createReservation(ReservationDTO dto) {
        Reservation reservation = beanMappingService.mapTo(dto, Reservation.class);
        reservationService.createReservation(reservation);
        return reservation.getId();
    }

    @Override
    public ReservationDTO getReservationWithID(Long id) {
        var reservation = reservationService.findByID(id);
        return (reservation == null) ? null : beanMappingService.mapTo(reservation, ReservationDTO.class);
    }

    @Override
    public void addFeedback(ReservationDTO dto) {
        reservationService.addFeedback(dto.getId(), dto.getFeedbackText());
    }

    @Override
    public void setReservationCompletion(Long id, boolean status) {
        reservationService.setReservationCompletion(id, status);
    }

    @Override
    public List<ReservationDTO> getAllReservationsForEmployee(Long employeeId) {
        var employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("cannot find employee with id " + employeeId);
        }
        return beanMappingService.mapTo(employee.getReservations(), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservationsForCustomer(Long customerId) {
        var customer = customerService.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("cannot find customer with id " + customerId);
        }
        return beanMappingService.mapTo(customer.getReservations(), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservationsForService(Long serviceId) {
        var service = serviceService.findServiceById(serviceId);
        if (service == null) {
            throw new IllegalArgumentException("cannot find service with id " + serviceId);
        }
        return beanMappingService.mapTo(service.getReservations(), ReservationDTO.class);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        reservationService.cancelReservation(reservationId);
    }

    @Override
    public List<ReservationDTO> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO) {
        var res = reservationService.getReservationsInPeriodForEmployee(from, to, beanMappingService.mapTo(employeeDTO, Employee.class));
        return beanMappingService.mapTo(res, ReservationDTO.class);
    }
}
