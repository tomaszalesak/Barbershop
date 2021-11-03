package cz.muni.PA165.barbershop.sampleData;

import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import cz.muni.fi.PA165.barbershop.service.CustomerService;
import cz.muni.fi.PA165.barbershop.service.EmployeeService;
import cz.muni.fi.PA165.barbershop.service.ReservationService;
import cz.muni.fi.PA165.barbershop.service.ServiceService;
import cz.muni.fi.PA165.barbershop.service.WorkingHoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ReservationService reservationService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {

        var sladky = customer("xsladky1", "Jan", "Sladký", "739942159", "Třebíč", "Modřínová", "67401");
        var yarovoy = customer("xyarovoy", "Konstantin", "Yarovoy", "9089034123", "Brno", "Vinarska", "60200");

        var hair = service(30, "haircut", BigDecimal.TEN);
        var shave = service( 15, "beard_shaving", BigDecimal.ONE);
        var candy = service(1, "give_candy", BigDecimal.ZERO);

        var zich = employee("xzich", "Jakub", "Zich", BigDecimal.ONE, List.of(hair, candy));
        var zalesak = employee("xzalesa2", "Tomas", "Zalesak", BigDecimal.TEN, List.of(shave, candy));
        var admin = employee("Admin", "Admin", "Admin", BigDecimal.ZERO, null);

        workingHours(zich, LocalDateTime.of(2021, 6, 10, 8, 0), LocalDateTime.of(2021, 6, 10, 12, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 10, 13, 0), LocalDateTime.of(2021, 6, 10, 18, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 13, 8, 0), LocalDateTime.of(2021, 6, 13, 12, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 13, 13, 0), LocalDateTime.of(2021, 6, 13, 18, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 14, 8, 0), LocalDateTime.of(2021, 6, 14, 12, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 14, 13, 0), LocalDateTime.of(2021, 6, 14, 18, 0));

        workingHours(zalesak, LocalDateTime.of(2021, 6, 10, 8, 0), LocalDateTime.of(2021, 6, 10, 10, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 10, 12, 0), LocalDateTime.of(2021, 6, 10, 14, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 10, 15, 0), LocalDateTime.of(2021, 6, 10, 17, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 13, 8, 0), LocalDateTime.of(2021, 6, 13, 10, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 13, 12, 0), LocalDateTime.of(2021, 6, 13, 14, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 13, 15, 0), LocalDateTime.of(2021, 6, 13, 17, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 14, 8, 0), LocalDateTime.of(2021, 6, 14, 10, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 14, 12, 0), LocalDateTime.of(2021, 6, 14, 14, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 14, 15, 0), LocalDateTime.of(2021, 6, 14, 17, 0));

        workingHours(zich, LocalDateTime.of(2021, 6, 26, 16, 0), LocalDateTime.of(2021, 6, 26, 17, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 28, 16, 0), LocalDateTime.of(2021, 6, 28, 17, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 29, 16, 0), LocalDateTime.of(2021, 6, 29, 17, 0));
        workingHours(zich, LocalDateTime.of(2021, 6, 26, 16, 0), LocalDateTime.of(2021, 6, 26, 17, 0));
        workingHours(zalesak, LocalDateTime.of(2021, 6, 27, 16, 0), LocalDateTime.of(2021, 6, 27, 17, 0));



        reservation(yarovoy, zich, LocalDateTime.of(2021, 5, 30, 13, 0), LocalDateTime.of(2021, 5, 30, 13, 31),
                List.of(hair, candy), false, null);
        reservation(yarovoy, zalesak, LocalDateTime.of(2021, 3, 30, 13, 0), LocalDateTime.of(2021, 3, 30, 13, 31),
                List.of(shave, candy), true, null);

        reservation(sladky, zich, LocalDateTime.of(2021, 5, 29, 13, 0), LocalDateTime.of(2021, 5, 29, 13, 31),
                List.of(hair, candy), false, null);
        reservation(sladky, zalesak, LocalDateTime.of(2021, 3, 20, 13, 0), LocalDateTime.of(2021, 3, 20, 13, 31),
                List.of(shave, candy), true, null);

        log.info("Loaded eShop users.");
    }

    private Customer customer(String login, String firstname, String lastname, String phoneNumber, String city, String street, String postalCode) {
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setPhoneNumber(phoneNumber);
        customer.setCity(city);
        customer.setStreet(street);
        customer.setPostalCode(postalCode);
        customerService.register(customer, "1234");

        return customer;
    }

    private Employee employee(String login, String firstname, String lastname, BigDecimal salary, List<MyService> services) {
        Employee employee = new Employee();
        employee.setLogin(login);
        employee.setFirstname(firstname);
        employee.setLastname(lastname);
        employee.setPhoneNumber("88005553535");
        employee.setCity("Brno");
        employee.setStreet("Puskina, dum Kolotuskina");
        employee.setPostalCode("1337");
        employee.setSalary(salary);
        employee.setServices(services);
        employeeService.register(employee, "1234");

        return employee;
    }

    private WorkingHours workingHours(Employee employee, LocalDateTime fromTime, LocalDateTime toTime) {
        WorkingHours workingHours = new WorkingHours();
        workingHours.setEmployee(employee);
        workingHours.setFromTime(fromTime);
        workingHours.setToTime(toTime);
        workingHoursService.create(workingHours);

        return workingHours;
    }

    private MyService service(int duration, String name, BigDecimal price) {
        MyService service = new MyService();
        service.setDurationMinutes(duration);
        service.setName(name);
        service.setPrice(price);
        serviceService.createService(service);

        return service;
    }

    private Reservation reservation(Customer customer, Employee employee, LocalDateTime fromTime, LocalDateTime toTime,
                                    List<MyService> services, boolean done, String feedback) {
        Reservation reservation = new Reservation(customer, employee, fromTime, toTime, services);
        reservation.setDone(done);
        reservation.setFeedbackText(feedback);
        reservationService.createReservation(reservation);
        return reservation;
    }
}
