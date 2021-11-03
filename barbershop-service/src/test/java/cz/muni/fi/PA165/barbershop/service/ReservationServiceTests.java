package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.ReservationDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;


@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationServiceTests extends AbstractTestNGSpringContextTests {
    @Mock
    private ReservationDao reservationDao;

    @Autowired
    @InjectMocks
    private ReservationService reservationService;

    Reservation reservation;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    Employee employee1;
    Customer customer1;
    MyService service1;

    @Captor
    ArgumentCaptor<Reservation> captor;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    private void setUp() {
        reservation = new Reservation();
        employee1 = new Employee();
        employee1.setId(1L);
        customer1 = new Customer();
        customer1.setId(2L);
        service1 = new MyService();
        service1.setId(3L);
        fromTime = LocalDateTime.of(2000, 1, 10, 12, 30, 0);
        toTime = LocalDateTime.of(2000, 1, 10, 14, 0, 0);
        reservation.setId(4L);
        reservation.setFromTime(fromTime);
        reservation.setToTime(toTime);
        reservation.setEmployee(employee1);
        reservation.setCustomer(customer1);
        reservation.setServices(List.of(service1));
        Mockito.when(reservationDao.findById(reservation.getId())).thenReturn(reservation);
    }

    @Test
    public void createReservationTest() {
        reservationDao.create(reservation);
        Mockito.verify(reservationDao).create(reservation);
    }

    @Test
    public void findReservationByIdTest() {
        Assert.assertEquals(reservationDao.findById(reservation.getId()), reservation);
    }

    @Test
    public void setReservationCompletionTest() {
        reservationService.setReservationCompletion(reservation.getId(), true);
        Mockito.verify(reservationDao, Mockito.times(2)).update(captor.capture());
        var res = captor.getValue();
        Assert.assertTrue(res.isDone());
    }

    @Test
    public void cancelReservationTest() {
        reservationService.cancelReservation(reservation.getId());
        Mockito.verify(reservationDao).delete(reservation.getId());
    }

    @Test
    public void addFeedback() {
        var feedback = "feedback";
        Assert.assertNotNull(reservationService.findByID(reservation.getId()));
        reservationService.addFeedback(reservation.getId(), feedback);
        Mockito.verify(reservationDao).update(captor.capture());
        var res = captor.getValue();
        Assert.assertEquals(res.getFeedbackText(), feedback);
    }

}
