package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;
import cz.muni.fi.PA165.barbershop.persistence.entity.Reservation;

import java.util.List;

/**
 * @author Jakub Zich
 */
public interface ServiceService {
    /**
     * Creates a service from the provided parameter
     * @param service to be created
     */
    void createService (MyService service);

    /**
     * Finds a service by ID
     * @param ID of the seeked service
     * @return found service
     */
    MyService findServiceById(Long ID);

    /**
     * Finds a service by name
     * @param name of the seeked service
     * @return found service
     */
    MyService findServiceByName(String name);

    /**
     * Finds all existing services
     * @return all services
     */
    List<MyService> findAllServices();

    /**
     * Updates parameters of a service
     * @param service to be updated
     */
    void updateService(MyService service);

    /**
     * Deletes given service
     * @param service to be deleted
     */
    void deleteService(MyService service);
}
