package cz.muni.fi.PA165.barbershop.persistence.dao;

import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Tomáš Zálešák
 */
@NoRepositoryBean
public abstract class GenericBaseDaoImpl<T> implements GenericBaseDao<T> {

    @PersistenceContext
    EntityManager em;

    private Class<T> type;

    public GenericBaseDaoImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T e) {
        em.persist(e);
    }

    @Override
    public T findById(Long id) {
        return em.find(type, id);
    }

    @Override
    public void update(T e) {
        em.merge(e);
    }

    @Override
    public void delete(Long id) {
        em.remove(em.getReference(type, id));
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("from " + type.getName(), type).getResultList();
    }

    @Override
    public List<T> findWithIds(List<Long> idList) {
        if (idList == null) {
            throw new IllegalArgumentException("idList is null");
        }
        try {
            return em.createQuery(
                    "select e from " + type.getName() + " e where e.id in :ids "
                    , type).setParameter("ids", idList)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }
}
