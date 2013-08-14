package es.weso.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representation of an observation for the web index project
 * 
 * @author Alejandro Montes García
 * @since 13/08/2013
 * @version 1.0
 */
@XmlRootElement
public class Indicator {
	private String uri, label, comment;
	private boolean higherBetter;
	private int start, end;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean getHigherBetter() {
		return higherBetter;
	}

	public void setHigherBetter(boolean higherBetter) {
		this.higherBetter = higherBetter;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
