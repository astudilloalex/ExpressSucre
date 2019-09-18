package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class AbstractFacade<T> {
	private Class<T> entityClass;
	
	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	protected abstract EntityManager getEntityManager();
	
	public void create(T entity) {
		getEntityManager().persist(entity);
	}
	
	public void edit(T entity) {
		getEntityManager().merge(entity);
	}
	
	public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll(){
    	CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
    	criteriaQuery.select(criteriaQuery.from(this.entityClass));
    	return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findRange(int[] range) {
    	CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
    	criteriaQuery.select(criteriaQuery.from(this.entityClass));
    	Query query=getEntityManager().createQuery(criteriaQuery);
    	query.setMaxResults(range[1] - range[0] + 1);
    	query.setFirstResult(range[0]);
        return query.getResultList();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = criteriaQuery.from(this.entityClass);
        criteriaQuery.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(criteriaQuery);
        return ((Long) q.getSingleResult()).intValue();
    }
}