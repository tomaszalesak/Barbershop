package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.EmployeeDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
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
public class EmployeeServiceTests extends AbstractTestNGSpringContextTests {
    @Mock
    private EmployeeDao employeeDao;

    @Mock
    PasswordEncoder encoder;

    @Autowired
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeMethod
    public void createUser() {
        employee = new Employee();
        employee.setLogin("slad");
        employee.setFirstname("Jan");
        employee.setLastname("Sladký");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerTest() {
        when(encoder.encode("1234")).thenReturn("1234");
        employeeService.register(employee, "1234");
        verify(employeeDao).create(employee);
    }

    @Test
    public void getAllTest(){
        when(employeeDao.findAll()).thenReturn(Collections.singletonList(employee));

        List<Employee> employees = employeeService.getAll();
        assertThat(employees.size()).isEqualTo(1);
        assertThat(employees.get(0)).isEqualTo(employee);

        verify(employeeDao).findAll();
    }

    @Test
    public void authenticateTest(){

        when(encoder.encode("1234")).thenReturn("4321");

        employeeService.register(employee, "1234");
        assertThat(employee.getPassword()).isEqualTo("4321");

        verify(employeeDao).create(employee);
    }


    @Test
    public void findByIdTest(){
        employeeService.register(employee, "1234");

        when(employeeDao.findById(employee.getId())).thenReturn(employee);
        Employee employee2 = employeeService.findById(employee.getId());

        assertThat(employee).isEqualTo(employee2);

        verify(employeeDao).findById(employee.getId());
    }

    @Test
    public void findByLoginTest(){
        employeeService.register(employee, "1234");

        when(employeeDao.findByLogin(employee.getLogin())).thenReturn(employee);
        Employee employee2 = employeeService.findByLogin(employee.getLogin());

        assertThat(employee).isEqualTo(employee2);

        verify(employeeDao).findByLogin(employee.getLogin());
    }
}
