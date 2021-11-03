package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Person;

public interface PersonDao<T extends Person> extends GenericBaseDao<T>{
    T findByLogin(String login);
}
