package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.TimeFrameDTO;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.pa165.barbershop.restreact.ApiUris;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceAlreadyExsistsException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceForbiddenException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceNotFoundException;
import cz.muni.pa165.barbershop.restreact.security.AccessChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_URI_EMPLOYEES)
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

    EmployeeFacade employeeFacade;

    @Autowired
    public EmployeeController (EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getEmployees() {
        return employeeFacade.getAll();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) throws Exception {
        if (!AccessChecking.isAdmin()){
            throw new ResourceForbiddenException();
        }
        try {
            var id = employeeFacade.register(employeeDTO, employeeDTO.getPassword());
            /////// BE AWARE, THIS ID CAN BE NULL DUE TO IMPLEMENTATION IN FACADE, IF CAUSES ERROR - FIX FACADE
            return employeeFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExsistsException();
        }
    }

    @GetMapping(value = "/find_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final EmployeeDTO findEmployeeById(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            return employeeFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/find_login/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final EmployeeDTO findEmployeeByLogin(@PathVariable("login") String login) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserLogin(login))) {
            throw new ResourceForbiddenException();
        }
        try {
            return employeeFacade.findByLogin(login);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateEmployeeById(@RequestBody EmployeeDTO employeeDTO) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(employeeDTO.getId()))) {
            throw new ResourceForbiddenException();
        }
        try {
            employeeFacade.update(employeeDTO);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteEmployeeById(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            EmployeeDTO e = employeeFacade.findById(id);
            employeeFacade.delete(e);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }


    @GetMapping(value = "/find_name/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO findEmployeeByName(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname) throws Exception {
        try {
            return employeeFacade.findByName(firstname, lastname).get(0);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/free_time/{id_employee}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimeFrameDTO> getFreeTimeInPeriod (@PathVariable long id_employee,
                                                                            @PathVariable
                                                                            @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from,
                                                                            @PathVariable
                                                                            @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) throws Exception {
        try {
            var employeeDTO = employeeFacade.findById(id_employee);
            return employeeFacade.getFreeTimeInPeriod(from, to, employeeDTO);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
