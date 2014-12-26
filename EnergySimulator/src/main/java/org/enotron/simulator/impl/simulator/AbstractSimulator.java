package org.enotron.simulator.impl.simulator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.enotron.simulator.api.GroupByTime;
import org.enotron.simulator.api.MeasurementImpl;
import org.enotron.simulator.api.RunDetails;
import org.enotron.simulator.api.Simulator;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.api.ValuesMode;
import org.enotron.simulator.utility.GroupByTimeHelper;
import org.enotron.simulator.utility.GroupedValues;
import org.enotron.simulator.utility.RunDetailsImpl;
import org.enotron.simulator.utility.ValuesModeHelper;

public abstract class AbstractSimulator implements Simulator, Runnable {
	
	protected Logger _logger = Logger.getLogger(this.getClass().getName());
	
	private static final long ONE_SECOND = 1000L;
	private static final long ONE_MINUTE = 60*ONE_SECOND;
	private static final long ONE_HOUR = 60*ONE_MINUTE;
	private static final long DEFAULT_SLEEP_TIME_MS = 10000l;
	private static final int BLOCK_SIZE_MAX = 10000;
	private static final int DEFAULT_BLOCK_SIZE = 100;

	protected String name;
	protected String description;
	protected double longitude;
	protected double latitude;
	protected byte[] uniqueId;
	protected double[][] sequentialValuesBlock;
	protected double[][] previousSequentialValuesBlock;
	private long sleepDurationMs = DEFAULT_SLEEP_TIME_MS;
	protected int valuesCount = 0;
	protected int valuesStart = 0;
	protected int blockSize = 10;
	protected long blockStartTimeMs = 0;
	protected Random rand = new Random();
	protected long startTime;
	protected long endTime;


	public AbstractSimulator(String name) throws SimulatorException {
		this(name, DEFAULT_BLOCK_SIZE);
	}

	public AbstractSimulator(String name, int blockSize) throws SimulatorException {
		if (name == null || name.isEmpty()) {
			throw new SimulatorException("Name parameter cannot be empty", 130);
		} else if (blockSize <= 1 || blockSize >10000) {
			_logger.warning("Block size must be between 2 and " + 
					BLOCK_SIZE_MAX + " setting to " + DEFAULT_BLOCK_SIZE);
			blockSize = DEFAULT_BLOCK_SIZE;
		} 

		this.name = name;
		this.blockSize = blockSize;

		long uidLong = rand.nextLong();
			
		uniqueId = BigInteger.valueOf(uidLong).toByteArray();
		_logger.info("Created new simulator " + name + 
				" with Uid: " + Long.toHexString(uidLong).toUpperCase());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public List<MeasurementImpl> getMeasurements(
			ValuesMode valuesMode, 
			Date startTime, 
			Date endTime,
			GroupByTime groupByTime,
			String[] parameterList) throws SimulatorException {
		
		long nowMs = System.currentTimeMillis();
		
		if (startTime == null && endTime == null) {
			startTime = new Date(nowMs - ONE_HOUR); //one hour ago
			endTime = new Date(nowMs); //now
			
		} else if (startTime == null) {
			//No startTime given - we have end time - go for 1 hour before
			startTime = new Date(endTime.getTime()-ONE_HOUR);
			
		} else if (endTime == null) {
			//No endTime given - we have start time - go for 1 hour after
			endTime = new Date(startTime.getTime()+ONE_HOUR);
			
		}
		
		//Need to work out the index to start and end at
		int startIndex = (int)((startTime.getTime() - blockStartTimeMs)/sleepDurationMs);
		int endIndex = (int)((endTime.getTime() - blockStartTimeMs)/sleepDurationMs);
		
		_logger.info("Request for measurements on " + this.getName() + 
				"\nBlock Start:\t" + valuesStart +
				"\nBlock Count:\t" + valuesCount +
				"\nStart index:\t" + startIndex + " (" + startTime + ")" +
				"\nEnd index:\t" + endIndex + " (" + endTime + ")" +
				"\nBlock start:\t" + new Date(blockStartTimeMs) + 
				"\nBlock duration:\t" + sleepDurationMs/1000l + "s" +
				"\nBlock size:\t" + blockSize);
		
		
		List<MeasurementImpl> measList = new ArrayList<MeasurementImpl>();
		String[] parameterUnits = null;
		if (parameterList == null || parameterList.length == 0) {
			//Use all
			parameterList = getParameterNames();
			parameterUnits = getParameterUnits();
		} else {
			parameterUnits = getUnitsForParameters(parameterList);
		}
		
		for (int p=0;p<parameterList.length;p++) {
			
			double[] activeValues;
			if (valuesStart == 0) {
				startIndex = startIndex<0?0:startIndex;
				endIndex = endIndex<=valuesCount?endIndex:valuesCount;
				
				activeValues = Arrays.copyOfRange(sequentialValuesBlock[p], startIndex, endIndex);

				_logger.info("Retrieving values [" + startIndex + "-" + endIndex + "] actual:" + activeValues.length + " from Recent Block only");
			
			} else if (startIndex<0 && endIndex>0) {
				startIndex = -startIndex<blockSize?startIndex:blockSize;
				endIndex = endIndex<=valuesCount?endIndex:valuesCount;
				
 				activeValues = ArrayUtils.addAll(
 									Arrays.copyOfRange(previousSequentialValuesBlock[p], startIndex, blockSize-1),
 									Arrays.copyOfRange(sequentialValuesBlock[p], 0, endIndex));

 				_logger.info("Retrieving values [" + startIndex + "-" + endIndex + "] actual:" + activeValues.length + " from Recent and Previous Blocks");
 				
 			} else if (startIndex<0 && endIndex<0) {
 				startIndex = -startIndex<blockSize?startIndex:blockSize;
 				endIndex = endIndex<=0?endIndex:0;
 				
 				activeValues = Arrays.copyOfRange(previousSequentialValuesBlock[p], startIndex, endIndex);

 				_logger.info("Retrieving values [" + startIndex + "-" + endIndex + "] actual:" + activeValues.length + " from Previous Block only");
 				
 			} else {
 				//If none are found just return an empty list
 				return measList;
 			}
			
			startTime = new Date(blockStartTimeMs+startIndex*sleepDurationMs);

			Map<Long, GroupedValues> groupedValues = 
					GroupByTimeHelper.groupValuesByTime(
							activeValues, 
							startTime.getTime(), 
							10*ONE_SECOND, 
							groupByTime);
			
			for (Long groupStartTimeMs:groupedValues.keySet()) {
				MeasurementImpl sampleRaw = new MeasurementImpl();
				sampleRaw.setDurationSecs(
						(int)(groupedValues.get(groupStartTimeMs).getDurationMs()/1000l));
				sampleRaw.setName(parameterList[p]);
				sampleRaw.setTimestamp(new Date(groupedValues.get(groupStartTimeMs).getActualStart()));
				sampleRaw.setUnit(parameterUnits[p]);
				sampleRaw.setValueCode(valuesMode);
				
				
				double[] processedValues = 
						ValuesModeHelper.getProcessedValues(
								valuesMode,
								groupedValues.get(groupStartTimeMs).getValues());

				Float[] processedValuesFloat = new Float[processedValues.length];
				for (int i=0;i<processedValues.length;i++) {
					processedValuesFloat[i] = new Float(processedValues[i]);
				}
				sampleRaw.setValues(processedValuesFloat);
				
				measList.add(sampleRaw);
				
			}
		}
			
		return measList;
	}
	

	//We never want to implement at this level - delegate to concrete class
	@Override
	public void run() {
		long now = System.currentTimeMillis();
		
		_logger.info("Generating value for " + 
				getName() + " at " + new Date() + " ID: " + valuesCount);
		
		//After each block switch
		if (valuesCount == 0) {
			//Round to nearest second
			blockStartTimeMs = now - now % 1000l;
		}

		abstractRun();
		
		//After the block has filled we rotate it around and 
		// persist the old ones if necessary
		if (valuesCount == blockSize-1) {
			createNewBlock();
		} else {
			valuesCount++;
		}
		
		//finally update the time
		setEndTime(now);
	};
	
	protected abstract void abstractRun();

	public long getSleepDurationMs() {
		return sleepDurationMs;
	}

	public void setSleepDurationMs(long sleepDurationMs) {
		this.sleepDurationMs = sleepDurationMs;
	}
	
	protected void createNewBlock() {
		previousSequentialValuesBlock = sequentialValuesBlock;
		sequentialValuesBlock = new double[getParameterNames().length][blockSize];
		valuesCount = 0;
		valuesStart = -blockSize;
		_logger.fine("Swicthing over block for " + name + " after " + blockSize + " entries."); 
	}

	public byte[] getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(byte[] uniqueId) {
		this.uniqueId = uniqueId;
	}

	public abstract String[] getParameterNames();

	public abstract String[] getParameterUnits();

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RunDetails stop() {
		
		return new RunDetailsImpl(null, new Date());
	}

	@Override
	public RunDetails deleteData() {
		RunDetails runDetails = stop();
		
		sequentialValuesBlock = new double[0][0]; 
		previousSequentialValuesBlock = new double[0][0];
		
		return runDetails;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	private String[] getUnitsForParameters(String[] parameterNames) {
		
		String[] paramUnits = new String[parameterNames.length];
		
		int p=0;
		//We need to get the units for the paremeters specified on input
		for (String parameterName:parameterNames) {
			//Search through the static parameter list
			for (int i=0;i<getParameterNames().length;i++) {
				if (parameterName.equalsIgnoreCase(getParameterNames()[i])) {
					paramUnits[p] = getParameterUnits()[i];
					break;
				}
			}
			p++;
		}
		
		return paramUnits;
	}
}
