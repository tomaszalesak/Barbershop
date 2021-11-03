package cz.muni.fi.PA165.barbershop.service.facade;

import cz.muni.fi.PA165.barbershop.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.PA165.barbershop.api.dto.PersonDTO;
import cz.muni.fi.PA165.barbershop.api.facade.PersonFacade;
import cz.muni.fi.PA165.barbershop.persistence.entity.Person;
import cz.muni.fi.PA165.barbershop.service.BeanMappingService;
import cz.muni.fi.PA165.barbershop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class PersonFacadeImpl<T extends Person, D extends PersonDTO> implements PersonFacade<D> {

    private PersonService<T> personService;

    private BeanMappingService beanMappingService;

    private Class<T> typeT;
    private Class<D> typeD;


    public PersonFacadeImpl(Class<T> personEntityClass, Class<D> personDTOClass, PersonService<T> personService, BeanMappingService beanMappingService) {
        this.personService = personService;
        this.beanMappingService = beanMappingService;
        typeT = personEntityClass;
        typeD = personDTOClass;
    }

    @Override
    public Long register(D personDTO, String unencryptedPassword) {
        T personEntity = beanMappingService.mapTo(personDTO, typeT);
        personService.register(personEntity, unencryptedPassword);
        personDTO.setId(personEntity.getId());
        return personEntity.getId();
    }

    @Override
    public List<D> getAll() {
        return beanMappingService.mapTo(personService.getAll(), typeD);
    }

    @Override
    public boolean authenticate(PersonAuthenticateDTO person) {
        return personService.authenticate(
                personService.findByLogin(person.getLogin()), person.getPassword());
    }

    @Override
    public D findById(Long personId) {
        T person = personService.findById(personId);
        return (person == null) ? null : beanMappingService.mapTo(person, typeD);
    }

    @Override
    public D findByLogin(String login) {
        T person = personService.findByLogin(login);
        return (person == null) ? null : beanMappingService.mapTo(person, typeD);
    }

    @Override
    public void update(D person) {
        personService.update(beanMappingService.mapTo(person, typeT));
    }

    @Override
    public void delete(D person) {
        personService.delete(beanMappingService.mapTo(person, typeT));
    }
}
