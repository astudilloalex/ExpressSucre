package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Bus;

@Stateless
public class BusFacade extends AbstractFacade<Bus> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public BusFacade() {
		super(Bus.class);
		// TODO Auto-generated constructor stub
	}
}
