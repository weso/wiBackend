package es.weso.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * Pair name-uri to allow navigation
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@XmlRootElement
public class NamedUri {

	private String name, uri;

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
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
