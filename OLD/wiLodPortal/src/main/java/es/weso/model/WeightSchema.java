package es.weso.model;

import com.google.gson.Gson;

public class WeightSchema {

	private String uri;
	private JSONHashMap<String, Double> schema;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public JSONHashMap<String, Double> getSchema() {
		return schema;
	}

	public void setSchema(JSONHashMap<String, Double> schema) {
		this.schema = schema;
	}
	
	public void addWeight(String uri, double value) {
		if(schema == null) {
			schema = new JSONHashMap<String, Double>();
		}
		schema.put(uri, value);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
