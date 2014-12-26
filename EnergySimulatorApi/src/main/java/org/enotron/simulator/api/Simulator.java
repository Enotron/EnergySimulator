package org.enotron.simulator.api;

import java.util.Date;
import java.util.List;

public interface Simulator {
	
	public String getName();

	public void setName(String name);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public double getLongitude();
	
	public void setLongitude(double longitude);

	public double getLatitude();
	
	public void setLatitude(double latitude);	
	
	public byte[] getUniqueId();

	public void setUniqueId(byte[] uniqueId);
	
	public List<MeasurementImpl> getMeasurements(ValuesMode valuesMode,
			Date startTime, Date endTime,
			GroupByTime groupByTime,
			String[] parameterList) throws SimulatorException;
	
	public void start();
	
	public RunDetails stop();
	
	public RunDetails deleteData();

}
