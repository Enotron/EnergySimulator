package org.enotron.simulator.impl.simulator;

import org.enotron.simulator.api.SimulatorException;

public class RefrigeratorSimulator extends AbstractSimulator {

	public static String[] parameterNames = new String[]{"NOMINAL_VOLTAGE","NOMINAL_POWER"};
	public static String[] parameterUnits = new String[]{"V","kW"};

	public RefrigeratorSimulator(String name, int blockSize) throws SimulatorException {
		super(name, blockSize);
	}

	@Override
	protected void abstractRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getParameterNames() {
		return parameterNames;
	}

	@Override
	public String[] getParameterUnits() {
		return parameterUnits;
	}

}
