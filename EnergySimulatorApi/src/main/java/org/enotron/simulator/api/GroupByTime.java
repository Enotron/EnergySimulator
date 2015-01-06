package org.enotron.simulator.api;

/**
 * A simple enumerated list of ways of grouping values by time
 * 
 * Note: Month is not yet add as this can be variable in length
 * 			-> to be added
 * 
 * This corresponds to the list of values in the SOAP project XSD
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
public enum GroupByTime {
	MINUTE(60*1000l),
	TENMINUTE(10*60*1000l),
	THIRTYMINUTE(30*60*1000l),
	HOUR(60*60*1000l),
	HALFDAY(12*60*60*1000l),
	DAY(24*60*60*1000l), //Watch out for daylight savings change 23 or 25 hours
	WEEK(24*60*60*1000l), //As above
	//MONTH - variable length - to be added
	NONE(0l);
	
	private long milliseconds;
	
	private GroupByTime(long milliseconds) {
		setMilliseconds(milliseconds);
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}
}
