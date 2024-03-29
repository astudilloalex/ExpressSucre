package controller;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import data.JsfUtil;
import data.JsfUtil.PersistAction;
import facade.BusSeatFacade;
import model.BusSeat;

@Named("busSeatController")
@SessionScoped
public class BusSeatController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private BusSeatFacade ejbFacade;
	private List<BusSeat> items = null;
	private BusSeat selected;
	@Inject
	private ReservationController reservationController;

	// Constructor
	public BusSeatController() {
	}

	// Inner class
	@SuppressWarnings("rawtypes")
	@FacesConverter(forClass = BusSeat.class)
	public static class BusSeatControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			BusSeatController controller = (BusSeatController) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "busSeatController");
			return controller.getBusSeat(getKey(value));
		}

		java.lang.Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(java.lang.Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof BusSeat) {
				BusSeat o = (BusSeat) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), BusSeat.class.getName() });
				return null;
			}
		}

	}

	private BusSeatFacade getFacade() {
		return ejbFacade;
	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				if (persistAction != PersistAction.DELETE) {
					getFacade().edit(selected);
				} else {
					getFacade().remove(selected);
				}
				JsfUtil.addSuccessMessage(successMessage);
			} catch (EJBException ex) {
				String msg = "";
				Throwable cause = ex.getCause();
				if (cause != null) {
					msg = cause.getLocalizedMessage();
				}
				if (msg.length() > 0) {
					JsfUtil.addErrorMessage(msg);
				} else {
					JsfUtil.addErrorMessage(ex,
							ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	public BusSeat prepareCreate() {
		selected = new BusSeat();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BusSeatCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null; // Invalidate list of items to trigger re-query.
		}
		unselected();
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BusSeatUpdated"));
		unselected();
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BusSeatDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public void unselected() {
		this.selected = null;
	}

	// Getters And Setters
	public BusSeat getSelected() {
		return selected;
	}

	public void setSelected(BusSeat selected) {
		this.selected = selected;
	}

	public List<BusSeat> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	public BusSeat getBusSeat(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<BusSeat> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<BusSeat> getItemsAvailableSelectOne() {
		return getFacade().findByAvailable();
	}

	public List<BusSeat> getItemsAvailableBus() {
		if (this.reservationController.getSelected().getScheduleBean() != null) {
			return getFacade().findByBusActive(this.reservationController.getSelected().getScheduleBean().getBusBean());
		}
		return null;
	}
}