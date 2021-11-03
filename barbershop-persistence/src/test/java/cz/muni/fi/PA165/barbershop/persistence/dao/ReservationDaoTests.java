package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jakub Zich
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ReservationDaoTests extends AbstractTestNGSpringContextTests {

    Customer customer1;
    Customer customer2;
    Employee employee1;
    Employee employee2;
    List<MyService> onlyCutting = new ArrayList<>();
    List<MyService> both = new ArrayList<>();
    Reservation reservation1;
    Reservation reservation2;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ServiceDao serviceDao;

    @BeforeMethod
    private void setup() {
        customer1 = new Customer("oldakorba", "jsemnamakanej", "Oldřich", "Chlubna",
                "123456789", "Brno", "Kounicova 125", "60200");
        customer2 = new Customer("adelaK", "52468", "Adéla", "Kučerová",
                "123456789", "Brno", "Purkyňova 456", "60200");
        customerDao.create(customer1);
        customerDao.create(customer2);

        employee1 = new Employee("fanda123", "heslo123", "František", "Lužík",
                "987654321", "Brno", "Palackého třída 354", "60200", new BigDecimal("25000"));
        employee2 = new Employee("lucinka", "jsem hezka", "Lucie", "Vošická",
                "987654321", "Brno", "Kolejní 753", "60200", new BigDecimal("25000"));
        employeeDao.create(employee1);
        employeeDao.create(employee2);

        reservation1 = new Reservation(customer1, employee1,
                LocalDateTime.of(2021, 4, 15, 12, 0, 0),
                LocalDateTime.of(2021, 4, 15, 12, 30, 0), both);
        reservation2 = new Reservation(customer2, employee2,
                LocalDateTime.of(2021, 4, 15, 12, 0, 0),
                LocalDateTime.of(2021, 4, 15, 12, 45, 0), onlyCutting);
    }

    @AfterMethod
    public void clean() {
        for (Reservation reservation : reservationDao.findAll()) {
            reservationDao.delete(reservation.getId());
        }
        for (Customer customer : customerDao.findAll()) {
            customerDao.delete(customer.getId());
        }
        for (Employee employee : employeeDao.findAll()) {
            employeeDao.delete(employee.getId());
        }
        for (MyService service : serviceDao.findAll()) {
            serviceDao.delete(service.getId());
        }
    }

    @Test
    public void testFindById() {
        reservationDao.create(reservation1);
        assertThat(reservationDao.findById(reservation1.getId())).isEqualTo(reservation1);
    }

    @Test
    public void testFindAll() {
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        assertThat(reservationDao.findAll()).contains(reservation1, reservation2);
    }

    @Test
    public void testDelete() {
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        reservationDao.delete(reservation1.getId());
        assertThat(reservationDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testUpdate() {
        LocalDateTime ldt = LocalDateTime.of(2021, 4, 18, 12, 0, 0);
        reservationDao.create(reservation1);
        reservation1.setToTime(ldt);
        reservationDao.update(reservation1);
        assertThat(reservationDao.findById(reservation1.getId()).getToTime()).isEqualTo(ldt);
    }

    @Test
    public void testCreateNull() {
        assertThatThrownBy(() -> reservationDao.create(null)).isInstanceOf(Exception.class);
    }
}
