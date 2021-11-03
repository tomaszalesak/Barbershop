package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WorkingHours DAO Interface
 *
 * @author Tomáš Zálešák
 */
public interface WorkingHoursDao extends GenericBaseDao<WorkingHours> {
    List<WorkingHours> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee);

}

