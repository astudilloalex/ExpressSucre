package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the platform database table.
 * 
 */
@Entity
@NamedQuery(name = "Platform.findAll", query = "SELECT p FROM Platform p")
public class Platform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer number;

	// bi-directional many-to-one association to BusStationPlatform
	@OneToMany(mappedBy = "platformBean")
	private List<BusStationPlatform> busStationPlatforms;

	public Platform() {
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

	public List<BusStationPlatform> getBusStationPlatforms() {
		return this.busStationPlatforms;
	}

	public void setBusStationPlatforms(List<BusStationPlatform> busStationPlatforms) {
		this.busStationPlatforms = busStationPlatforms;
	}

	public BusStationPlatform addBusStationPlatform(BusStationPlatform busStationPlatform) {
		getBusStationPlatforms().add(busStationPlatform);
		busStationPlatform.setPlatformBean(this);

		return busStationPlatform;
	}

	public BusStationPlatform removeBusStationPlatform(BusStationPlatform busStationPlatform) {
		getBusStationPlatforms().remove(busStationPlatform);
		busStationPlatform.setPlatformBean(null);

		return busStationPlatform;
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
		if (!(object instanceof Platform)) {
			return false;
		}
		Platform other = (Platform) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}