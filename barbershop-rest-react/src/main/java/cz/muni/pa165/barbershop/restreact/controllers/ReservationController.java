package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.fi.PA165.barbershop.api.dto.ReservationDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ReservationUpdateFeedbackDTO;
import cz.muni.fi.PA165.barbershop.api.dto.ReservationUpdateStatusDTO;
import cz.muni.fi.PA165.barbershop.api.dto.TimeFrameDTO;
import cz.muni.fi.PA165.barbershop.api.facade.EmployeeFacade;
import cz.muni.fi.PA165.barbershop.api.facade.ReservationFacade;
import cz.muni.pa165.barbershop.restreact.ApiUris;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceAlreadyExsistsException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceForbiddenException;
import cz.muni.pa165.barbershop.restreact.exceptions.ResourceNotFoundException;
import cz.muni.pa165.barbershop.restreact.security.AccessChecking;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_URI_RESERVATIONS)
@CrossOrigin("http://localhost:3000")
public class ReservationController {

    ReservationFacade reservationFacade;
    EmployeeFacade employeeFacade;

    public ReservationController (ReservationFacade reservationFacade, EmployeeFacade employeeFacade) {
        this.reservationFacade = reservationFacade;
        this.employeeFacade = employeeFacade;
    }

    @GetMapping(value = "/find_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationDTO findReservationById(@PathVariable("id") long id) throws Exception{
        try {
            var reservation = reservationFacade.getReservationWithID(id);
            if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(reservation.getCustomerDTO().getId()) ||
                    AccessChecking.hasUserId(reservation.getEmployeeDTO().getId()))) {
                throw new ResourceForbiddenException();
            }
            if (reservation == null){
                throw new ResourceNotFoundException();
            }else
                return reservation ;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ReservationDTO> getReservationsForCustomer(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            return reservationFacade.getAllReservationsForCustomer(id);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ReservationDTO> getReservationsForEmployee(@PathVariable("id") long id) throws Exception {
        if (!(AccessChecking.isAdmin() || AccessChecking.hasUserId(id))) {
            throw new ResourceForbiddenException();
        }
        try {
            return reservationFacade.getAllReservationsForEmployee(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
    //ID in consumed JSON should be NULL
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) throws Exception {
        if (!(AccessChecking.hasUserId(reservationDTO.getCustomerDTO().getId()))) {
            throw new ResourceForbiddenException();
        }
        try {
            var id = reservationFacade.createReservation(reservationDTO);
            /////// BE AWARE, THIS ID CAN BE NULL DUE TO IMPLEMENTATION IN FACADE, IF CAUSES ERROR - FIX FACADE
            return reservationFacade.getReservationWithID(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExsistsException();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public final String deleteReservation(@PathVariable long id) throws Exception {
        try {
            var reservation = reservationFacade.getReservationWithID(id);
            if (!(AccessChecking.hasUserId(reservation.getEmployeeDTO().getId()) ||
                    AccessChecking.hasUserId(reservation.getCustomerDTO().getId()))) {
                throw new ResourceForbiddenException();
            }
            reservationFacade.cancelReservation(id);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping(value = "/update/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final String updateReservationStatus(@RequestBody ReservationUpdateStatusDTO reservationUpdateStatusDTO) throws Exception {
        try {
            var reservation = reservationFacade.getReservationWithID(reservationUpdateStatusDTO.getId());
            if (!(AccessChecking.hasUserId(reservation.getEmployeeDTO().getId()))) {
                throw new ResourceForbiddenException();
            }
            reservationFacade.setReservationCompletion(reservationUpdateStatusDTO.getId(), reservationUpdateStatusDTO.getStatus());
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping(value = "/update/feedback", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final String updateReservationFeedback(@RequestBody ReservationUpdateFeedbackDTO reservationUpdateFeedbackDTO) throws Exception {
        try {
            var reservation = reservationFacade.getReservationWithID(reservationUpdateFeedbackDTO.getId());
            if (!(AccessChecking.hasUserId(reservation.getCustomerDTO().getId()))) {
                throw new ResourceForbiddenException();
            }
            reservation.setFeedbackText(reservationUpdateFeedbackDTO.getFeedbackText());
            reservationFacade.addFeedback(reservation);
            return "Success";
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value = "/employee/{id_employee}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimeFrameDTO> getReservationTimesInPeriodForEmployee (@PathVariable long id_employee,
                                                                            @PathVariable
                                                                      @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from,
                                                                            @PathVariable
                                                                      @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) throws Exception {
        try {
            var employeeDTO = employeeFacade.findById(id_employee);
            var reservations = reservationFacade.getReservationsInPeriodForEmployee(from, to, employeeDTO);
            var timeFrames = new ArrayList<TimeFrameDTO>();
            for (ReservationDTO reservation: reservations) {
                timeFrames.add(new TimeFrameDTO(reservation.getFromTime(), reservation.getToTime()));
            }
            return  timeFrames;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
