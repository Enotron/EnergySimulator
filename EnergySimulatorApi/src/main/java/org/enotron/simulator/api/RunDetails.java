package org.enotron.simulator.api;

import java.util.Date;

public interface RunDetails {

	public void setStartTime(Date startTime);
	public Date getStartTime();
	public void setEndTime(Date endTime);
	public Date getEndTime();
	
	public long getDurationMs();
	
	public boolean isRunning();
}
