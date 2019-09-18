package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the bus_station_platform database table.
 * 
 */
@Entity
@Table(name="bus_station_platform")
@NamedQuery(name="BusStationPlatform.findAll", query="SELECT b FROM BusStationPlatform b")
public class BusStationPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal amount;

	private Boolean state;

	//bi-directional many-to-one association to BusStation
	@ManyToOne
	@JoinColumn(name="bus_station")
	private BusStation busStationBean;

	//bi-directional many-to-one association to Platform
	@ManyToOne
	@JoinColumn(name="platform")
	private Platform platformBean;

	public BusStationPlatform() {
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

	public BusStation getBusStationBean() {
		return this.busStationBean;
	}

	public void setBusStationBean(BusStation busStationBean) {
		this.busStationBean = busStationBean;
	}

	public Platform getPlatformBean() {
		return this.platformBean;
	}

	public void setPlatformBean(Platform platformBean) {
		this.platformBean = platformBean;
	}

}