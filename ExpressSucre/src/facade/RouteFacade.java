package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Route;

@Stateless
public class RouteFacade extends AbstractFacade<Route> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public RouteFacade() {
		super(Route.class);
		// TODO Auto-generated constructor stub
	}
}
