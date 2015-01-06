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
