package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DataAccessObject interface for Reservation Entity
 *
 * @author Konstantin Yarovoy
 */
public interface ReservationDao extends GenericBaseDao<Reservation> {
    List<Reservation> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee);
}

