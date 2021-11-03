package cz.muni.fi.PA165.barbershop.api.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ReservationDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Konstantin Yarovoy
 */
public interface ReservationFacade {
    Long createReservation(ReservationDTO dto);
    ReservationDTO getReservationWithID(Long id);
    void setReservationCompletion(Long id, boolean status);
    List<ReservationDTO> getAllReservationsForEmployee(Long employeeId);
    List<ReservationDTO> getAllReservationsForCustomer(Long customerId);
    List<ReservationDTO> getAllReservationsForService(Long serviceId);
    void cancelReservation(Long reservationId);
    void addFeedback(ReservationDTO dto);
    List<ReservationDTO> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, EmployeeDTO employeeDTO);
}
