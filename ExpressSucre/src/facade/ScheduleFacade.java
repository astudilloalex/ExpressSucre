package facade;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.AbstractFacade;
import model.Route;
import model.Schedule;

@Stateless
public class ScheduleFacade extends AbstractFacade<Schedule> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	private Query query;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public ScheduleFacade() {
		super(Schedule.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<Schedule> findByRoute(Route route, Timestamp currentDate){
		this.query=this.entityManager.createNamedQuery("Schedule.findByRoute", Schedule.class);
		this.query.setParameter("route", route);
		this.query.setParameter("date", currentDate);
		return this.query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Schedule> findByCurrentDate(Timestamp currentDate){
		this.query=this.entityManager.createNamedQuery("Schedule.findByCurrentDate", Schedule.class);
		this.query.setParameter("date", currentDate);
		return this.query.getResultList();
	}
}
