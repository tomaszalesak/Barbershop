package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;

import java.util.List;

/**
 * Author: Jan Sladký
 */
public interface EmployeeDao extends PersonDao<Employee> {

    List<Employee> findByName(String firstname, String lastname);
}
