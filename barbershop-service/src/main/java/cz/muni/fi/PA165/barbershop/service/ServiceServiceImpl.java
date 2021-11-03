package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.ServiceDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jakub Zich
 */
@Service
public class ServiceServiceImpl implements ServiceService{

    private ServiceDao serviceDao;

    @Autowired
    public ServiceServiceImpl (ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public void createService (MyService service){
        serviceDao.create(service);
    }

    @Override
    public MyService findServiceById(Long ID){
        return serviceDao.findById(ID);
    }

    @Override
    public MyService findServiceByName(String name){
        return serviceDao.findByName(name);
    }

    @Override
    public List<MyService> findAllServices(){
        return serviceDao.findAll();
    }

    @Override
    public void updateService(MyService service){
        serviceDao.update(service);
    }

    @Override
    public void deleteService(MyService service){
        serviceDao.delete(service.getId());
    }
}
