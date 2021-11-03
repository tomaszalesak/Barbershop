package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.WorkingHoursDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

public class WorkingHoursServiceTests {

    @Mock
    private WorkingHoursDao workingHoursDao;

    @Autowired
    @InjectMocks
    private WorkingHoursServiceImpl workingHoursService;

    private WorkingHours wh;
    private Employee e;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void init() {
        fromTime = LocalDateTime.MIN;
        toTime = LocalDateTime.MAX;
        e = new Employee();
        wh = new WorkingHours(fromTime, toTime, e);
    }

    @AfterMethod
    void reset() {
        Mockito.reset(workingHoursDao);
    }

    @Test
    void create() {
        workingHoursService.create(wh);
        verify(workingHoursDao).create(wh);
    }

    @Test
    void findById() {
        workingHoursService.findById(Long.MIN_VALUE);
        verify(workingHoursDao).findById(Long.MIN_VALUE);
    }

    @Test
    void update() {
        workingHoursService.update(wh);
        verify(workingHoursDao).update(wh);
    }

    @Test
    void delete() {
        workingHoursService.delete(wh);
        verify(workingHoursDao).delete(wh.getId());
    }
}
