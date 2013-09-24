package es.weso.model;

import java.util.Collection;
import java.util.HashSet;

import com.google.gson.Gson;

public class Subindex {
	private String uri, comment, label;
	private Collection<NamedUri> components;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Collection<NamedUri> getComponents() {
		return components;
	}

	public void setComponents(Collection<NamedUri> components) {
		this.components = components;
	}
	
	public void addComponent(NamedUri component) {
		if(components == null) {
			components = new HashSet<NamedUri>();
		}
		components.add(component);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
