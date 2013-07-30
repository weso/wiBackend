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

	private String name, uri;
	private NamedUri country, indicator;
	private double value;
	private int year, trend;
	private Rank rank;

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

	public NamedUri getCountry() {
		return country;
	}

	public void setCountry(NamedUri country) {
		this.country = country;
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

	public int getTrend() {
		return trend;
	}

	public void setTrend(int trend) {
		this.trend = trend;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

}
