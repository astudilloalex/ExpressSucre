package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user", schema = "public")
@NamedQueries({ 
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username=:username")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String password;

	private String username;

	//bi-directional many-to-one association to PersonUser
	@OneToMany(mappedBy="user")
	private List<PersonUser> personUsers;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person")
	private Person personBean;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role")
	private Role roleBean;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<PersonUser> getPersonUsers() {
		return this.personUsers;
	}

	public void setPersonUsers(List<PersonUser> personUsers) {
		this.personUsers = personUsers;
	}

	public PersonUser addPersonUser(PersonUser personUser) {
		getPersonUsers().add(personUser);
		personUser.setUser(this);

		return personUser;
	}

	public PersonUser removePersonUser(PersonUser personUser) {
		getPersonUsers().remove(personUser);
		personUser.setUser(null);

		return personUser;
	}

	public Person getPersonBean() {
		return this.personBean;
	}

	public void setPersonBean(Person personBean) {
		this.personBean = personBean;
	}

	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}