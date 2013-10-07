package es.weso.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * Triple name-uri-value
 * 
 * @author Alejandro Montes García
 * @since 04/07/2013
 * @version 1.0
 */
@XmlRootElement
public class ValuedNamedUri extends NamedUri {
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
