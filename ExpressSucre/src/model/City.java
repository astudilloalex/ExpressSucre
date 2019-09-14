package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to BusStation
	@OneToMany(mappedBy="cityBean")
	private List<BusStation> busStations;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country")
	private Country countryBean;

	public City() {
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

	public List<BusStation> getBusStations() {
		return this.busStations;
	}

	public void setBusStations(List<BusStation> busStations) {
		this.busStations = busStations;
	}

	public BusStation addBusStation(BusStation busStation) {
		getBusStations().add(busStation);
		busStation.setCityBean(this);

		return busStation;
	}

	public BusStation removeBusStation(BusStation busStation) {
		getBusStations().remove(busStation);
		busStation.setCityBean(null);

		return busStation;
	}

	public Country getCountryBean() {
		return this.countryBean;
	}

	public void setCountryBean(Country countryBean) {
		this.countryBean = countryBean;
	}

}