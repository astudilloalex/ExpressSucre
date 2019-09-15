package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Role;

@Stateless
public class RolesFacade extends AbstractFacade<Role> {

	@PersistenceContext(name = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public RolesFacade() {
		super(Role.class);
		// TODO Auto-generated constructor stub
	}
}
