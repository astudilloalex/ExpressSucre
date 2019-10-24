package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import data.EncryptPassword;
import facade.PersonFacade;
import facade.PersonUserFacade;
import facade.RoleFacade;
import facade.UserFacade;
import model.Person;
import model.PersonUser;
import model.User;

@Named("registerController")
@SessionScoped
public class RegisterController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginController controller;
	@EJB
	private PersonFacade personFacade;
	@EJB
	private UserFacade userFacade;
	@EJB
	private RoleFacade roleFacade;
	@EJB
	private PersonUserFacade personUserFacade;
	
	private PersonUser personUser;
	private Person person;
	private User user;
	private String password;
	private String repeatPassword;
	private EncryptPassword encryptPassword;
	
	private void createPersonUser() {
		this.personUser=new PersonUser();
		this.personUser.setUser(this.user);
		this.personUser.setIdCard(this.person.getIdCard());
		this.personUser.setFirstName(this.person.getFirstName());
		this.personUser.setLastName(this.person.getLastName());
		this.personUserFacade.create(this.personUser);
	}
	
	@PostConstruct
	public void verifySession() {
		if (this.controller.getUser() != null) {
			try {
				if(this.controller.getUser().getRoleBean().getCode().equals("ADM") || this.controller.getUser().getRoleBean().getCode().equals("EMP")) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/ExpressSucre/faces/administrator/list.xhtml");
				}else {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/ExpressSucre/faces/customer/list.xhtml");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			this.encryptPassword=new EncryptPassword();
			this.person=new Person();
			this.user=new User();
			this.user.setRoleBean(this.roleFacade.customer());
		}
	}
	
	public String register() {
		if(this.password.equals(this.repeatPassword)) {
			this.personFacade.create(this.person);
			this.user.setPassword(this.encryptPassword.getEncriptPassword(this.password));
			this.user.setPersonBean(this.person);
			this.userFacade.create(this.user);
			this.createPersonUser();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							ResourceBundle.getBundle("/Bundle").getString("RegisterCompleted"),
							ResourceBundle.getBundle("/Bundle").getString("RegisterCompletedMessage")));
			return "/login?faces-redirect=true";
		}else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							ResourceBundle.getBundle("/Bundle").getString("RegisterPasswordDoNotMatch"),
							ResourceBundle.getBundle("/Bundle").getString("RegisterPasswordDoNotMatch_Message")));
		}
		return null;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
