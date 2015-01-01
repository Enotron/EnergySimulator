package org.enotron.simulator.services;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.enotron.simulator.api.GroupByTime;
import org.enotron.simulator.api.MeasurementImpl;
import org.enotron.simulator.api.Simulator;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;
import org.enotron.simulator.impl.simulator.AbstractSimulator;
import org.enotron.simulator.utility.ByteArrayHelper;

/**
 * A Spring service that is designed to be injected in to the simulator 
 * where needed.
 * 
 * This groups methods related to data, like:
 * Get Measurements
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
public class DataService {
	
	private Logger _logger = Logger.getLogger(DataService.class.getName());
	
	private ConfigurationService configService;
	
	
	public DataService() {

	}

	public List<MeasurementImpl> getMeaurementList(
			byte[] simulatorId, 
			String[] parameterList, 
			ValuesMode valuesmode, 
			Date startTime, 
			Date endTime,
			GroupByTime groupByTime) 
					throws SimulatorException {
		
		
		//Check that this is a valid simulator ID
		AbstractSimulator sim = (AbstractSimulator) getSimulator(simulatorId);

		String simulatorIdHex = ByteArrayHelper.toHexString(simulatorId);
		_logger.fine("Received GetData request for " + simulatorIdHex + 
				" and " + parameterList + " for " + valuesmode + " grouped by ");
		
		if (parameterList == null || parameterList.length == 0) {
			_logger.warning("No Parameter List specified - " +
					"will return all parameters");
		}
		
		if (valuesmode == null) {
			_logger.warning("No Values mode specified - assuming " + 
					ValuesMode.AVERAGE);
			valuesmode = ValuesMode.AVERAGE;
		}
		
		return sim.getMeasurements(valuesmode, startTime, endTime, groupByTime, parameterList);
	}
	
	public ConfigurationService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigurationService configService) {
		this.configService = configService;
	}

	private Simulator getSimulator(byte[] simulatorUid) throws SimulatorException {

		ByteBuffer simKey;
		try {
			simKey = ByteBuffer.wrap(simulatorUid);
		} catch (Exception ex) {
			throw new SimulatorException("Invalid value passed as Simulator ID", -141);
		}
		

		Simulator sim = configService.getSimList().get(simKey);
		if (sim == null) {
			String simulatorIdHex = ByteArrayHelper.toHexString(simulatorUid);
			throw new SimulatorException(
					"Failed to find simulator with ID " + 
							simulatorIdHex, -139);
		}
		
		return sim;
	}	
}
