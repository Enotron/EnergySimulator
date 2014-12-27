package org.enotron.simulator.exception;


/**
 * Base class of Web Services faults. 
 * 
 * Derived classes should have 
 * 1) A no args Constructor
 * 2) A 1 arg Constructor - the String message
 * 3) A 2 arg constructor - the String message and a Throwable
 * 
 * Also they should include the annotation
 * 	@SoapFault(faultCode=FaultCode.CLIENT)
 * 
 * @author Sean Condon, Enotron.org
 *
 */
public abstract class AbstractSimulatorFault extends Exception {
	
	private static final long serialVersionUID = -6885809184743078007L;

	public AbstractSimulatorFault(String message, Throwable t) {
		super(message, t);
	}
	
	public AbstractSimulatorFault(String message) {
		super(message);
	}
	
	public AbstractSimulatorFault() {
		
	}

}
