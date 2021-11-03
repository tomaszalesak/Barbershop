package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.entity.Person;

import java.util.List;

/**
 * An interface that defines a service access to the Customer, Employee entities.
 *
 * @author Jan Sladký
 */
public interface PersonService<T extends Person> {

    /**
     * Register the given person with the given unencrypted password.
     */
    void register(T p, String unencryptedPassword);

    /**
     * Get all registered persons
     */
    List<T> getAll();

    /**
     * Try to authenticate a person. Return true only if the hashed password matches the records.
     */
    boolean authenticate(T p, String password);

    /**
     * Try to find person by id and return person
     */
    T findById(Long personId);

    /**
     * Try to find person by login and return person
     */
    T findByLogin(String personLogin);

    /**
     * Delete person by login
     */
    void update(T person);

    /**
     * Delete person by login
     */
    void delete(T person);


}
