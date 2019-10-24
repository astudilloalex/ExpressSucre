package controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import data.JsfUtil;
import data.JsfUtil.PersistAction;
import data.pdf.GeneratePDF;
import facade.BusSeatFacade;
import facade.PersonUserFacade;
import facade.ReservationFacade;
import facade.RouteFacade;
import facade.ScheduleFacade;
import model.BusSeat;
import model.BusStation;
import model.City;
import model.PersonUser;
import model.Reservation;
import model.Route;
import model.Schedule;

@Named("customerController")
@SessionScoped
public class CustomerController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ReservationFacade reservationFacade;
	@EJB
	private PersonUserFacade personUserFacade;
	@EJB
	private ScheduleFacade scheduleFacade;
	@EJB
	private RouteFacade routeFacade;
	@EJB
	private BusSeatFacade busSeatFacade;
	@Inject
	private LoginController loginController;
	private Reservation selected;

	private City cityOrigin;
	private City cityDestination;
	private BusStation busStation1;
	private BusStation busStation2;
	private PersonUser personUser;
	private BusSeat busSeat;
	private List<Reservation> reservations;
	private List<BusSeat> busSeats;
	private List<Schedule> schedules;
	private GeneratePDF generatePDF;

	// Constructor
	public CustomerController() {
	}

	// Inner class
	@SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Reservation.class)
	public static class ReservationControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			CustomerController controller = (CustomerController) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "customerController");
			return controller.getReservation(getKey(value));
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
			if (object instanceof Reservation) {
				Reservation o = (Reservation) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Reservation.class.getName() });
				return null;
			}
		}

	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				if (persistAction != PersistAction.DELETE) {
					getReservationFacade().edit(selected);
				} else {
					getReservationFacade().remove(selected);
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

	private ReservationFacade getReservationFacade() {
		return reservationFacade;
	}

	private PersonUserFacade getPersonUserFacade() {
		return personUserFacade;
	}

	private ScheduleFacade getScheduleFacade() {
		return scheduleFacade;
	}

	private RouteFacade getRouteFacade() {
		return routeFacade;
	}

	private BusSeatFacade getBusSeatFacade() {
		return busSeatFacade;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	public PersonUser prepareCreatePerson() {
		this.personUser = new PersonUser();
		initializeEmbeddableKey();
		return personUser;
	}

	public Reservation prepareCreate() {
		selected = new Reservation();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		this.selected.setPersonUserBean(this.personUser);
		this.selected.getBusSeatBean().setState(false);
		getBusSeatFacade().edit(this.selected.getBusSeatBean());
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ReservationCreated"));
		unselected();
	}

	public void createPersonUser() {
		this.personUser.setUser(this.loginController.getUser());
		this.personUserFacade.create(this.personUser);
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ReservationUpdated"));
		unselected();
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ReservationDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
		}
	}

	public StreamedContent getPrintReservation() {
		this.generatePDF = new GeneratePDF();
		InputStream stream = new ByteArrayInputStream(this.generatePDF.getReservation(this.selected));
		return new DefaultStreamedContent(stream, "application/pdf", "reservation.pdf");
	}

	public void unselected() {
		this.selected = null;
	}

	public void unselectedPersonUser() {
		this.personUser = null;
	}

	public List<PersonUser> getPersonUsers() {
		return getPersonUserFacade().findByUser(this.loginController.getUser());
	}

	public List<Route> getRoutes() {
		return getRouteFacade().findByBusStations(this.busStation1, this.busStation2);
	}

	// Getters And Setters
	public Reservation getSelected() {
		return selected;
	}

	public void setSelected(Reservation selected) {
		this.selected = selected;
	}

	public Reservation getReservation(java.lang.Integer id) {
		return getReservationFacade().find(id);
	}

	public List<Reservation> getItemsAvailableSelectMany() {
		return getReservationFacade().findAll();
	}

	public List<Reservation> getItemsAvailableSelectOne() {
		return getReservationFacade().findAll();
	}

	public City getCityOrigin() {
		return cityOrigin;
	}

	public void setCityOrigin(City cityOrigin) {
		this.cityOrigin = cityOrigin;
	}

	public City getCityDestination() {
		return cityDestination;
	}

	public void setCityDestination(City cityDestination) {
		this.cityDestination = cityDestination;
	}

	public PersonUser getPersonUser() {
		return personUser;
	}

	public void setPersonUser(PersonUser personUser) {
		this.personUser = personUser;
	}

	public List<Schedule> getSchedules() {
		if (!this.getRoutes().isEmpty()) {
			this.schedules = getScheduleFacade().findByRoute(getRoutes().get(0), new Timestamp(new Date().getTime()));
		}
		return schedules;
	}

	public BusStation getBusStation1() {
		return busStation1;
	}

	public void setBusStation1(BusStation busStation1) {
		this.busStation1 = busStation1;
	}

	public BusStation getBusStation2() {
		return busStation2;
	}

	public void setBusStation2(BusStation busStation2) {
		this.busStation2 = busStation2;
	}

	public BusSeat getBusSeat() {
		return busSeat;
	}

	public void setBusSeat(BusSeat busSeat) {
		this.busSeat = busSeat;
	}

	public List<Reservation> getReservations() {
		this.reservations = getReservationFacade()
				.findByPersonUser(getPersonUserFacade().findByUser(loginController.getUser()));
		return reservations;
	}

	public List<BusSeat> getBusSeats() {
		if (this.selected != null) {
			if (this.selected.getScheduleBean() != null) {
				this.busSeats = getBusSeatFacade().findByBusActive(this.selected.getScheduleBean().getBusBean());
			}
		}
		return busSeats;
	}
}