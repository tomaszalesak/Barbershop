package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.ReservationDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Konstantin Yarovoy
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationDao reservationDao;

    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation findByID(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public void createReservation(Reservation reservation) {
        reservationDao.create(reservation);
    }

    @Override
    public void setReservationCompletion(Long reservationId, boolean status) {
        var reservation = findByID(reservationId);
        reservation.setDone(status);
        reservationDao.update(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        reservationDao.delete(reservationId);
    }

    @Override
    public void addFeedback(Long reservationId, String feedback) {
        var reservation = reservationDao.findById(reservationId);
        reservation.setFeedbackText(feedback);
        reservationDao.update(reservation);
    }

    @Override
    public List<Reservation> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee) {
        return reservationDao.getReservationsInPeriodForEmployee(from, to, employee);
    }
}
