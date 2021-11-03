package cz.muni.pa165.barbershop.restreact.controllers;

import cz.muni.pa165.barbershop.restreact.ApiUris;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class MainController {
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        return Map.of(
                "api_uri", ApiUris.ROOT_URI_AUTH,
                "reservations_uri", ApiUris.ROOT_URI_RESERVATIONS,
                "customers_uri", ApiUris.ROOT_URI_CUSTOMERS,
                "employees_uri", ApiUris.ROOT_URI_EMPLOYEES,
                "working_hours_uri", ApiUris.ROOT_URI_WORKING_HOURS,
                "services_uri", ApiUris.ROOT_URI_SERVICES);

    }
}
