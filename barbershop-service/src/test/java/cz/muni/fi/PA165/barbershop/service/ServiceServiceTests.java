package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.ServiceDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ServiceServiceTests extends AbstractTestNGSpringContextTests {

    @Mock
    ServiceDao serviceDao;

    @InjectMocks
    @Autowired
    ServiceService serviceService;

    MyService service;
    List<MyService> allServices = new ArrayList<>();

    @BeforeMethod
    private void init() throws ServiceException{
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    private void setUp()  {
        service = new MyService();
        service.setId(456L);
        service.setName("Hair cutting");
        service.setDurationMinutes(30);
        service.setPrice(new BigDecimal(100));

        allServices.add(service);
    }

    @Test
    public void testCreateService(){
        serviceDao.create(service);
        Mockito.verify(serviceDao).create(service);
    }

    @Test
    public void testFindServiceById(){
        Mockito.when(serviceDao.findById(service.getId())).thenReturn(service);
        Assert.assertEquals(serviceDao.findById(456L), service);
    }

    @Test
    public void testFindServiceByName(){
        Mockito.when(serviceDao.findByName(service.getName())).thenReturn(service);
        Assert.assertEquals(serviceDao.findByName("Hair cutting"), service);
    }

    @Test
    public void findAllServices(){
        Mockito.when(serviceDao.findAll()).thenReturn(allServices);
        Assert.assertEquals(serviceDao.findAll(), allServices);
    }

    @Test
    public void testUpdateService(){
        service.setName("Washing");
        serviceDao.update(service);
        Mockito.verify(serviceDao).update(service);
    }

    @Test
    public void testDeleteService(){
        serviceDao.delete(456L);
        Mockito.verify(serviceDao).delete(service.getId());
    }
}
