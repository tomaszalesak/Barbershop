package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WorkingHoursDaoImpl extends GenericBaseDaoImpl<WorkingHours> implements WorkingHoursDao {

    public WorkingHoursDaoImpl() {
        super(WorkingHours.class);
    }

    public List<WorkingHours> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee){
        Query query = em.createQuery("SELECT w FROM WorkingHours w JOIN  w.employee e WHERE (e.id = :employee_id) AND " +
                "((w.fromTime >= :from AND w.toTime <= :to) OR (w.fromTime >= :from AND w.fromTime <= :to) OR (w.toTime >= :from AND w.toTime <= :to))", WorkingHours.class);
        query.setParameter("employee_id", employee.getId()).setParameter("from", from).setParameter("to",to);
        return query.getResultList();
    }
}
