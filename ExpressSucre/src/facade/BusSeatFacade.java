package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.BusSeat;

@Stateless
public class BusSeatFacade extends AbstractFacade<BusSeat> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public BusSeatFacade() {
		super(BusSeat.class);
		// TODO Auto-generated constructor stub
	}
}
