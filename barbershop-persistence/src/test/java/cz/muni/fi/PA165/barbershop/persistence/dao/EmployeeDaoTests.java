package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomáš Zálešák
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EmployeeDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private EmployeeDao employeeDao;

    private Employee employee1;
    private Employee employee2;

    @BeforeMethod
    private void setUp() {
        employee1 = new Employee("login", "password", "Jan", "Slaný", "123456789", "Trebic", "Ajtacka 123", "68801", new BigDecimal("9.90"));
        employee2 = new Employee("login1", "password", "Jan", "Hořký", "123456789", "Krasnopavlovsk", "Raje 123", "68801", new BigDecimal("56169.00"));
        employeeDao.create(employee1);
        employeeDao.create(employee2);
    }

    @AfterMethod
    private void clean() {
        for (Employee e : employeeDao.findAll()) {
            employeeDao.delete(e.getId());
        }
    }

    @Test
    public void testCreate() {
        var employee = new Employee("sgsgvvgfd", "ggvdfg", "ggfvgdf", "dgvsdfg", "dgdffd", "Krasnopavlovsk", "Raje 123", "68801", new BigDecimal("56169.00"));
        employeeDao.create(employee);
        assertThat(employeeDao.findById(employee.getId())).isEqualTo(employee);
    }

    @Test
    public void testFindById() {
        assertThat(employeeDao.findById(employee1.getId())).isEqualTo(employee1);
    }

    @Test
    public void testFindAll() {
        var employees = employeeDao.findAll();
        assertThat(employees).hasSize(2);
        assertThat(employees).contains(employee1, employee2);
    }

    @Test
    public void testUpdate() {
        var salary = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
        employee1.setSalary(salary);
        employeeDao.update(employee1);
        assertThat(employeeDao.findById(employee1.getId()).getSalary()).isEqualTo(salary);
    }

    @Test
    public void testDelete() {
        employeeDao.delete(employee1.getId());
        var employees = employeeDao.findAll();
        assertThat(employees).hasSize(1);
        assertThat(employees).doesNotContain(employee1);
    }

    @Test
    public void testFindByName() {
        var employee = employeeDao.findByName("Jan", "Slaný");
        assertThat(employee).hasSize(1);
        assertThat(employee).contains(employee1);
    }

}
