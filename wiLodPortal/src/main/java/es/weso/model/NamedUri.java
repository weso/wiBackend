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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.toLowerCase().hashCode());
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
		NamedUri other = (NamedUri) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equalsIgnoreCase(other.uri))
			return false;
		return true;
	}

}
