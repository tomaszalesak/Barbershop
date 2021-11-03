package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.CustomerDTO;
import cz.muni.fi.PA165.barbershop.api.facade.CustomerFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerFacadeImpl extends PersonFacadeImpl<Customer, CustomerDTO> implements CustomerFacade {

    @Autowired
    public CustomerFacadeImpl(CustomerService customerService, BeanMappingService beanMappingService) {
        super(Customer.class, CustomerDTO.class, customerService, beanMappingService);
    }
}
