package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.PersonUser;
import model.Reservation;

@Stateless
public class ReservationFacade extends AbstractFacade<Reservation> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	private Query query;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public ReservationFacade() {
		super(Reservation.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> findByPersonUser(List<PersonUser> personUsers){
		this.query=this.entityManager.createNamedQuery("Reservation.findByPerson", Reservation.class);
		this.query.setParameter("personUsers", personUsers);
		return this.query.getResultList();
	}
}
