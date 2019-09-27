package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.Role;

@Stateless
public class RoleFacade extends AbstractFacade<Role> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	private Query query;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public RoleFacade() {
		super(Role.class);
		// TODO Auto-generated constructor stub
	}
	
	public Role customer() {
		this.query=this.entityManager.createNamedQuery("Role.findByCode", Role.class);
		this.query.setParameter("code", "CLI");
		return (Role) this.query.getResultList().get(0);
	}
}
