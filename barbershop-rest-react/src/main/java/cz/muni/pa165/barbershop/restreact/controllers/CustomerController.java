package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.fi.PA165.barbershop.api.dto.CustomerDTO;
import cz.muni.fi.PA165.barbershop.api.facade.CustomerFacade;
import cz.muni.pa165.barbershop.restreact.ApiUris;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceAlreadyExsistsException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceForbiddenException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceNotFoundException;
import cz.muni.pa165.barbershop.restreact.security.AccessChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_URI_CUSTOMERS)
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    CustomerFacade customerFacade;

    @Autowired
    public CustomerController (CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getCustomers() throws Exception{
        if (!AccessChecking.isAdmin()){
            throw new ResourceForbiddenException();
        }
        return customerFacade.getAll();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) throws Exception {
        try {
            var id = customerFacade.register(customerDTO, customerDTO.getPassword());
            /////// BE AWARE, THIS ID CAN BE NULL DUE TO IMPLEMENTATION IN FACADE, IF CAUSES ERROR - FIX FACADE
            return customerFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExsistsException();
        }
    }

    @GetMapping(value = "/find_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO findCustomerById(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            return customerFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/find_login/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO findCustomerByLogin(@PathVariable("login") String login) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserLogin(login))) {
            throw new ResourceForbiddenException();
        }
        try {
            return customerFacade.findByLogin(login);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomerById(@RequestBody CustomerDTO customerDTO) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(customerDTO.getId()))) {
            throw new ResourceForbiddenException();
        }
        try {
            customerFacade.update(customerDTO);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteCustomerById(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            CustomerDTO c = customerFacade.findById(id);
            customerFacade.delete(c);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
