package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.WorkingHoursDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tomáš Zálešák
 */
@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private WorkingHoursDao workingHoursDao;

    @Autowired
    public WorkingHoursServiceImpl(WorkingHoursDao workingHoursDao) {
        this.workingHoursDao = workingHoursDao;
    }

    @Override
    public void create(WorkingHours workingHours) {
        workingHoursDao.create(workingHours);
    }

    @Override
    public WorkingHours findById(Long id) {
        return workingHoursDao.findById(id);
    }

    @Override
    public List<WorkingHours> findAll() {
        return workingHoursDao.findAll();
    }

    @Override
    public void update(WorkingHours workingHours) {
        workingHoursDao.update(workingHours);
    }

    @Override
    public void delete(WorkingHours workingHours) {
        workingHoursDao.delete(workingHours.getId());
    }

    @Override
    public List<WorkingHours> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee) {
        return workingHoursDao.getWorkingHoursInPeriodForEmployee(from, to, employee);
    }
}
