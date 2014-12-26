package org.enotron.simulator.impl.simulator;

import java.util.Date;

import org.enotron.simulator.api.SimulatorException;

public class ElectricVehicleSimulator extends AbstractSimulator {
	
	private int minVoltage = 200;
	private int maxVoltage = 240;
	
	private int minPower = -2400; //-2.4kW
	private int maxPower = 20000; //20kW
	
	public static String[] parameterNames = new String[]{"NOMINAL_VOLTAGE","NOMINAL_POWER"};
	public static String[] parameterUnits = new String[]{"V","kW"};
	
	public ElectricVehicleSimulator() throws SimulatorException {
		this("DummyValue", 100);		
	}
	
	public ElectricVehicleSimulator(String name, int blockSize) throws SimulatorException {
		super(name, blockSize);
	
		sequentialValuesBlock = new double[parameterNames.length][blockSize];
		previousSequentialValuesBlock = new double[parameterNames.length][blockSize];
		
	}

	
	@Override
	public void run() {
		_logger.info("Generating value for " + 
				getName() + " at " + new Date() + " ID: " + valuesCount);
		
		//After each block switch
		if (valuesCount == 0) {
			//Round to nearest second
			blockStartTimeMs = System.currentTimeMillis() - System.currentTimeMillis() % 1000l;
		}

		//Calculate the volatges
		sequentialValuesBlock[0][valuesCount] = 
				minVoltage + 
				(maxVoltage-minVoltage) * rand.nextDouble();
		
		double lastPower = (valuesCount==0?0:sequentialValuesBlock[1][valuesCount]);
		sequentialValuesBlock[1][valuesCount] = 
				lastPower + 
				0.5 * rand.nextDouble() * (maxPower-minPower)/100d;
		
		//After the block has filled we rotate it around and 
		// persist the old ones if necessary
		if (valuesCount == blockSize-1) {
			createNewBlock();
		} else {
			valuesCount++;
		}
		
		setEndTime(System.currentTimeMillis());
	}


	@Override
	protected void abstractRun() {
		_logger.fine("Setting value of " + parameterNames[0] + " and " + 
				parameterNames[1] + " for " + 
				this.getClass().getSimpleName() + ": " + getName());
		
		//Calculate the volatges
		sequentialValuesBlock[0][valuesCount] = 
				minVoltage + 
				(maxVoltage-minVoltage) * rand.nextDouble();
		
		double lastPower = (valuesCount==0?0:sequentialValuesBlock[1][valuesCount]);
		sequentialValuesBlock[1][valuesCount] = 
				lastPower + 
				0.5 * rand.nextDouble() * (maxPower-minPower)/100d;
	}


	@Override
	public String[] getParameterNames() {
		return ElectricVehicleSimulator.parameterNames;
	}


	@Override
	public String[] getParameterUnits() {
		return ElectricVehicleSimulator.parameterUnits;
	}
	
}
