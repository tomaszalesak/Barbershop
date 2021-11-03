package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.CustomerDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Jan Sladký
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceTests extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    @Mock
    PasswordEncoder encoder;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeMethod
    public void createUser() {
        customer = new Customer();
        customer.setLogin("slad");
        customer.setFirstname("Jan");
        customer.setLastname("Sladký");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerTest() {
        when(encoder.encode("1234")).thenReturn("1234");
        customerService.register(customer, "1234");
        verify(customerDao).create(customer);
    }

    @Test
    public void getAllTest(){
        when(customerDao.findAll()).thenReturn(Collections.singletonList(customer));

        List<Customer> customers = customerService.getAll();
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0)).isEqualTo(customer);

        verify(customerDao).findAll();
    }

    @Test
    public void authenticateTest(){
        when(encoder.encode("1234")).thenReturn("4321");

        customerService.register(customer, "1234");
        assertThat(customer.getPassword()).isEqualTo("4321");

        verify(customerDao).create(customer);
    }


    @Test
    public void findByIdTest(){
        customerService.register(customer, "1234");

        when(customerDao.findById(customer.getId())).thenReturn(customer);
        Customer customer2 = customerService.findById(customer.getId());

        assertThat(customer).isEqualTo(customer2);

        verify(customerDao).findById(customer.getId());
    }

    @Test
    public void findByLoginTest(){
        customerService.register(customer, "1234");

        when(customerDao.findByLogin(customer.getLogin())).thenReturn(customer);
        Customer customer2 = customerService.findByLogin(customer.getLogin());

        assertThat(customer).isEqualTo(customer2);

        verify(customerDao).findByLogin(customer.getLogin());
    }
}
