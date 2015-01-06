package org.enotron.simulator.api;

import java.util.Date;
import java.util.List;

/**
 * Interface that all simulator implement. AbstractSimulator class implements
 * most of these methods, so it's best to extend that class
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
public interface Simulator {
	
	public String getName();

	public void setName(String name);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public double getLongitude();
	
	public void setLongitude(double longitude);

	public double getLatitude();
	
	public void setLatitude(double latitude);	
	
	public byte[] getUniqueId();

	public void setUniqueId(byte[] uniqueId);
	
	public List<MeasurementImpl> getMeasurements(ValuesMode valuesMode,
			Date startTime, Date endTime,
			GroupByTime groupByTime,
			String[] parameterList) throws SimulatorException;
	
	public void start();
	
	public RunDetails stop();
	
	public RunDetails deleteData();

}
