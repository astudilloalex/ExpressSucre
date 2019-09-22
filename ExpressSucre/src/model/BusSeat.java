package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the bus_seat database table.
 * 
 */
@Entity
@Table(name = "bus_seat")
@NamedQuery(name = "BusSeat.findAll", query = "SELECT b FROM BusSeat b")
public class BusSeat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String location;

	private Boolean state;

	// bi-directional many-to-one association to Bus
	@ManyToOne
	@JoinColumn(name = "bus")
	private Bus busBean;

	// bi-directional many-to-one association to Seat
	@ManyToOne
	@JoinColumn(name = "seat")
	private Seat seatBean;

	public BusSeat() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Bus getBusBean() {
		return this.busBean;
	}

	public void setBusBean(Bus busBean) {
		this.busBean = busBean;
	}

	public Seat getSeatBean() {
		return this.seatBean;
	}

	public void setSeatBean(Seat seatBean) {
		this.seatBean = seatBean;
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
		if (!(object instanceof BusSeat)) {
			return false;
		}
		BusSeat other = (BusSeat) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}