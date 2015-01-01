package org.enotron.simulator.utility;

import java.util.Date;

import org.enotron.simulator.api.RunDetails;

/**
 * A class that implements the Run Details interface
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
public class RunDetailsImpl implements RunDetails {
	
	private Date startTime;
	private Date endTime;
	private boolean running;
	
	public RunDetailsImpl() {
	}
	
	public RunDetailsImpl(boolean running) {
		this.setRunning(running);
	}
	
	public RunDetailsImpl(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public long getDurationMs() {
		if (startTime != null && endTime != null) {
			return endTime.getTime() - startTime.getTime();
		}
		
		return 0l;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
