package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.Person;

public abstract class PersonDaoImpl<T extends Person> extends GenericBaseDaoImpl<T> implements PersonDao<T> {

    public PersonDaoImpl(Class<T> type) {
        super(type);
    }
}
