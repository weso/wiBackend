package es.weso.model;

import java.util.HashMap;

import com.google.gson.Gson;

public class JSONHashMap<K, V> extends HashMap<K, V> {
	
	private static final long serialVersionUID = -6531924575293841160L;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
