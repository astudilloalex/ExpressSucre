package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
	@NamedQuery(name = "Reservation.findByPerson", query = "SELECT r FROM Reservation r WHERE r.scheduleBean.date>CURRENT_TIMESTAMP AND r.personUserBean IN :personUsers ORDER BY r.scheduleBean.date DESC")
})
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// bi-directional many-to-one association to BusSeat
	@ManyToOne
	@JoinColumn(name = "bus_seat")
	private BusSeat busSeatBean;

	// bi-directional many-to-one association to PersonUser
	@ManyToOne
	@JoinColumn(name = "person_user")
	private PersonUser personUserBean;

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

	public BusSeat getBusSeatBean() {
		return this.busSeatBean;
	}

	public void setBusSeatBean(BusSeat busSeatBean) {
		this.busSeatBean = busSeatBean;
	}

	public PersonUser getPersonUserBean() {
		return this.personUserBean;
	}

	public void setPersonUserBean(PersonUser personUserBean) {
		this.personUserBean = personUserBean;
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