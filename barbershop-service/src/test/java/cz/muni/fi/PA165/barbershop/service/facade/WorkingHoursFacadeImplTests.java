package cz.muni.fi.PA165.barbershop.service.facade;


import cz.muni.fi.PA165.barbershop.api.dto.EmployeeDTO;
import cz.muni.fi.PA165.barbershop.api.dto.WorkingHoursDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import cz.muni.fi.PA165.barbershop.persistence.entity.WorkingHours;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.WorkingHoursService;
import cz.muni.fi.PA165.barbershop.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Tomáš Zálešák
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class WorkingHoursFacadeImplTests {

    @Mock
    WorkingHoursService workingHoursService;

    @Mock
    BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private WorkingHoursFacadeImpl workingHoursFacade;

    private WorkingHours workingHours;
    private WorkingHoursDTO workingHoursDTO;
    private WorkingHoursDTO workingHoursDTO1;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUp() {
        var employee = new Employee();

        workingHours = new WorkingHours();
        workingHours.setId(1L);
        workingHours.setFromTime(LocalDateTime.MIN);
        workingHours.setToTime(LocalDateTime.MAX);
        workingHours.setEmployee(employee);

        var employeeDTO = new EmployeeDTO();
        workingHoursDTO = new WorkingHoursDTO();
        workingHoursDTO.setId(1L);
        workingHoursDTO.setFromTime(LocalDateTime.MIN);
        workingHoursDTO.setToTime(LocalDateTime.MAX);
        workingHoursDTO.setEmployeeDTO(employeeDTO);

        workingHoursDTO1 = new WorkingHoursDTO();
        workingHoursDTO1.setFromTime(LocalDateTime.MIN);
        workingHoursDTO1.setToTime(LocalDateTime.MAX);
        workingHoursDTO1.setEmployeeDTO(employeeDTO);

        Mockito.when(beanMappingService.mapTo(workingHours, WorkingHoursDTO.class)).thenReturn(workingHoursDTO);
        Mockito.when(beanMappingService.mapTo(workingHoursDTO, WorkingHours.class)).thenReturn((workingHours));
        Mockito.when(beanMappingService.mapTo(workingHoursDTO1, WorkingHours.class)).thenReturn((workingHours));

    }

    @Test
    public void create() {
        workingHoursFacade.create(workingHoursDTO1);
        verify(workingHoursService).create(workingHours);
    }

    @Test
    public void findById() {
        Mockito.when(workingHoursService.findById(1L)).thenReturn(workingHours);
        Assert.assertEquals(workingHoursFacade.findById(1L), workingHoursDTO);
    }

    @Test
    public void findAll() {
        Mockito.when(workingHoursService.findAll()).thenReturn(Collections.singletonList(workingHours));
        Mockito
                .when(beanMappingService.mapTo(Collections.singletonList(workingHours), WorkingHoursDTO.class))
                .thenReturn(Collections.singletonList(workingHoursDTO));

        Collection<WorkingHoursDTO> workingHoursDTOS = workingHoursFacade.findAll();

        assertThat(workingHoursDTOS.size()).isEqualTo(1);
        assertThat(workingHoursDTOS).containsOnly(workingHoursDTO);
        verify(workingHoursService).findAll();
    }

    @Test
    public void update() {
        workingHoursFacade.update(workingHoursDTO);
        verify(workingHoursService).update(workingHours);
    }

    @Test
    public void delete() {
        workingHoursFacade.delete(workingHoursDTO);
        verify(workingHoursService).delete(workingHours);
    }
}
