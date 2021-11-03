package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.api.dto.CustomerDTO;
import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ReservationDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ServiceDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    BeanMappingService beanMappingService;

    private Employee employee1;
    private Customer customer1;
    private MyService service1;
    private MyService service2;
    private Reservation reservation1;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareVariables() {
        employee1 = new Employee("login", "password", "name", "surname", "+420123456789", "City", "Street", "11111", BigDecimal.ONE);
        employee1.setId(1L);
        customer1 = new Customer("login1", "password1", "name1", "surname1", "+420123456789a", "City1", "Street1", "11111a");
        customer1.setId(11L);
        service1 = new MyService("serviceName", 1, BigDecimal.TEN);
        service1.setId(1L);
        service2 = new MyService("service2Name", 2, BigDecimal.TEN);
        service2.setId(2L);
        fromTime = LocalDateTime.of(2000, 1, 10, 12, 30, 0);
        toTime = LocalDateTime.of(2000, 1, 10, 14, 0, 0);
        reservation1 = new Reservation(customer1, employee1, fromTime, toTime, List.of(service1, service2));
        reservation1.setId(1);
    }

    @Test
    public void reservationTransformTest() {
        var dto = beanMappingService.mapTo(reservation1, ReservationDTO.class);
        Assert.assertEquals(reservation1.getId(), dto.getId());
        Assert.assertEquals(reservation1.isDone(), dto.isDone());
        Assert.assertEquals(reservation1.getFeedbackText(), dto.getFeedbackText());
        Assert.assertEquals(dto.getCustomerDTO(), beanMappingService.mapTo(customer1, CustomerDTO.class));
        Assert.assertEquals(dto.getServiceDTOs(), beanMappingService.mapTo(List.of(service1, service2), ServiceDTO.class));
        Assert.assertEquals(dto.getEmployeeDTO(), beanMappingService.mapTo(employee1, EmployeeDTO.class));
        Assert.assertEquals(dto.getFromTime(), fromTime);
        Assert.assertEquals(dto.getToTime(), toTime);
    }

}
