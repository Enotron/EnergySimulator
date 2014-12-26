package org.enotron.simulator.impl.controller;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.impl.simulator.AbstractSimulator;
@Deprecated
public abstract class AbstractController {
	
	protected Logger _logger = Logger.getLogger(this.getClass().getName());
	
	protected int simulatorCounter;
	protected String simulatorType;
	protected ScheduledExecutorService simExecutor;
	
	
	public AbstractController() {
		simExecutor = new ScheduledThreadPoolExecutor(10);
	}
	
	public abstract AbstractSimulator createInstance(String name, int blockSize) 
			throws SimulatorException;

	protected void startSimulator(AbstractSimulator sim) {

		//Round the start times
		long startTime = 
				sim.getSleepDurationMs()-
				System.currentTimeMillis()%sim.getSleepDurationMs();

		_logger.info("Starting simulator " + 
						sim.getName() + " at " + 
						new Date(System.currentTimeMillis() + startTime));
		
		simExecutor.scheduleAtFixedRate(sim,
										startTime, 
										sim.getSleepDurationMs(), 
										TimeUnit.MILLISECONDS);
		
	}

	public String getSimulatorType() {
		return simulatorType;
	}

	public void setSimulatorType(String simulatorType) {
		this.simulatorType = simulatorType;
	}
}
