package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.ServiceDTO;
import cz.muni.fi.PA165.barbershop.api.facade.ServiceFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakub Zich
 */
@Service
@Transactional
public class ServiceFacadeImpl implements ServiceFacade {

    private ServiceService serviceService;
    private BeanMappingService beanMappingService;

    @Autowired
    public ServiceFacadeImpl (ServiceService serviceService, BeanMappingService beanMappingService) {
        this.serviceService = serviceService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long create(ServiceDTO serviceDTO){
        MyService mappedService = beanMappingService.mapTo(serviceDTO, MyService.class);
        serviceService.createService(mappedService);
        return mappedService.getId();
    }

    @Override
    public ServiceDTO findServiceByID(Long serviceID){
        MyService service = serviceService.findServiceById(serviceID);
        if (service == null){return null;}
        else {return beanMappingService.mapTo(service, ServiceDTO.class);}
    }

    @Override
    public ServiceDTO findServiceByName(String serviceName){
        MyService service = serviceService.findServiceByName(serviceName);
        if (service == null){return null;}
        else {return beanMappingService.mapTo(service, ServiceDTO.class);}
    }

    @Override
    public void update(ServiceDTO serviceDTO){ serviceService.updateService(beanMappingService.mapTo(serviceDTO, MyService.class)); }

    @Override
    public void delete(Long serviceID){
        serviceService.deleteService(serviceService.findServiceById(serviceID));
    }

    @Override
    public List<ServiceDTO> getAllServices(){
        List<MyService> services = serviceService.findAllServices();
        return beanMappingService.mapTo(services, ServiceDTO.class);
    }
}
