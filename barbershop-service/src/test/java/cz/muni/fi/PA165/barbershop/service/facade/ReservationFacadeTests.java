package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.ReservationDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.*;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.CustomerService;
import cz.muni.fi.PA165.barbershop.service.EmployeeService;
import cz.muni.fi.PA165.barbershop.service.ReservationService;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;


@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationFacadeTests extends AbstractTestNGSpringContextTests {
    @Mock
    private ReservationService reservationService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private CustomerService customerService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private ReservationFacadeImpl reservationFacade;

    Employee employee1;
    Customer customer1;
    MyService service1;
    MyService service2;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    Reservation reservation1;
    ReservationDTO reservation1DTO;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
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
        reservation1DTO = new ReservationDTO();
        reservation1DTO.setId(reservation1.getId());
        reservation1DTO.setFromTime(fromTime);
        reservation1DTO.setToTime(toTime);
        Mockito.when(beanMappingService.mapTo(reservation1, ReservationDTO.class)).thenReturn(reservation1DTO);
    }

    @Test
    public void addFeedbackTest() {
        String feedback = "feedback";
        ReservationDTO dto = new ReservationDTO();
        dto.setId(1L);
        dto.setFeedbackText(feedback);
        reservationFacade.addFeedback(dto);
        Mockito.verify(reservationService, Mockito.times(1)).addFeedback(1L, feedback);
    }
    @Test
    public void cancelReservationTest() {
        reservationFacade.cancelReservation(1L);
        Mockito.verify(reservationService, Mockito.times(1)).cancelReservation(1L);
    }

    @Test
    public void createReservationTest() {
        var resCreateDTO = new ReservationDTO();
        resCreateDTO.setFromTime(fromTime);
        resCreateDTO.setToTime(toTime);
        Mockito.when(beanMappingService.mapTo(resCreateDTO, Reservation.class)).thenReturn(reservation1);
        var id = reservationFacade.createReservation(resCreateDTO);
        Mockito.verify(reservationService, Mockito.times(1)).createReservation(reservation1);
        Assert.assertEquals(reservation1.getId(), (long) id);
    }

    @Test void getReservationFromIdTest() {
        Mockito.when(reservationService.findByID(reservation1.getId())).thenReturn(reservation1);
        Assert.assertEquals(reservationFacade.getReservationWithID(reservation1.getId()), reservation1DTO);
    }

    @Test void setReservationCompletionTest() {
        reservationFacade.setReservationCompletion(reservation1.getId(), true);
        Mockito.verify(reservationService, Mockito.times(1)).setReservationCompletion(reservation1.getId(), true);
    }

    @Test void getAllReservationsForEmployeeTest() {
        employee1.setReservations(List.of(reservation1));
        var reservationDTOs = List.of(reservation1DTO);
        Mockito.when(employeeService.findById(employee1.getId())).thenReturn(employee1);
        Mockito.when(beanMappingService.mapTo(employee1.getReservations(), ReservationDTO.class)).thenReturn(reservationDTOs);
        Assert.assertTrue(reservationFacade.getAllReservationsForEmployee(employee1.getId()).contains(reservation1DTO));
    }

    @Test void getAllReservationsForCustomerTest() {
        customer1.setReservations(List.of(reservation1));
        var reservationDTOs = List.of(reservation1DTO);
        Mockito.when(customerService.findById(customer1.getId())).thenReturn(customer1);
        Mockito.when(beanMappingService.mapTo(customer1.getReservations(), ReservationDTO.class)).thenReturn(reservationDTOs);
        Assert.assertTrue(reservationFacade.getAllReservationsForCustomer(customer1.getId()).contains(reservation1DTO));
    }

}
