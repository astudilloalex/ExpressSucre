package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.AbstractFacade;
import model.Brand;

@Stateless
public class BrandFacade extends AbstractFacade<Brand> {

	@PersistenceContext(unitName = "ExpressSucre")
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	public BrandFacade() {
		super(Brand.class);
		// TODO Auto-generated constructor stub
	}
}
