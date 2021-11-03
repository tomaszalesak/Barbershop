package cz.muni.fi.PA165.barbershop.persistence.dao;

import cz.muni.fi.PA165.barbershop.persistence.entity.MyService;

/**
 * Service DAO interface
 *
 * @author Jakub Zich
 */
public interface ServiceDao extends GenericBaseDao<MyService> {
    /**
     * Find Service by name
     *
     * @param name Service name
     * @return service
     */
    MyService findByName(String name);
}
