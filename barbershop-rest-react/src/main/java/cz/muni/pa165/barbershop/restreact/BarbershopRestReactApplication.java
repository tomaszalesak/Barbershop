package cz.muni.pa165.barbershop.restreact;

import cz.muni.PA165.barbershop.sampleData.SampleDataConfiguration;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ServiceConfiguration.class, SampleDataConfiguration.class})
public class BarbershopRestReactApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarbershopRestReactApplication.class, args);
    }

}
