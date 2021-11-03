package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: Jan Sladký
 */
@Repository
public class EmployeeDaoImpl extends PersonDaoImpl<Employee> implements EmployeeDao {

    public EmployeeDaoImpl() {
        super(Employee.class);
    }

    @Override
    public Employee findByLogin(String login) {
        return em.createQuery("SELECT e FROM Employee e WHERE e.login = :login", Employee.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    @Override
    public List<Employee> findByName(String firstname, String lastname) {
        return em.createQuery("SELECT e FROM Employee e WHERE e.firstname = :firstname AND e.lastname = :lastname", Employee.class)
                .setParameter("firstname", firstname).setParameter("lastname", lastname)
                .getResultList();
    }
}
