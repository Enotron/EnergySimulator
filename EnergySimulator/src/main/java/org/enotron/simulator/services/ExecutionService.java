package org.enotron.simulator.services;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.enotron.simulator.api.RunDetails;
import org.enotron.simulator.api.Simulator;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.impl.simulator.AbstractSimulator;
import org.enotron.simulator.utility.ByteArrayHelper;
import org.enotron.simulator.utility.RunDetailsImpl;

/**
 * A Spring service that is designed to be injected in to the simulator 
 * where needed.
 * 
 * This groups methods related to execution, like:
 * Start and Stop
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
public class ExecutionService {

	private Logger _logger = Logger.getLogger(ExecutionService.class.getName());
	
	private ConfigurationService configService;
	
	private ScheduledExecutorService simExecutor;
	
	private HashMap<ByteBuffer,ScheduledFuture<?>> executorList;

	
	public ExecutionService() {
		simExecutor = new ScheduledThreadPoolExecutor(10);
		
		executorList = new HashMap<ByteBuffer, ScheduledFuture<?>>();
		
	}

	public RunDetails startSimulator(byte[] simulatorUid) throws SimulatorException {
		
		AbstractSimulator sim = (AbstractSimulator)getSimulator(simulatorUid);
		
		RunDetailsImpl runDetails = 
				new RunDetailsImpl(
						new Date(sim.getStartTime()),
						new Date(sim.getEndTime()));


		String simulatorIdHex = ByteArrayHelper.toHexString(simulatorUid);

		_logger.info("Starting simulator " + simulatorIdHex);
		
		ByteBuffer simulatorBb = ByteBuffer.wrap(simulatorUid);
		
		if (executorList.containsKey(simulatorBb)) {
			
			_logger.info("Simulator is already running");
			runDetails.setRunning(false);
			return runDetails;
		}
		
		//Round the start times
		long now = System.currentTimeMillis();
		long startTime = 
				sim.getSleepDurationMs()-
				now%sim.getSleepDurationMs();
		
		sim.setStartTime(now+startTime);
		//Set it to be the same as the start time for the moment 
		// each run will update it 
		sim.setEndTime(now+startTime);

		_logger.info("Starting simulator " + 
						sim.getName() + " at " + 
						new Date(System.currentTimeMillis() + startTime));
		
		ScheduledFuture<?> executor = simExecutor.scheduleAtFixedRate(sim,
										startTime, 
										sim.getSleepDurationMs(), 
										TimeUnit.MILLISECONDS);
		
		executorList.put(simulatorBb,executor);
		runDetails.setRunning(true);
		runDetails.setStartTime(new Date(now+startTime));
		runDetails.setEndTime(new Date(now+startTime));
		return runDetails;
	}
	
	public RunDetails stopSimulator(byte[] simulatorUid) throws SimulatorException {
		//Check that this is a valid simulator ID
		AbstractSimulator sim = (AbstractSimulator) getSimulator(simulatorUid);

		String simulatorIdHex = ByteArrayHelper.toHexString(simulatorUid);
		_logger.info("Stopping simulator " + simulatorIdHex);

		RunDetailsImpl runDetails = 
				new RunDetailsImpl(
						new Date(sim.getStartTime()),
						new Date(sim.getEndTime()));
		
		ByteBuffer simulatorBb = ByteBuffer.wrap(simulatorUid);
		
		if (!executorList.containsKey(simulatorBb)) {
			
			_logger.info("Simulator is not running");
			runDetails.setRunning(false);
			return runDetails;
		}
		
		executorList.get(simulatorBb).cancel(true);
		executorList.remove(simulatorBb);
		runDetails.setRunning(false);
		return runDetails;

	}
	
	public RunDetails getSimulatorStatus(byte[] simulatorUid) throws SimulatorException {

		AbstractSimulator sim = (AbstractSimulator)getSimulator(simulatorUid);
		
		String simulatorIdHex = ByteArrayHelper.toHexString(simulatorUid);
		_logger.info("Simulator status of " + simulatorIdHex);

		RunDetailsImpl runDetails = 
				new RunDetailsImpl(
						new Date(sim.getStartTime()),
						new Date(sim.getEndTime()));
		
		
		ByteBuffer simulatorBb = ByteBuffer.wrap(simulatorUid);
		
		if (!executorList.containsKey(simulatorBb)) {
			
			_logger.info("Simulator is not running");
			runDetails.setRunning(false);
			return runDetails;
		}

		runDetails.setRunning(true);
		
		return runDetails;
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
	
	public ConfigurationService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigurationService configService) {
		this.configService = configService;
	}
}
