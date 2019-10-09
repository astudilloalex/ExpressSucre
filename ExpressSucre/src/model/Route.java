package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the route database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
	@NamedQuery(name = "Route.findByBusStations", query = "SELECT r FROM Route r WHERE r.busStation1=:busStation1 AND r.busStation2=:busStation2") 
})
public class Route implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal amount;

	private Boolean state;

	// bi-directional many-to-one association to BusStation
	@ManyToOne
	@JoinColumn(name = "bus_station_destination")
	private BusStation busStation1;

	// bi-directional many-to-one association to BusStation
	@ManyToOne
	@JoinColumn(name = "bus_station_origin")
	private BusStation busStation2;

	// bi-directional many-to-one association to Schedule
	@OneToMany(mappedBy = "routeBean")
	private List<Schedule> schedules;

	public Route() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public BusStation getBusStation1() {
		return this.busStation1;
	}

	public void setBusStation1(BusStation busStation1) {
		this.busStation1 = busStation1;
	}

	public BusStation getBusStation2() {
		return this.busStation2;
	}

	public void setBusStation2(BusStation busStation2) {
		this.busStation2 = busStation2;
	}

	public List<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schedule addSchedule(Schedule schedule) {
		getSchedules().add(schedule);
		schedule.setRouteBean(this);

		return schedule;
	}

	public Schedule removeSchedule(Schedule schedule) {
		getSchedules().remove(schedule);
		schedule.setRouteBean(null);

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
		if (!(object instanceof Route)) {
			return false;
		}
		Route other = (Route) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}