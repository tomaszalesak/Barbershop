package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
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
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jan Sladký
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WorkingHoursDaoTests extends AbstractTestNGSpringContextTests {

    Employee employee;
    @Autowired
    private WorkingHoursDao workingHoursDao;
    @Autowired
    private EmployeeDao employeeDao;
    private WorkingHours workingHours1;
    private WorkingHours workingHours2;

    @BeforeMethod
    private void setUp() {
        employee = new Employee("pepa", "123", "Jozef", "Novák", "739942138", "Třebíč", "Modřínová", "67401", new BigDecimal("534"));
        workingHours1 = new WorkingHours(LocalDateTime.of(2021, Month.FEBRUARY, 18, 6, 30), LocalDateTime.of(2021, Month.FEBRUARY, 20, 12, 30), employee);
        workingHours2 = new WorkingHours(LocalDateTime.of(2021, Month.MARCH, 20, 6, 30), LocalDateTime.of(2021, Month.MARCH, 20, 12, 30), employee);

        employeeDao.create(employee);
        workingHoursDao.create(workingHours1);
        workingHoursDao.create(workingHours2);
    }

    @AfterMethod
    private void close() {
        for (WorkingHours review : workingHoursDao.findAll()) {
            workingHoursDao.delete(review.getId());
        }

        employeeDao.delete(employee.getId());
    }

    @Test
    public void TestCreate() {
        WorkingHours workingHours = new WorkingHours(LocalDateTime.of(2021, Month.FEBRUARY, 20, 6, 30), LocalDateTime.of(2021, Month.FEBRUARY, 20, 12, 30), employee);
        workingHoursDao.create(workingHours);

        WorkingHours findWorkingHours = workingHoursDao.findById(workingHours.getId());
        assertThat(findWorkingHours).isEqualTo(workingHours);
    }

    @Test
    public void TestCreateWithNull() {
        assertThatThrownBy(() -> workingHoursDao.create(null)).isInstanceOf(Exception.class);
    }

    @Test
    public void TestFindById() {
        assertThat(workingHoursDao.findById(workingHours1.getId())).isEqualTo(workingHours1);
    }

    @Test
    public void TestFindByIdWithNull() {
        assertThatThrownBy(() -> workingHoursDao.findById(null)).isInstanceOf(Exception.class);
    }

    @Test
    public void TestFindAll() {
        List<WorkingHours> workingHours = workingHoursDao.findAll();
        List<WorkingHours> workingHoursList = new ArrayList<>();
        workingHoursList.add(workingHours1);
        workingHoursList.add(workingHours2);
        assertThat(workingHours).containsExactlyElementsOf(workingHoursList);
    }


    @Test
    public void TestUpdate() {
        workingHours1.setFromTime(LocalDateTime.of(2021, Month.FEBRUARY, 20, 6, 50));
        workingHoursDao.update(workingHours1);
        assertThat(workingHoursDao.findById(workingHours1.getId())).isEqualTo(workingHours1);
    }

    @Test
    public void TestUpdateWithNull() {
        assertThatThrownBy(() -> workingHoursDao.update(null)).isInstanceOf(Exception.class);
    }


    @Test
    public void TestDelete() {
        workingHoursDao.delete(workingHours1.getId());
        assertThat(workingHoursDao.findAll()).containsExactly(workingHours2);
    }

    @Test
    public void TestDeleteWithNull() {
        assertThatThrownBy(() -> workingHoursDao.delete(null)).isInstanceOf(Exception.class);
    }

    @Test
    public void TestGetWorkingHoursInPeriodForEmployee() {
        List<WorkingHours> w = new ArrayList<>();
        w.add(workingHours1);
        var result = workingHoursDao.getWorkingHoursInPeriodForEmployee(LocalDateTime.of(2021, Month.FEBRUARY, 17, 6, 30), LocalDateTime.of(2021, Month.FEBRUARY, 21, 6, 30), employee);
        assertThat(result).containsAll(w);
    }

}
