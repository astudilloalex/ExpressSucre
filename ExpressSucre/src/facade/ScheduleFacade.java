package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Schedule;

@Stateless
public class ScheduleFacade extends AbstractFacade<Schedule> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public ScheduleFacade() {
		super(Schedule.class);
		// TODO Auto-generated constructor stub
	}
}
