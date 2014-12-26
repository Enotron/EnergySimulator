package org.enotron.simulator.services;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.enotron.simulator.api.RunDetails;
import org.enotron.simulator.api.Simulator;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.impl.simulator.AbstractSimulator;
import org.enotron.simulator.utility.ByteArrayHelper;

public class ConfigurationService {

	private Logger _logger = Logger.getLogger(this.getClass().getName());
	
	private Set<String> simulatorTypes;
	
	private Map<ByteBuffer, Simulator> simList;
	
	private final static Date startDate = new Date();
	
	public ConfigurationService() {
		
	}
	
	public ConfigurationService(Set<String> simulatorTypes) {
		this.simulatorTypes = simulatorTypes;
		
		setSimList(new HashMap<ByteBuffer,Simulator>());
				
	}
	
	public Simulator createSimulator(
			String simulatorType, 
			String simulatorName, 
			int blockSize,
			double longitude,
			double latitude,
			String desc ) 
			throws SimulatorException {
		
		if (simulatorType == null) {
			throw new SimulatorException(
					"No SimulatorType specified. Use one of " + 
					getSimulatorTypesString(),-125);
		}
		
		_logger.fine("Configuration Service created new Simulator of type " + 
				simulatorType + " as " + simulatorName);
		
		try {
			Class<?> simClass = this.getClass().getClassLoader().
					loadClass("org.enotron.simulator.impl.simulator." + simulatorType);

			Simulator sim = (Simulator) simClass.newInstance();
			
			sim.setName(simulatorName);
			sim.setLatitude(latitude);
			sim.setLongitude(longitude);
			sim.setDescription(desc);
			
			getSimList().put(ByteBuffer.wrap(sim.getUniqueId()), sim);
			
			return sim;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
				
				
		throw new SimulatorException(
				"Invalid SimulatorType specified " + simulatorType + 
				". Use one of " + 
				getSimulatorTypesString(),-126);
	}
	
	public RunDetails deleteGridSim(byte[] gridsimId) throws SimulatorException {
		String simulatorIdHex = ByteArrayHelper.toHexString(gridsimId);
		
		_logger.info("Stopping simulator " + simulatorIdHex);

		//Check that this is a valid simulator ID - throws exception if it's not
		AbstractSimulator sim = (AbstractSimulator) getSimulator(gridsimId);
		
		ByteBuffer simKey = ByteBuffer.wrap(gridsimId);
		
		sim.stop();
		RunDetails runDetails = sim.deleteData();
		
		getSimList().remove(simKey);
		
		return runDetails;
	}


	public Set<String> getSimulatorTypes() {
		return simulatorTypes;
	}

	public String getSimulatorTypesString() {
		
		StringBuffer sb = new StringBuffer();
		
		boolean first=true; 
		for (String simType:getSimulatorTypes()) {
			if (first) first=false;
			else sb.append(", ");
			
			sb.append(simType.substring(simType.lastIndexOf(".")+1));
		}
		
		return sb.toString();
	}
	
	public String[] getParameterDetails(String simType, String fieldName) throws SimulatorException {
		try {
			
			Class<?> simClass = this.getClass().getClassLoader().loadClass(simType);
			Field f = simClass.getField(fieldName);
			_logger.fine("Retrieved field " + f.getName() + " of class " + 
					simClass.getName() + " Modifiers: " + f.getModifiers());
			Object o = f.get(null);

			if (o instanceof String[]) {
				
				return (String[])o;

			} else {
				_logger.warning("Cannot decode field " + fieldName + " of type " + o.getClass().getName());
				return new String[0];
			}
		} catch (NoSuchFieldException e) {
			_logger.warning(fieldName + " field of Simulator class not found. Expecting \"public static String[]\" " + e.getMessage());
			e.printStackTrace();
			return new String[0];
		} catch (Exception e) {
			throw new SimulatorException("Exception " + e.getClass().getSimpleName() + 
					" when getting " + fieldName + " for " + simType +
					". " + e.getMessage(), -138);
		}
	}

	public Map<ByteBuffer, Simulator> getSimList() {
		return simList;
	}

	public void setSimList(HashMap<ByteBuffer, Simulator> hashMap) {
		this.simList = hashMap;
	}
	
	private Simulator getSimulator(byte[] simulatorUid) throws SimulatorException {

		ByteBuffer simKey;
		try {
			simKey = ByteBuffer.wrap(simulatorUid);
		} catch (Exception ex) {
			throw new SimulatorException("Invalid value passed as Simulator ID", -141);
		}
		

		Simulator sim = getSimList().get(simKey);
		if (sim == null) {
			String simulatorIdHex = ByteArrayHelper.toHexString(simulatorUid);

			String keyList = "KeyList:";
			for (ByteBuffer simulatorKey:getSimList().keySet()) {
				keyList += ("," + ByteArrayHelper.toHexString(simulatorKey.array()));
				if (simulatorKey == simKey) {
					_logger.warning("WTF - matches" );
				} else {
					_logger.warning("No match :-(" );
				}
			}
			throw new SimulatorException(
					"Failed to find simulator with ID " + 
							simulatorIdHex + " " + keyList, -139);
		}
		
		return sim;
	}

	public Date getStartDate() {
		return startDate;
	}

}
