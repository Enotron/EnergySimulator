package org.enotron.simulator.api;

import java.util.Date;


/**
 * A simple class that transports time series measurement data
 *  
 * @author scondon
 * @copyright 2015 Enotron Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */
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