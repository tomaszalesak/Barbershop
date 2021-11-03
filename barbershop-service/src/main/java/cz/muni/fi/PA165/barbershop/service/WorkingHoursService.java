package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tomáš Zálešák
 */
public interface WorkingHoursService {
    /**
     * Creates workingHours passed as parameter.
     *
     * @param workingHours the workingHours to be created
     */
    void create(WorkingHours workingHours);

    /**
     * Finds workingHours with id passed as parameter.
     *
     * @param id the id of the returned workingHours
     * @return workingHours with id passed as parameter
     */
    WorkingHours findById(Long id);

    /**
     * Finds all workingHours.
     *
     * @return all workingHours
     */
    List<WorkingHours> findAll();

    /**
     * Updates already created workingHours, with the workingHours passed as parameter.
     *
     * @param workingHours the workingHours to be updated
     */
    void update(WorkingHours workingHours);

    /**
     * Deletes already created workingHours passed as parameter.
     *
     * @param workingHours the workingHours to be deleted
     */
    void delete(WorkingHours workingHours);

    /**
     * Get workingHours by employee and  from to time
     *
     * @param from time from when working hours
     * @param to time until when working hours
     * @param employee which employee
     */
    List<WorkingHours> getWorkingHoursInPeriodForEmployee(LocalDateTime from, LocalDateTime to, Employee employee);

}
