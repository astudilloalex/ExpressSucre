package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the schedule database table.
 * 
 */
@Entity
@NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Timestamp date;

	// bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy = "scheduleBean")
	private List<Reservation> reservations;

	// bi-directional many-to-one association to Bus
	@ManyToOne
	@JoinColumn(name = "bus")
	private Bus busBean;

	// bi-directional many-to-one association to Route
	@ManyToOne
	@JoinColumn(name = "route")
	private Route routeBean;

	public Schedule() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setScheduleBean(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setScheduleBean(null);

		return reservation;
	}

	public Bus getBusBean() {
		return this.busBean;
	}

	public void setBusBean(Bus busBean) {
		this.busBean = busBean;
	}

	public Route getRouteBean() {
		return this.routeBean;
	}

	public void setRouteBean(Route routeBean) {
		this.routeBean = routeBean;
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
		if (!(object instanceof Schedule)) {
			return false;
		}
		Schedule other = (Schedule) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}