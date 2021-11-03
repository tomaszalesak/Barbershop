package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.CustomerDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends PersonServiceImpl<Customer, CustomerDao> implements CustomerService {
    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, PasswordEncoder encoder) {
        super(customerDao, encoder);
    }
}
