package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;

import java.util.List;

/**
 * An interface that defines a service access to the Employee entity.
 *
 * @author Jan Sladký
 */
public interface EmployeeService extends PersonService<Employee> {
    List<Employee> findByName(String firstname, String lastname);
}
