package es.weso.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representation of an observation for the web index project
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@XmlRootElement
public class Observation {

	private String name, uri, countryName, countryUri, countryCode;
	private NamedUri indicator;
	private double value;
	private int year;
	private int rank;

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

	public NamedUri getIndicator() {
		return indicator;
	}

	public void setIndicator(NamedUri indicator) {
		this.indicator = indicator;
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

}
