package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Konstantin Yarovoy
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ServiceDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private ServiceDao serviceDao;

    private MyService s1;
    private MyService s2;
    private MyService s3;
    private MyService s1Cpy;

    @BeforeMethod
    private void setUp() {
        s1 = new MyService("s1", 30, BigDecimal.ONE);
        s2 = new MyService("s2", 15, BigDecimal.TEN);
        s3 = new MyService("s3", 60, BigDecimal.valueOf(13.5));
        s1Cpy = new MyService("s1", 30, BigDecimal.ONE);
    }

    @AfterMethod
    public void after() {
        List<MyService> services = serviceDao.findAll();
        for (MyService service : services) {
            serviceDao.delete(service.getId());
        }
    }

    @Test
    public void testFindById() {
        serviceDao.create(s1);
        assertThat(serviceDao.findById(s1.getId())).isEqualTo(s1);
    }

    @Test
    public void testFindAll() {
        serviceDao.create(s1);
        serviceDao.create(s2);
        assertThat(serviceDao.findAll()).contains(s1, s2);
    }

    @Test
    public void testDelete() {
        serviceDao.create(s1);
        serviceDao.delete(s1.getId());
        assertThat(serviceDao.findAll().size()).isEqualTo(0);
    }

    @Test
    public void testUpdate() {
        String name = "s1Edit";
        serviceDao.create(s1);
        s1.setName(name);
        serviceDao.update(s1);
        assertThat(serviceDao.findById(s1.getId()).getName()).isEqualTo(name);
    }

    @Test
    public void testEqualAreEqual() {
        serviceDao.create(s1);
        assertThat(serviceDao.findById(s1.getId())).isEqualTo(s1Cpy);
    }

    @Test
    public void testDecimals() {
        serviceDao.create(s3);
        assertThat(serviceDao.findById(s3.getId()).getPrice()).isEqualTo(s3.getPrice());
    }

    @Test
    public void testCreateNull() {
        assertThatThrownBy(() -> serviceDao.create(null)).isInstanceOf(Exception.class);
    }

    @Test
    public void testFindNotExistent() {
        assertThatThrownBy(() -> serviceDao.findById(s1.getId())).isInstanceOf(Exception.class);
    }
}
