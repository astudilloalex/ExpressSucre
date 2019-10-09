package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.PersonUser;
import model.User;

@Stateless
public class PersonUserFacade extends AbstractFacade<PersonUser> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}

	public PersonUserFacade() {
		super(PersonUser.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<PersonUser> findByUser(User user){
		Query query=this.entityManager.createNamedQuery("PersonUser.findByUser", PersonUser.class);
		query.setParameter("user", user);
		return query.getResultList();
	}
}
