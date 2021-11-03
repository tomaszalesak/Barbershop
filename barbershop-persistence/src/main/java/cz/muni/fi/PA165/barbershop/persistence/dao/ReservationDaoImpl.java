package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of ReservationDao
 *
 * @author Konstantin Yarovoy
 */
@Repository
public class ReservationDaoImpl extends GenericBaseDaoImpl<Reservation> implements ReservationDao {

    public ReservationDaoImpl() {
        super(Reservation.class);
    }

    @Override
    public List<Reservation> getReservationsInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee){
        Query query = em.createQuery(
                "SELECT r FROM Reservation r JOIN  r.employee e WHERE (e.id = :employee_id) AND " +
                        "((r.fromTime >= :from AND r.toTime <= :to) OR (r.fromTime >= :from AND r.fromTime <= :to) OR (r.toTime >= :from AND r.toTime <= :to))", Reservation.class);
        query.setParameter("employee_id", employee.getId()).setParameter("from", from).setParameter("to",to);
        return query.getResultList();
    }
}
