package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the seat database table.
 * 
 */
@Entity
@NamedQuery(name = "Seat.findAll", query = "SELECT s FROM Seat s")
public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer number;

	// bi-directional many-to-one association to BusSeat
	@OneToMany(mappedBy = "seatBean")
	private List<BusSeat> busSeats;

	public Seat() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<BusSeat> getBusSeats() {
		return this.busSeats;
	}

	public void setBusSeats(List<BusSeat> busSeats) {
		this.busSeats = busSeats;
	}

	public BusSeat addBusSeat(BusSeat busSeat) {
		getBusSeats().add(busSeat);
		busSeat.setSeatBean(this);

		return busSeat;
	}

	public BusSeat removeBusSeat(BusSeat busSeat) {
		getBusSeats().remove(busSeat);
		busSeat.setSeatBean(null);

		return busSeat;
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
		if (!(object instanceof Seat)) {
			return false;
		}
		Seat other = (Seat) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}