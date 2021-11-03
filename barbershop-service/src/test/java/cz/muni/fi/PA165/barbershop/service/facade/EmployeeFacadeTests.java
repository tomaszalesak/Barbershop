package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.EmployeeService;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jan Sladký
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class EmployeeFacadeTests extends AbstractTestNGSpringContextTests {
    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeFacadeImpl employeeFacade;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private PersonAuthenticateDTO personAuthenticateDTO;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void init() {
        employee = new Employee();
        employee.setLogin("slad");
        employee.setFirstname("Jan");
        employee.setLastname("Sladký");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setLogin("slad");
        employeeDTO.setFirstname("Jan");
        employeeDTO.setLastname("Sladký");

        personAuthenticateDTO = new PersonAuthenticateDTO();
        personAuthenticateDTO.setLogin("slad");
        personAuthenticateDTO.setPassword("1234");
    }

    @Test
    public void registerTest(){
        when(beanMappingService.mapTo(employeeDTO, Employee.class)).thenReturn(employee);

        employeeFacade.register(employeeDTO, "1234");
        verify(employeeService).register(employee, "1234");
    }

    @Test
    public void getAllTest(){
        when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));
        when(beanMappingService.mapTo(Collections.singletonList(employee), EmployeeDTO.class)).thenReturn(Collections.singletonList(employeeDTO));

        Collection<EmployeeDTO> employeeDTOS =  employeeFacade.getAll();

        assertThat(employeeDTOS.size()).isEqualTo(1);
        assertThat(employeeDTOS).containsOnly(employeeDTO);
        verify(employeeService).getAll();
    }

    @Test
    public void authenticateTest(){
        when(employeeService.findByLogin(personAuthenticateDTO.getLogin())).thenReturn(employee);
        when(employeeService.authenticate(employee, personAuthenticateDTO.getPassword())).thenReturn(true);

        assertThat(employeeFacade.authenticate(personAuthenticateDTO)).isTrue();
        verify(employeeService).authenticate(employee, "1234");
    }

    @Test
    public void findByIdTest(){
        when(employeeService.findById(1L)).thenReturn(employee);
        when(beanMappingService.mapTo(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        EmployeeDTO employeeDTO1 =  employeeFacade.findById(1L);

        assertThat(employeeDTO1).isEqualTo(employeeDTO);
        verify(employeeService).findById(1L);
    }
}
