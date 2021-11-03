package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import org.springframework.stereotype.Repository;

/**
 * Author: Jan Sladký
 */
@Repository
public class CustomerDaoImpl extends PersonDaoImpl<Customer> implements CustomerDao {

    public CustomerDaoImpl() {
        super(Customer.class);
    }

    @Override
    public Customer findByLogin(String login) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.login = :login", Customer.class)
                .setParameter("login", login)
                .getSingleResult();
    }
}
