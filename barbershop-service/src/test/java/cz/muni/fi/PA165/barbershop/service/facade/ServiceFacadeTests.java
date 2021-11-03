package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.ServiceDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;
import cz.muni.fi.PA165.barbershop.service.BeanMappingServiceImpl;
import cz.muni.fi.PA165.barbershop.service.ServiceServiceImpl;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ServiceFacadeTests {

    @Mock
    BeanMappingServiceImpl beanMappingService;

    @Mock
    ServiceServiceImpl serviceService;

    @InjectMocks
    ServiceFacadeImpl serviceFacade;

    MyService service;
    ServiceDTO serviceDTO;
    ServiceDTO serviceDTO1;
    List<MyService> allServices = new ArrayList<>();

    @BeforeMethod
    private void init() throws ServiceException{
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    private void setUp() {
        service = new MyService();
        service.setId(456L);
        service.setName("Hair cutting");
        service.setDurationMinutes(30);
        service.setPrice(new BigDecimal(100));

        serviceDTO = new ServiceDTO();
        serviceDTO.setId(service.getId());
        serviceDTO.setName(service.getName());
        serviceDTO.setDurationMinutes(service.getDurationMinutes());
        serviceDTO.setPrice(service.getPrice());

        serviceDTO1 = new ServiceDTO();
        serviceDTO1.setName(service.getName());
        serviceDTO1.setDurationMinutes(service.getDurationMinutes());
        serviceDTO1.setPrice(service.getPrice());

        Mockito.when(beanMappingService.mapTo(service, ServiceDTO.class)).thenReturn(serviceDTO);
        Mockito.when(beanMappingService.mapTo(serviceDTO, MyService.class)).thenReturn(service);
        Mockito.when(beanMappingService.mapTo(serviceDTO1, MyService.class)).thenReturn(service);
    }

    @Test
    public void testCreate(){
        serviceFacade.create(serviceDTO1);
        Mockito.verify(serviceService).createService(service);
    }

    @Test
    public void testFindById(){
        Mockito.when(serviceService.findServiceById(456L)).thenReturn(service);
        Assert.assertEquals(serviceFacade.findServiceByID(456L), serviceDTO);
    }

    @Test
    public void testFindByName(){
        Mockito.when(serviceService.findServiceByName("Hair cutting")).thenReturn(service);
        Assert.assertEquals(serviceFacade.findServiceByName("Hair cutting"), serviceDTO);
    }

    @Test
    public void testUpdate(){
        serviceFacade.update(serviceDTO);
        Mockito.verify(serviceService).updateService(service);
    }

    @Test
    public void testDelete(){
        Mockito.when(serviceService.findServiceById(456L)).thenReturn(service);
        serviceFacade.delete(service.getId());
        Mockito.verify(serviceService).deleteService(service);
    }

    @Test
    public void testFindAllServices(){
        allServices.add(service);
        Mockito.when(serviceService.findAllServices()).thenReturn(allServices);
        serviceFacade.getAllServices();
        Mockito.verify(serviceService).findAllServices();
    }
}
