package es.weso.model;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Generic wrapper to keep JAXB happy
 * 
 * @author Alejandro Montes García
 * @since 10/07/2013
 * @version 1.0
 * @param <T>
 */
@XmlRootElement
public class ItemCollection<T> {
	private Collection<T> collection;

	public ItemCollection() {
		collection = Collections.emptyList();
	}

	public ItemCollection(Collection<T> collection) {
		this.collection = collection;
	}

	public Collection<T> getCollection() {
		return collection;
	}

	public void setCollection(Collection<T> collection) {
		this.collection = collection;
	}

}
