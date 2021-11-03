package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Konstantin Yarovoy
 */
public interface ReservationService {

    Reservation findByID(Long id);
    void createReservation(Reservation reservation);
    void setReservationCompletion(Long reservationId, boolean status);
    void cancelReservation(Long reservationId);
    void addFeedback(Long reservationId, String feedback);
    List<Reservation> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee);
}
