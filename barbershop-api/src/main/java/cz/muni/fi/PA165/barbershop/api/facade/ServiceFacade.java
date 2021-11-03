package cz.muni.fi.PA165.barbershop.api.facade;

import cz.muni.fi.PA165.barbershop.api.dto.ServiceDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakub Zich
 */
public interface ServiceFacade {
    Long create(ServiceDTO ServiceDTO);
    ServiceDTO findServiceByID(Long serviceID);
    ServiceDTO findServiceByName(String serviceName);
    void update(ServiceDTO serviceDTO);
    void delete(Long serviceID);
    List<ServiceDTO> getAllServices();
}
