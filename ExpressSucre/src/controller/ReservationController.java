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
import javax.inject.Named;

import data.JsfUtil;
import data.JsfUtil.PersistAction;
import facade.ReservationFacade;
import model.Reservation;

@Named("reservationController")
@SessionScoped
public class ReservationController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
    private ReservationFacade ejbFacade;
    private List<Reservation> items = null;
    private Reservation selected;

    //Constructor
    public ReservationController() {
    }
    
    //Inner class
    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Reservation.class)
    public static class ReservationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReservationController controller = (ReservationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reservationController");
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Reservation.class.getName()});
                return null;
            }
        }

    }
    
    private ReservationFacade getFacade() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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

    public Reservation prepareCreate() {
        selected = new Reservation();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ReservationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        unselected();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ReservationUpdated"));
        unselected();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ReservationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void unselected() {
    	this.selected=null;
    }
    
    //Getters And Setters
    public Reservation getSelected() {
        return selected;
    }

    public void setSelected(Reservation selected) {
        this.selected = selected;
    }
    
    public List<Reservation> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public Reservation getReservation(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Reservation> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Reservation> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
}