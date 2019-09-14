package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bus_seating database table.
 * 
 */
@Entity
@Table(name="bus_seating")
@NamedQuery(name="BusSeating.findAll", query="SELECT b FROM BusSeating b")
public class BusSeating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String location;

	private Boolean state;

	//bi-directional many-to-one association to Bus
	@ManyToOne
	@JoinColumn(name="bus")
	private Bus busBean;

	//bi-directional many-to-one association to Seating
	@ManyToOne
	@JoinColumn(name="seating")
	private Seating seatingBean;

	public BusSeating() {
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

	public Seating getSeatingBean() {
		return this.seatingBean;
	}

	public void setSeatingBean(Seating seatingBean) {
		this.seatingBean = seatingBean;
	}

}