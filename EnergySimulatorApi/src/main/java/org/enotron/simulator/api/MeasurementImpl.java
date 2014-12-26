package org.enotron.simulator.api;

import java.util.Date;



public class MeasurementImpl {
	private String name;
	private String unit;
	private Date timestamp;
	private int durationSecs;
	private ValuesMode valueCode;
	private Float[] values;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getDurationSecs() {
		return durationSecs;
	}

	public void setDurationSecs(int durationSecs) {
		this.durationSecs = durationSecs;
	}

	public ValuesMode getValueCode() {
		return valueCode;
	}

	public void setValueCode(ValuesMode valueCode) {
		this.valueCode = valueCode;
	}

	public Float[] getValues() {
		return values;
	}

	public void setValues(Float[] values) {
		this.values = values;
	}
}