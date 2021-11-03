package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.CustomerDTO;
import cz.muni.fi.PA165.barbershop.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.CustomerService;
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
public class CustomerFacadeTests extends AbstractTestNGSpringContextTests {

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerFacadeImpl customerFacade;

    private Customer customer;
    private CustomerDTO customerDTO;
    private PersonAuthenticateDTO personAuthenticateDTO;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void init() {
        customer = new Customer();
        customer.setLogin("slady");
        customer.setFirstname("Jan");
        customer.setLastname("Sladký");

        customerDTO = new CustomerDTO();
        customerDTO.setLogin("sladyy");
        customerDTO.setFirstname("Jan");
        customerDTO.setLastname("Sladký");

        personAuthenticateDTO = new PersonAuthenticateDTO();
        personAuthenticateDTO.setLogin("slad");
        personAuthenticateDTO.setPassword("1234");
    }

    @Test
    public void registerTest(){
        when(beanMappingService.mapTo(customerDTO, Customer.class)).thenReturn(customer);

        customerFacade.register(customerDTO, "1234");
        verify(customerService).register(customer, "1234");
    }

    @Test
    public void getAllTest(){
        when(customerService.getAll()).thenReturn(Collections.singletonList(customer));
        when(beanMappingService.mapTo(Collections.singletonList(customer), CustomerDTO.class)).thenReturn(Collections.singletonList(customerDTO));

        Collection<CustomerDTO> customerDTOS =  customerFacade.getAll();

        assertThat(customerDTOS.size()).isEqualTo(1);
        assertThat(customerDTOS).containsOnly(customerDTO);
        verify(customerService).getAll();
    }

    @Test
    public void authenticateTest(){
        when(customerService.findByLogin(personAuthenticateDTO.getLogin())).thenReturn(customer);
        when(customerService.authenticate(customer, personAuthenticateDTO.getPassword())).thenReturn(true);

        assertThat(customerFacade.authenticate(personAuthenticateDTO)).isTrue();
        verify(customerService).authenticate(customer, "1234");
    }

    @Test
    public void findByIdTest(){
        when(customerService.findById(1L)).thenReturn(customer);
        when(beanMappingService.mapTo(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO customerDTO1 =  customerFacade.findById(1L);

        assertThat(customerDTO1).isEqualTo(customerDTO);
        verify(customerService).findById(1L);
    }

}
