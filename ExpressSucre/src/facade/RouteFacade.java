package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.BusStation;
import model.Route;

@Stateless
public class RouteFacade extends AbstractFacade<Route> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	Query query;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public RouteFacade() {
		super(Route.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<Route> findByBusStations(BusStation busStation1, BusStation busStation2){
		query=this.entityManager.createNamedQuery("Route.findByBusStations", Route.class);
		query.setParameter("busStation1", busStation1);
		query.setParameter("busStation2", busStation2);
		return this.query.getResultList();
	}
}
