package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import model.User;

public class TemplateController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean administratorDisabled;
	private String user;
	
	private boolean isAdmin(User user) {
		return true;
	}
	
	public void verifySession() {
		try {
			User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			if (user == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/DataBaseProject/faces/index.xhtml");
			} else {
				this.user = user.getUsername();
				this.administratorDisabled = !isAdmin(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validateAdmin() {
		if (this.administratorDisabled) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/DataBaseProject/faces/template.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String logoutSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", null);
		return "/index?faces-redirect=true";
	}
	
	//Getters and Setters
	public boolean isAdministratorDisabled() {
		return administratorDisabled;
	}
}