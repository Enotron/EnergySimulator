package org.enotron.simulator.api;

public class SimulatorException extends Throwable {
	
	private static final long serialVersionUID = 3218113729208799043L;

	private int errorCode;
	
	public SimulatorException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
