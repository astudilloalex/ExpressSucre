package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.BusStation;

@Stateless
public class BusStationFacade extends AbstractFacade<BusStation> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public BusStationFacade() {
		super(BusStation.class);
		// TODO Auto-generated constructor stub
	}
}
