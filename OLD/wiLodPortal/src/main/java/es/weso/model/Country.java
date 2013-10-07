package es.weso.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * Representation of a country for the web index project
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@XmlRootElement
public class Country {
	private String name, uri, isoCode2, isoCode3;
	private Collection<ValuedNamedUri> observations;
	private Collection<NamedUri> belongsTo;
	private double lat, lon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIsoCode2() {
		return isoCode2;
	}

	public void setIsoCode2(String isoCode2) {
		this.isoCode2 = isoCode2;
	}

	public String getIsoCode3() {
		return isoCode3;
	}

	public void setIsoCode3(String isoCode3) {
		this.isoCode3 = isoCode3;
	}

	public Collection<ValuedNamedUri> getObservations() {
		return observations;
	}

	public void setObservations(Collection<ValuedNamedUri> observations) {
		this.observations = observations;
	}

	public Collection<NamedUri> getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Collection<NamedUri> belongsTo) {
		this.belongsTo = belongsTo;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
