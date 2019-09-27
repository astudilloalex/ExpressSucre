package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("templateController")
@SessionScoped
public class TemplateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginController loginController;
	private boolean administratorDisabled;

	public void verifyAdministrator() {
		try {
			if (this.loginController.getUser() == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ExpressSucre/faces/login.xhtml");
			} else {
				if (!this.loginController.getUser().getRoleBean().getCode().equals("ADM") && this.loginController.getUser().getRoleBean().getCode().equals("EMP")) {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/ExpressSucre/faces/administrator/list.xhtml");
				}else if(!this.loginController.getUser().getRoleBean().getCode().equals("ADM") && this.loginController.getUser().getRoleBean().getCode().equals("CLI")) {
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/ExpressSucre/faces/customer/list.xhtml");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isAdminDisabled() {
		if (this.loginController.getUser() != null
				&& this.loginController.getUser().getRoleBean().getCode().equals("ADM")) {
			return false;
		}
		return true;
	}

	public void verifySession() {
		try {
			if (this.loginController.getUser() == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ExpressSucre/faces/login.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verifyLogin() {
		try {
			if (this.loginController.getUser() != null) {
					switch (this.loginController.getUser().getRoleBean().getCode()) {
					case "ADM":
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/ExpressSucre/faces/administrator/list.xhtml");
						break;
					case "EMP":
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/ExpressSucre/faces/administrator/list.xhtml");
						break;
					default:
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/ExpressSucre/faces/customer/list.xhtml");
						break;
					}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void validateAdmin() {
		if (this.administratorDisabled) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/DataBaseProject/faces/administrator/list.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String logoutSession() {
		this.loginController.setUser(null);
		return "/index?faces-redirect=true";
	}

	// Getters and Setters
	public boolean isAdministratorDisabled() {
		return administratorDisabled;
	}
}