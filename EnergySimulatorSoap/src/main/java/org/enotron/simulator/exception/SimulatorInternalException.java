package org.enotron.simulator.exception;

public class SimulatorInternalException extends AbstractSimulatorFault {

	private static final long serialVersionUID = -7447412377648327848L;

	public SimulatorInternalException() {
		super();
	}

	public SimulatorInternalException(String message, Throwable t) {
		super(message, t);
	}

	public SimulatorInternalException(String message) {
		super(message);
	}
}
