package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.fi.PA165.barbershop.api.dto.WorkingHoursCreateDTO;
import cz.muni.fi.PA165.barbershop.api.dto.WorkingHoursDTO;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.fi.PA165.barbershop.api.facade.WorkingHoursFacade;
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
@RequestMapping(ApiUris.ROOT_URI_WORKING_HOURS)
@CrossOrigin("http://localhost:3000")
public class WorkingHoursController {

    WorkingHoursFacade workingHoursFacade;
    EmployeeFacade employeeFacade;

    @Autowired
    public WorkingHoursController (WorkingHoursFacade workingHoursFacade, EmployeeFacade employeeFacade) {
        this.workingHoursFacade = workingHoursFacade;
        this.employeeFacade = employeeFacade;
    }

    @GetMapping(value = "/find_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final WorkingHoursDTO findWorkingHoursById(@PathVariable("id") long id) throws Exception{
        try {
            return workingHoursFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<WorkingHoursDTO> getWorkingHoursForEmployee(@PathVariable("id") long id) throws Exception {
        try {
            return workingHoursFacade.getWorkingHoursForEmployee(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/create/employee/{id_employee}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WorkingHoursDTO createWorkingHours(@RequestBody WorkingHoursCreateDTO workingHoursCreateDTO, @PathVariable("id_employee") long id_employee) throws Exception {
        if (!AccessChecking.hasUserId(id_employee)) {
            throw new ResourceForbiddenException();
        }
        try {
            var employeeDTO = employeeFacade.findById(id_employee);
            /////// BE AWARE, THIS ID CAN BE NULL DUE TO IMPLEMENTATION IN FACADE, IF CAUSES ERROR - FIX FACADE
            WorkingHoursDTO workingHoursDTO = new WorkingHoursDTO();
            workingHoursDTO.setFromTime(workingHoursCreateDTO.getFromTime());
            workingHoursDTO.setToTime(workingHoursCreateDTO.getToTime());
            workingHoursDTO.setEmployeeDTO(employeeDTO);
            var id = workingHoursFacade.create(workingHoursDTO);
            return workingHoursFacade.findById(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExsistsException();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public final String deleteWorkingHours(@PathVariable long id) throws Exception {
        try {
            var dto = workingHoursFacade.findById(id);
            if (!AccessChecking.hasUserId(dto.getEmployeeDTO().getId())) {
                throw new ResourceForbiddenException();
            }
            workingHoursFacade.delete(workingHoursFacade.findById(id));
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final String updateWorkingHours(@RequestBody WorkingHoursDTO workingHoursDTO) throws Exception {
        if (!AccessChecking.hasUserId(workingHoursDTO.getEmployeeDTO().getId())) {
            throw new ResourceForbiddenException();
        }
        try {
            workingHoursFacade.update(workingHoursDTO);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/employee/{id_employee}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<WorkingHoursDTO> getWorkingHoursByEmployeeTime(@PathVariable long id_employee,
                                                                     @PathVariable
                                                                     @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from,
                                                                     @PathVariable
                                                                     @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) throws Exception {
        try {
            var employeeDTO = employeeFacade.findById(id_employee);
            return workingHoursFacade.getWorkingHoursInPeriodForEmployee(from, to, employeeDTO);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }


}

