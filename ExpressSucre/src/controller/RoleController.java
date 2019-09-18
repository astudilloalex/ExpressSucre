package controller;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import data.JsfUtil;
import data.JsfUtil.PersistAction;
import facade.RoleFacade;
import model.Role;

@Named("roleController")
@SessionScoped
public class RoleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private RoleFacade facade;
	private List<Role> items;
	private Role selected;

	public RoleController() {
		// TODO Auto-generated constructor stub
	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (this.selected != null) {
			setEmbeddableKeys();
			try {
				if (persistAction != PersistAction.DELETE) {
					getFacade().edit(selected);
				} else {
					getFacade().remove(selected);
				}
				JsfUtil.addSuccessMessage(successMessage);
			} catch (EJBException e) {
				String msg = "";
				Throwable cause = e.getCause();
				if (cause != null) {
					msg = cause.getLocalizedMessage();
				}
				if (msg.length() > 0) {
					JsfUtil.addErrorMessage(msg);
				} else {
					JsfUtil.addErrorMessage(e,
							ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
				JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	public Role prepareCreate() {
		this.selected = new Role();
		this.initializeEmbeddableKey();
		System.out.println("Prepare Create Open");
		return this.selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RoleCreated"));
		if (!JsfUtil.isValidationFailed()) {
			this.items = null;
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RoleUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RoleDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			this.selected = null;
			this.items = null;
		}
	}

	// Getters and setters
	public RoleFacade getFacade() {
		return this.facade;
	}

	public List<Role> getItems() {
		if (this.items == null) {
			this.items = getFacade().findAll();
		}
		return this.items;
	}

	public Role getSelected() {
		return this.selected;
	}

	public void setSelected(Role selected) {
		this.selected = selected;
	}
}
