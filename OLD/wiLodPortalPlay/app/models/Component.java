package models;

import java.util.Collection;
import java.util.HashSet;

public class Component {

	private String uri, label;
	private Collection<NamedUri> indicators;

	public Collection<NamedUri> getIndicators() {
		return indicators;
	}

	public void setIndicators(Collection<NamedUri> indicators) {
		this.indicators = indicators;
	}

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
	
	public void addIndicator(NamedUri indicator) {
		if(indicators == null) {
			indicators = new HashSet<NamedUri>();
		}
		indicators.add(indicator);
	}

}
