package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;

	private Query query;
	private List<User> users;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}

	public UserFacade() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public User verifyUser(String username) {
		this.query = this.entityManager.createNamedQuery("User.findByUsername", User.class).setParameter("username", username);
		users = this.query.getResultList();
		if (!this.users.isEmpty()) {
			return this.users.get(0);
		}
		return null;
	}

}
