package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the bus database table.
 * 
 */
@Entity
@NamedQuery(name = "Bus.findAll", query = "SELECT b FROM Bus b")
public class Bus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer capacity;

	@Column(name = "disk_number")
	private Integer diskNumber;

	private String plate;

	// bi-directional many-to-one association to Brand
	@ManyToOne
	@JoinColumn(name = "brand")
	private Brand brandBean;

	// bi-directional many-to-one association to BusSeat
	@OneToMany(mappedBy = "busBean")
	private List<BusSeat> busSeats;

	// bi-directional many-to-one association to Schedule
	@OneToMany(mappedBy = "busBean")
	private List<Schedule> schedules;

	public Bus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getDiskNumber() {
		return this.diskNumber;
	}

	public void setDiskNumber(Integer diskNumber) {
		this.diskNumber = diskNumber;
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Brand getBrandBean() {
		return this.brandBean;
	}

	public void setBrandBean(Brand brandBean) {
		this.brandBean = brandBean;
	}

	public List<BusSeat> getBusSeats() {
		return this.busSeats;
	}

	public void setBusSeats(List<BusSeat> busSeats) {
		this.busSeats = busSeats;
	}

	public BusSeat addBusSeat(BusSeat busSeat) {
		getBusSeats().add(busSeat);
		busSeat.setBusBean(this);

		return busSeat;
	}

	public BusSeat removeBusSeat(BusSeat busSeat) {
		getBusSeats().remove(busSeat);
		busSeat.setBusBean(null);

		return busSeat;
	}

	public List<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schedule addSchedule(Schedule schedule) {
		getSchedules().add(schedule);
		schedule.setBusBean(this);

		return schedule;
	}

	public Schedule removeSchedule(Schedule schedule) {
		getSchedules().remove(schedule);
		schedule.setBusBean(null);

		return schedule;
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
		if (!(object instanceof Bus)) {
			return false;
		}
		Bus other = (Bus) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}