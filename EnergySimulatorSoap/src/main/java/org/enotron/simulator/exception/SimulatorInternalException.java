package org.enotron.simulator.exception;

/**
 * Simple Exception class
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
