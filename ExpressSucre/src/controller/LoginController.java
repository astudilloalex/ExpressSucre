package controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import data.EncryptPassword;
import facade.UserFacade;
import model.User;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacade ejbFacade;
	private String username;
	private String password;
	private User user;
	private EncryptPassword encryptPassword;

	public LoginController() {
		this.encryptPassword = new EncryptPassword();
	}

	public String authenticate() {
		this.user = this.ejbFacade.verifyUser(this.username);
		if (this.user != null) {
			if (this.user.getPassword().equals(this.encryptPassword.getEncriptPassword(this.password))) {
				switch (this.user.getRoleBean().getCode()) {
				case "ADM":
					return "/administrator/list?faces-redirect=true";
				case "EMP":
					return "/administrator/list?faces-redirect=true";
				default:
					return "/customer/list?faces-redirect=true";
				}
			} else {
				this.user = null;
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							ResourceBundle.getBundle("/Bundle").getString("UserLoginPasswordError"),
							ResourceBundle.getBundle("/Bundle").getString("PasswordIncorrect")));
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL,
						ResourceBundle.getBundle("/Bundle").getString("UserLoginError"),
						ResourceBundle.getBundle("/Bundle").getString("UserDoesNotExist")));
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
