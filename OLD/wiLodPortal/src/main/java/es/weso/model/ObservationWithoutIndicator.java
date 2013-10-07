package es.weso.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * Representation of an observation for the web index project
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@XmlRootElement
public class ObservationWithoutIndicator {

	private String name, uri, countryName, countryUri, countryCode;
	private double value;
	private int year, rank;
	
	public ObservationWithoutIndicator(){}
	
	public ObservationWithoutIndicator(Observation obs) {
		this.name = obs.getName();
		this.uri = obs.getUri();
		this.countryName = obs.getCountryName();
		this.countryUri = obs.getCountryUri();
		this.countryCode = obs.getCountryCode();
		this.value = obs.getValue();
		this.year = obs.getYear();
		this.rank = obs.getRank();
	}

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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryUri() {
		return countryUri;
	}

	public void setCountryUri(String countryUri) {
		this.countryUri = countryUri;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
