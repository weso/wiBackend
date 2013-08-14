package es.weso.model;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import com.google.gson.Gson;

/**
 * Representation of an observation for the web index project
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public class Stats {
	private double mean, sd, median, max, min;

	public Stats() {
	}

	public Stats(double[] values) {
		this.mean = new Mean().evaluate(values);
		this.sd = new StandardDeviation().evaluate(values);
		this.median = new Median().evaluate(values);
		this.max = new Max().evaluate(values);
		this.min = new Min().evaluate(values);
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
