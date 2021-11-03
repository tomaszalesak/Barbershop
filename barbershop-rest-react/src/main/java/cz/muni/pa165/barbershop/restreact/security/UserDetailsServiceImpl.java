package cz.muni.pa165.barbershop.restreact.security;

import cz.muni.fi.PA165.barbershop.api.dto.PersonDTO;
import cz.muni.fi.PA165.barbershop.api.facade.CustomerFacade;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private EmployeeFacade employeeFacade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonDTO customerDTO = null;
        PersonDTO employeeDTO = null;
        try {
            customerDTO = customerFacade.findByLogin(s);
        } catch (Exception ignored){}
        try {
            employeeDTO = employeeFacade.findByLogin(s);
        } catch (Exception ignored){}


        if (customerDTO == null && employeeDTO == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        if(customerDTO != null && employeeDTO == null)
            return new UserDetailsImpl(customerDTO, Customer.class);
        else
            return new UserDetailsImpl(employeeDTO, Employee.class);
    }
}
