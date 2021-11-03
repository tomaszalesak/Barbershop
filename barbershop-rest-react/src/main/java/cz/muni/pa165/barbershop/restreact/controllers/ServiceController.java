package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.fi.PA165.barbershop.api.dto.ServiceDTO;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.fi.PA165.barbershop.api.facade.ServiceFacade;
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
@RequestMapping(ApiUris.ROOT_URI_SERVICES)
@CrossOrigin("http://localhost:3000")
public class ServiceController {

    ServiceFacade serviceFacade;
    EmployeeFacade employeeFacade;

    @Autowired
    public ServiceController (ServiceFacade serviceFacade, EmployeeFacade employeeFacade) {
        this.serviceFacade = serviceFacade;
        this.employeeFacade = employeeFacade;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceDTO> getServices() {
        return serviceFacade.getAllServices();
    }

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ServiceDTO> getServicesForEmployee(@PathVariable("id") long id) throws Exception {
        try {
            var employee = employeeFacade.findById(id);
            return List.copyOf(employee.getServiceDTOs());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO createService(@RequestBody ServiceDTO serviceDTO) throws Exception {
        if (!AccessChecking.isAdmin()){
            throw new ResourceForbiddenException();
        }
        try {
            var id = serviceFacade.create(serviceDTO);
            /////// BE AWARE, THIS ID CAN BE NULL DUE TO IMPLEMENTATION IN FACADE, IF CAUSES ERROR - FIX FACADE
            return serviceFacade.findServiceByID(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExsistsException();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public final String deleteService(@PathVariable("id") long id) throws Exception {
        if (!AccessChecking.isAdmin()){
            throw new ResourceForbiddenException();
        }
        try {
            serviceFacade.delete(id);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final String updateService(@RequestBody ServiceDTO serviceDTO) throws Exception {
        if (!AccessChecking.isAdmin()){
            throw new ResourceForbiddenException();
        }
        try {
            serviceFacade.update(serviceDTO);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/find_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO findServiceById(@PathVariable("id") long id) throws Exception {
        try {
            return serviceFacade.findServiceByID(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/find_name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO findServiceByName(@PathVariable("name") String name) throws Exception {
        try {
            return serviceFacade.findServiceByName(name);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
