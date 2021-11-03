package cz.muni.fi.PA165.barbershop.api.facade;

import cz.muni.fi.PA165.barbershop.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.PA165.barbershop.api.dto.PersonDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PersonFacade<D extends PersonDTO> {

    /**
     * Register the given person with the given unencrypted password.
     */
    Long register(D u, String unencryptedPassword);

    /**
     * Get all registered persons
     */
    List<D> getAll();

    /**
     * Try to authenticate a person. Return true only if the hashed password matches the records.
     */
    boolean authenticate(PersonAuthenticateDTO u);


    D findById(Long userId);

    D findByLogin(String login);

    void update(D person);

    void delete(D person);

}
