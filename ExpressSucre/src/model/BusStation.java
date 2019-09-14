package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bus_station database table.
 * 
 */
@Entity
@Table(name="bus_station")
@NamedQuery(name="BusStation.findAll", query="SELECT b FROM BusStation b")
public class BusStation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name="city")
	private City cityBean;

	//bi-directional many-to-one association to BusStationPlatform
	@OneToMany(mappedBy="busStationBean")
	private List<BusStationPlatform> busStationPlatforms;

	//bi-directional many-to-one association to Route
	@OneToMany(mappedBy="busStation1")
	private List<Route> routes1;

	//bi-directional many-to-one association to Route
	@OneToMany(mappedBy="busStation2")
	private List<Route> routes2;

	public BusStation() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCityBean() {
		return this.cityBean;
	}

	public void setCityBean(City cityBean) {
		this.cityBean = cityBean;
	}

	public List<BusStationPlatform> getBusStationPlatforms() {
		return this.busStationPlatforms;
	}

	public void setBusStationPlatforms(List<BusStationPlatform> busStationPlatforms) {
		this.busStationPlatforms = busStationPlatforms;
	}

	public BusStationPlatform addBusStationPlatform(BusStationPlatform busStationPlatform) {
		getBusStationPlatforms().add(busStationPlatform);
		busStationPlatform.setBusStationBean(this);

		return busStationPlatform;
	}

	public BusStationPlatform removeBusStationPlatform(BusStationPlatform busStationPlatform) {
		getBusStationPlatforms().remove(busStationPlatform);
		busStationPlatform.setBusStationBean(null);

		return busStationPlatform;
	}

	public List<Route> getRoutes1() {
		return this.routes1;
	}

	public void setRoutes1(List<Route> routes1) {
		this.routes1 = routes1;
	}

	public Route addRoutes1(Route routes1) {
		getRoutes1().add(routes1);
		routes1.setBusStation1(this);

		return routes1;
	}

	public Route removeRoutes1(Route routes1) {
		getRoutes1().remove(routes1);
		routes1.setBusStation1(null);

		return routes1;
	}

	public List<Route> getRoutes2() {
		return this.routes2;
	}

	public void setRoutes2(List<Route> routes2) {
		this.routes2 = routes2;
	}

	public Route addRoutes2(Route routes2) {
		getRoutes2().add(routes2);
		routes2.setBusStation2(this);

		return routes2;
	}

	public Route removeRoutes2(Route routes2) {
		getRoutes2().remove(routes2);
		routes2.setBusStation2(null);

		return routes2;
	}

}