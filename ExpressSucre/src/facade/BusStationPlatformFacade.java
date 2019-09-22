package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.BusStationPlatform;

@Stateless
public class BusStationPlatformFacade extends AbstractFacade<BusStationPlatform> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public BusStationPlatformFacade() {
		super(BusStationPlatform.class);
		// TODO Auto-generated constructor stub
	}
}
