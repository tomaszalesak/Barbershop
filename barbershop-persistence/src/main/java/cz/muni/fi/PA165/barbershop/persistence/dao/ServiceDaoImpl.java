package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Service DAO class
 *
 * @author Jakub Zich
 */
@Repository
public class ServiceDaoImpl extends GenericBaseDaoImpl<MyService> implements ServiceDao {

    EmployeeDao employeeDao;
    ReservationDao reservationDao;

    @Autowired
    public ServiceDaoImpl(EmployeeDao employeeDao, ReservationDao reservationDao) {
        super(MyService.class);
        this.employeeDao = employeeDao;
        this.reservationDao = reservationDao;
    }

    @Override
    public void delete(Long id) {
        MyService myService = super.findById(id);
        List<Employee> employees = myService.getEmployees();
        List<Employee> copy = new ArrayList<>(employees);
        for (Employee e : copy) {
            myService.removeEmployee(e);
            employeeDao.update(e);
        }

        List<Reservation> reservations = myService.getReservations();
        List<Reservation> copy2 = new ArrayList<>(reservations);
        for (Reservation r : copy2) {
            myService.removeReservation(r);
            reservationDao.update(r);
        }
        super.delete(id);
    }

    @Override
    public MyService findByName(String name) {
        return em.createQuery("select s from MyService s where s.name = :name", MyService.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
