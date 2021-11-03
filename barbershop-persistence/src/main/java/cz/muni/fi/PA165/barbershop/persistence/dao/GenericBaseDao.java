package cz.muni.fi.PA165.barbershop.persistence.dao;


import java.util.List;

/**
 * @author Tomáš Zálešák
 */
public interface GenericBaseDao<T> {
    void create(T e);

    /**
     * Finds entity with given id
     *
     * @param id of the entity to be found
     * @return found entity
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     */
    T findById(Long id);

    /**
     * Finds and returns all entities
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Updates given entity
     *
     * @param e entity to be updated
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     */
    void update(T e);

    /**
     * Deletes entity with given id
     *
     * @param id of entity to be deleted
     * @throws IllegalArgumentException - if the instance is not an entity or is a detached entity
     */
    void delete(Long id);

    /**
     * Finds and returns entities according to idList
     * @param idList list of requested ids
     * @return List of entities
     * @throws IllegalArgumentException - idList is null
     */
    List<T> findWithIds(List<Long> idList);
}
