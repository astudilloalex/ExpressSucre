package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.Bus;
import model.BusSeat;

@Stateless
public class BusSeatFacade extends AbstractFacade<BusSeat> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;

	private Query query;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}

	public BusSeatFacade() {
		super(BusSeat.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<BusSeat> findByBusActive(Bus bus) {
		this.query = this.entityManager.createNamedQuery("BusSeat.findByBusActive", BusSeat.class);
		this.query.setParameter("bus", bus);
		return this.query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<BusSeat> findByAvailable(){
		this.query = this.entityManager.createNamedQuery("BusSeat.findByAvailable", BusSeat.class);
		return this.query.getResultList();
	}
}
