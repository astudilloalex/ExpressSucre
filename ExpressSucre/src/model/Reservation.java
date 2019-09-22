package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "person")
	private Person personBean;

	// bi-directional many-to-one association to Schedule
	@ManyToOne
	@JoinColumn(name = "schedule")
	private Schedule scheduleBean;

	public Reservation() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getPersonBean() {
		return this.personBean;
	}

	public void setPersonBean(Person personBean) {
		this.personBean = personBean;
	}

	public Schedule getScheduleBean() {
		return this.scheduleBean;
	}

	public void setScheduleBean(Schedule scheduleBean) {
		this.scheduleBean = scheduleBean;
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
		if (!(object instanceof Reservation)) {
			return false;
		}
		Reservation other = (Reservation) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}