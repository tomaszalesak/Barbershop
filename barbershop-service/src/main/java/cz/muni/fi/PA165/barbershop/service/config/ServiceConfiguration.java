package cz.muni.fi.PA165.barbershop.service.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import cz.muni.fi.PA165.barbershop.persistence.PersistenceApplicationContext;
import cz.muni.fi.PA165.barbershop.service.ReservationService;
import cz.muni.fi.PA165.barbershop.service.facade.ReservationFacadeImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Import({PersistenceApplicationContext.class})
@ComponentScan(basePackageClasses={ReservationService.class, ReservationFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer(){
        Mapper dozer = DozerBeanMapperBuilder.create()
                .withMappingFiles(List.of("dozer_configuration.xml"))
                .build();
        return dozer;
    }

    @Bean
    public PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder();
    }
}