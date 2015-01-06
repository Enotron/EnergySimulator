package org.enotron;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.enotron.simulator.api.GroupByTime;
import org.enotron.simulator.api.MeasurementImpl;
import org.enotron.simulator.api.RunDetails;
import org.enotron.simulator.api.Simulator;
import org.enotron.simulator.api.SimulatorException;
import org.enotron.simulator.exception.SimulatorInternalException;
import org.enotron.simulator.services.ConfigurationService;
import org.enotron.simulator.services.DataService;
import org.enotron.simulator.services.ExecutionService;
import org.enotron.simulator.utility.ByteArrayHelper;
import org.enotron.simulator.utility.CalendarHelper;
import org.enotron.simulator.web_service.CreateGridsimFault;
import org.enotron.simulator.web_service.CreateGridsimRequest;
import org.enotron.simulator.web_service.CreateGridsimResponse;
import org.enotron.simulator.web_service.DeleteGridsimFault;
import org.enotron.simulator.web_service.DeleteGridsimRequest;
import org.enotron.simulator.web_service.DeleteGridsimResponse;
import org.enotron.simulator.web_service.GetDataFault;
import org.enotron.simulator.web_service.GetDataRequest;
import org.enotron.simulator.web_service.GetDataResponse;
import org.enotron.simulator.web_service.ListGridsimsResponse;
import org.enotron.simulator.web_service.Measurement;
import org.enotron.simulator.web_service.MetadataResponse;
import org.enotron.simulator.web_service.Parameter;
import org.enotron.simulator.web_service.SimulatorDetails;
import org.enotron.simulator.web_service.SimulatorDetailsAndRun;
import org.enotron.simulator.web_service.SimulatorMetadata;
import org.enotron.simulator.web_service.SimulatorRun;
import org.enotron.simulator.web_service.StartGridsimFault;
import org.enotron.simulator.web_service.StartGridsimRequest;
import org.enotron.simulator.web_service.StartGridsimResponse;
import org.enotron.simulator.web_service.StopGridsimFault;
import org.enotron.simulator.web_service.StopGridsimRequest;
import org.enotron.simulator.web_service.StopGridsimResponse;
import org.enotron.simulator.web_service.ValuesMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * SOAP Web Service End Point
 * 
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
@Endpoint
public class EnergySimulatorEndpoint {
	private static final String NAMESPACE_URI = "http://simulator.enotron.org/web-service";
	private Logger _logger = Logger.getLogger(this.getClass().getName());

	private ConfigurationService configuration;
	private DataService simdata;
	private ExecutionService execution;
	
	private DatatypeFactory factory;

	public EnergySimulatorEndpoint() {
		
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			_logger.warning("Exception on XML DataFactory Creation: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	@Autowired
	public EnergySimulatorEndpoint(
			ConfigurationService configuration, 
			DataService simdata, 
			ExecutionService execution) {
		this();
		this.configuration = configuration;
		this.simdata = simdata;
		this.execution = execution;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDataRequest")
	@ResponsePayload
	public GetDataResponse getData(
			@RequestPayload GetDataRequest getDataPayload) 
					throws GetDataFault {
		
		if (getDataPayload.getValuesMode() == null) {
			throw new GetDataFault("Invalid value for 'ValuesMode' use one of enumerated values");
		}
		
		GetDataResponse resp = new GetDataResponse();
		
		Date startTime=null, endTime=null;
		if (getDataPayload.getStartTime() != null) {
			startTime = new Date(getDataPayload.getStartTime().getMillisecond());
		}
		
		if (getDataPayload.getEndTime() != null) {
			endTime = new Date(getDataPayload.getEndTime().getMillisecond());
		}

		try {
			for (MeasurementImpl measurement: 
					simdata.getMeaurementList(
							getDataPayload.getSimulatorid(), 
							getDataPayload.getParameterList().toArray(
									new String[getDataPayload.getParameterList().size()]), 
							org.enotron.simulator.api.ValuesMode.valueOf(
									getDataPayload.getValuesMode().name()),
							startTime,
							endTime, 
							GroupByTime.valueOf(getDataPayload.getGroupBy().name()))) {
				
				Measurement measSoap = new Measurement();
				measSoap.setDurationSecs(measurement.getDurationSecs());
				measSoap.setName(measurement.getName());
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTimeInMillis(measurement.getTimestamp().getTime());
				measSoap.setTimestamp(factory.newXMLGregorianCalendar(cal));
				measSoap.setUnit(measurement.getUnit());
				measSoap.setValueCode(ValuesMode.valueOf(measurement.getValueCode().name()));
				
				for (Float measValue:measurement.getValues()) {
					measSoap.getValues().add(measValue);
				}
				
				resp.getMeasurement().add(measSoap);
			}
			
			return resp;
		} catch (SimulatorException e) {
			e.printStackTrace();
			throw new GetDataFault(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GetDataFault(
					"Exception from getData " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * Notes:
	 * 1. The name of the localPart should be the same as the Request Type, 
	 * 		but start with a lower case letter
	 * 2. The name of the method should be the same as the operation in the WSDL
	 * 			i.e the same as the RequestType but with Request removed
	 * 
	 * @param createGridSimPayload
	 * @return
	 * @throws SimulatorException 
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createGridsimRequest")
	@ResponsePayload
	public CreateGridsimResponse createGridsim(
			@RequestPayload CreateGridsimRequest createGridSimPayload) throws CreateGridsimFault {
		
		_logger.info("Creating instance of " + createGridSimPayload.getSimulatorType());
		CreateGridsimResponse resp = new CreateGridsimResponse();
		
		Simulator simulator = null;
		try {
		
			simulator = configuration.createSimulator(
					createGridSimPayload.getSimulatorType(),
					createGridSimPayload.getSimulatorName(), 
					(createGridSimPayload.getBlockSize()==null?0:createGridSimPayload.getBlockSize().intValue()),
					createGridSimPayload.getLatitude(),
					createGridSimPayload.getLongitude(),
					createGridSimPayload.getDescription());

			resp.setSimulatorDetails(new SimulatorDetails());
			resp.getSimulatorDetails().setSimulatorName(simulator.getName());
			resp.getSimulatorDetails().setSimulatorType(createGridSimPayload.getSimulatorType());
			resp.getSimulatorDetails().setSimulatorUid(simulator.getUniqueId());
			resp.getSimulatorDetails().setLongitude(simulator.getLongitude());
			resp.getSimulatorDetails().setLatitude(simulator.getLatitude());
			resp.getSimulatorDetails().setDescription(simulator.getDescription());
			
			
			return resp;
			
		} catch (SimulatorException e) {
			//The Fault Class (generated from JAXB)
			// has to extend Exception (not Throwable)
			// and must have annotation @SoapFault(faultCode=FaultCode.CLIENT)
			// and constructor ..(String message, Throwable t)
			throw new CreateGridsimFault(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateGridsimFault(e.getMessage(), e);
		}
				


	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "listGridsimsRequest")
	@ResponsePayload
	public ListGridsimsResponse listGridsims() {
		ListGridsimsResponse resp = new ListGridsimsResponse();
		
		for (ByteBuffer simId:configuration.getSimList().keySet()) {
			Simulator sim = configuration.getSimList().get(simId);
			
			SimulatorDetails simDetails = new SimulatorDetails();
			simDetails.setSimulatorName(sim.getName());
			simDetails.setSimulatorUid(simId.array());
			simDetails.setSimulatorType(sim.getClass().getSimpleName());
			simDetails.setLongitude(sim.getLongitude());
			simDetails.setLatitude(sim.getLatitude());
			simDetails.setDescription(sim.getDescription());
			
			SimulatorDetailsAndRun sdr = new SimulatorDetailsAndRun();
			sdr.setSimulatorDetails(simDetails);

			if (execution != null) {
				try {
					SimulatorRun run = 
							convertRunDetails(
									execution.getSimulatorStatus(simId.array()));
					sdr.setSimulatorRun(run);
					
				} catch (SimulatorException e) {
					e.printStackTrace();
				}
			}
			resp.getSimulatorDetails().add(sdr);
		}
		
		return resp;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "startGridsimRequest")
	@ResponsePayload
	public StartGridsimResponse startGridSim(@RequestPayload StartGridsimRequest req) 
			throws StartGridsimFault, SimulatorInternalException {

		StartGridsimResponse resp = new StartGridsimResponse();

		try {
			RunDetails runDetails = 
					execution.startSimulator(req.getSimulatorid());

			if (runDetails.isRunning()) {
				_logger.info("Simulator " + 
							ByteArrayHelper.toHexString(req.getSimulatorid()) + 
							" has been started successfully");
			}

			resp.setSimulatorUid(req.getSimulatorid());
			resp.setRunDetails(convertRunDetails(runDetails));

		} catch (SimulatorException se) {
			_logger.warning("Exception while running startSimulator");
			throw new StartGridsimFault("Exception while running startSimulator. " + 
					se.getMessage(), se);
			
		} catch (Exception ex) {
			_logger.warning("Exception while running startSimulator");
			ex.printStackTrace();
			throw new SimulatorInternalException("Exception while running startSimulator", ex);
		}
		
				
		return resp;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "stopGridsimRequest")
	@ResponsePayload
	public StopGridsimResponse stopGridSim(@RequestPayload StopGridsimRequest req) 
			throws StopGridsimFault, SimulatorInternalException {

		StopGridsimResponse resp = new StopGridsimResponse();

		try {
			RunDetails runDetails = execution.stopSimulator(req.getSimulatorid());
			if (!runDetails.isRunning()) {
				_logger.info("Simulator " + 
							ByteArrayHelper.toHexString(req.getSimulatorid()) + 
							" has been stopped successfully");
			}

			resp.setSimulatorUid(req.getSimulatorid());
			resp.setRunDetails(convertRunDetails(runDetails));
			
		} catch (SimulatorException se) {
			_logger.warning("Exception while stopping simulator");
			throw new StopGridsimFault("Exception while stopping simulator. " + 
					se.getMessage(), se);
			
		} catch (Exception ex) {
			_logger.warning("Exception while stopping simulator");
			ex.printStackTrace();
			throw new SimulatorInternalException("Exception while stopping simulator", ex);
		}
		
		
		return resp;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteGridsimRequest")
	@ResponsePayload
	public DeleteGridsimResponse deleteGridSim(@RequestPayload DeleteGridsimRequest req) 
			throws DeleteGridsimFault {
		
		DeleteGridsimResponse resp = new DeleteGridsimResponse();
		
		try {
			SimulatorDetails sd = new SimulatorDetails();
			sd.setSimulatorUid(req.getSimulatorid());
			resp.setSimulatorDetails(sd);
			
			RunDetails runDetails = configuration.deleteGridSim(req.getSimulatorid());
			resp.setRunDetails(convertRunDetails(runDetails));
			
		} catch (SimulatorException e) {
			_logger.warning(e.getMessage());
			throw new DeleteGridsimFault(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DeleteGridsimFault("Internal error in Simulator", e);
		}
		
		return resp;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "metadataRequest")
	@ResponsePayload
	public MetadataResponse getMetadata() {
		
		MetadataResponse resp = new MetadataResponse();
		resp.setVersion("1.0.0");
		resp.setDescription("Enotron Smart Grid Simulator www.enotron.org");
		resp.setBuildDate(factory.newXMLGregorianCalendar(2014, 11, 06, 0, 0, 0, 0, 0));

		for (String simType:
				configuration.getSimulatorTypes()) {
			
			SimulatorMetadata simMd = new SimulatorMetadata();
			simMd.setSimulatorClass(simType);
			simMd.setSimulatorType(simType.substring(simType.lastIndexOf(".")));

			try {
				String[] paramNames = configuration.getParameterDetails(simType, "parameterNames");
				String[] paramUnits = configuration.getParameterDetails(simType, "parameterUnits");
				
				for (int i=0;i<paramNames.length;i++) {
					Parameter param = new Parameter();
					param.setParameterName(paramNames[i]);
					param.setParameterUnit(paramUnits[i]);
					simMd.getParameterList().add(param);
				}
				
			} catch (SimulatorException e) {
				_logger.warning("Exception in getting Metadata for Simulator " + 
						simType + ". " + e.getMessage());
				e.printStackTrace();
			}
			
			resp.getSimulatorMetadata().add(simMd);
		}
		
		return resp;
	}
	
	
	private SimulatorRun convertRunDetails(RunDetails rd) {
		SimulatorRun sr = new SimulatorRun();
		sr.setRunnning(rd.isRunning());
		
		if (rd.getDurationMs() > 0) {
			sr.setRunTime(factory.newDuration(rd.getDurationMs()));
		}
		
		if (rd.getStartTime() != null ) {
			sr.setStartTime(
					factory.newXMLGregorianCalendar(
							CalendarHelper.forDate(rd.getStartTime())));
		}
		
		if (rd.getEndTime() != null) {
			sr.setStopTime(
					factory.newXMLGregorianCalendar(
							CalendarHelper.forDate(rd.getEndTime())));
		}
		
		return sr;
	}
}
