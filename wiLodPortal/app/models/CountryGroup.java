package models;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * Representation of a country group for the web index project
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@XmlRootElement
public class CountryGroup {

	private String name, uri;
	private Collection<CountryForRegion> countries;
	private boolean isContinent;

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

	public Collection<CountryForRegion> getCountries() {
		return countries;
	}

	public void setCountries(Collection<CountryForRegion> countries) {
		this.countries = countries;
	}

	public boolean isContinent() {
		return isContinent;
	}

	public void setContinent(boolean isContinent) {
		this.isContinent = isContinent;
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
		CountryGroup other = (CountryGroup) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
