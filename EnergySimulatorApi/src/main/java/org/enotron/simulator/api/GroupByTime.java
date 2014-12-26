package org.enotron.simulator.api;

public enum GroupByTime {
	MINUTE(60*1000l),
	TENMINUTE(10*60*1000l),
	THIRTYMINUTE(30*60*1000l),
	HOUR(60*60*1000l),
	HALFDAY(12*60*60*1000l),
	DAY(24*60*60*1000l), //Watch out for daylight savings change 23 or 25 hours
	WEEK(24*60*60*1000l), //As above
	//MONTH - variable length - to be added
	NONE(0l);
	
	private long milliseconds;
	
	private GroupByTime(long milliseconds) {
		setMilliseconds(milliseconds);
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}
}
