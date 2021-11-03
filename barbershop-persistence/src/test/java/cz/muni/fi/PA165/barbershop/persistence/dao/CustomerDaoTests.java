package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomáš Zálešák
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerDao customerDao;

    private Customer customer1;
    private Customer customer2;

    @BeforeMethod
    private void setUp() {
        customer1 = new Customer("login", "password", "Jan", "Slaný", "123456789", "Trebic", "Ajtacka 123", "68801");
        customer2 = new Customer("login1", "password", "Jan", "Hořký", "123456789", "Krasnopavlovsk", "Raje 123", "68801");
        customerDao.create(customer1);
        customerDao.create(customer2);
    }

    @AfterMethod
    private void clean() {
        for (Customer e : customerDao.findAll()) {
            customerDao.delete(e.getId());
        }
    }

    @Test
    public void testCreate() {
        assertThat(customerDao.findById(customer1.getId())).isEqualTo(customer1);
    }

    @Test
    public void testFindById() {
        assertThat(customerDao.findById(customer1.getId())).isEqualTo(customer1);
    }

    @Test
    public void testFindAll() {
        var employees = customerDao.findAll();
        assertThat(employees).hasSize(2);
        assertThat(employees).contains(customer1, customer2);
    }

    @Test
    public void testUpdate() {
        var name = "Tomas";
        customer1.setFirstname(name);
        customerDao.update(customer1);
        assertThat(customerDao.findById(customer1.getId()).getFirstname()).isEqualTo(name);
    }

    @Test
    public void testDelete() {
        customerDao.delete(customer1.getId());
        var customers = customerDao.findAll();
        assertThat(customers).hasSize(1);
        assertThat(customers).doesNotContain(customer1);
    }
}
