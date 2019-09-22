package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Seat;

@Stateless
public class SeatFacade extends AbstractFacade<Seat> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public SeatFacade() {
		super(Seat.class);
		// TODO Auto-generated constructor stub
	}
}
