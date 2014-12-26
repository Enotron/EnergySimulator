package org.enotron.simulator.utility;

public class GroupedValues {
	
	private double[] values;
	
	private int numberOfPeriods;
	
	private long durationMs;
	
	private long actualStart;

	public GroupedValues(double[] values) {
		
		this.values = values;
	}

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public int getNumberOfPeriods() {
		return numberOfPeriods;
	}

	public void setNumberOfPeriods(int numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}

	public long getDurationMs() {
		return durationMs;
	}

	public void setDurationMs(long durationMs) {
		this.durationMs = durationMs;
	}

	public long getActualStart() {
		return actualStart;
	}

	public void setActualStart(long actualStart) {
		this.actualStart = actualStart;
	}
}
