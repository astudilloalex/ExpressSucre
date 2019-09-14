package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bus database table.
 * 
 */
@Entity
@NamedQuery(name="Bus.findAll", query="SELECT b FROM Bus b")
public class Bus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer capacity;

	@Column(name="disk_number")
	private Integer diskNumber;

	private String plaque;

	//bi-directional many-to-one association to Brand
	@ManyToOne
	@JoinColumn(name="brand")
	private Brand brandBean;

	//bi-directional many-to-one association to BusSeating
	@OneToMany(mappedBy="busBean")
	private List<BusSeating> busSeatings;

	//bi-directional many-to-one association to Schedule
	@OneToMany(mappedBy="busBean")
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

	public String getPlaque() {
		return this.plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public Brand getBrandBean() {
		return this.brandBean;
	}

	public void setBrandBean(Brand brandBean) {
		this.brandBean = brandBean;
	}

	public List<BusSeating> getBusSeatings() {
		return this.busSeatings;
	}

	public void setBusSeatings(List<BusSeating> busSeatings) {
		this.busSeatings = busSeatings;
	}

	public BusSeating addBusSeating(BusSeating busSeating) {
		getBusSeatings().add(busSeating);
		busSeating.setBusBean(this);

		return busSeating;
	}

	public BusSeating removeBusSeating(BusSeating busSeating) {
		getBusSeatings().remove(busSeating);
		busSeating.setBusBean(null);

		return busSeating;
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

}