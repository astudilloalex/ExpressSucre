package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Reservation;

@Stateless
public class ReservationFacade extends AbstractFacade<Reservation> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public ReservationFacade() {
		super(Reservation.class);
		// TODO Auto-generated constructor stub
	}
}
