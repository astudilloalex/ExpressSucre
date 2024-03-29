package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Platform;

@Stateless
public class PlatformFacade extends AbstractFacade<Platform> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public PlatformFacade() {
		super(Platform.class);
		// TODO Auto-generated constructor stub
	}
}
