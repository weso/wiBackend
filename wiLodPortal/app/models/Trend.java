package models;

import com.google.gson.Gson;

public class Trend {

	private double trend;

	public Trend() {
	}

	public Trend(double trend) {
		this.setTrend(trend);
	}

	public double getTrend() {
		return trend;
	}

	public void setTrend(double trend) {
		this.trend = trend;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
