package org.enotron.simulator.utility;

import java.util.Date;

import org.enotron.simulator.api.RunDetails;

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
