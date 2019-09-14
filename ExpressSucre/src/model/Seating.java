package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seating database table.
 * 
 */
@Entity
@NamedQuery(name="Seating.findAll", query="SELECT s FROM Seating s")
public class Seating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer number;

	//bi-directional many-to-one association to BusSeating
	@OneToMany(mappedBy="seatingBean")
	private List<BusSeating> busSeatings;

	public Seating() {
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

	public List<BusSeating> getBusSeatings() {
		return this.busSeatings;
	}

	public void setBusSeatings(List<BusSeating> busSeatings) {
		this.busSeatings = busSeatings;
	}

	public BusSeating addBusSeating(BusSeating busSeating) {
		getBusSeatings().add(busSeating);
		busSeating.setSeatingBean(this);

		return busSeating;
	}

	public BusSeating removeBusSeating(BusSeating busSeating) {
		getBusSeatings().remove(busSeating);
		busSeating.setSeatingBean(null);

		return busSeating;
	}

}