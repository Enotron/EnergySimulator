package org.enotron.simulator;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.enotron.EnergySimulatorEndpoint;
import org.enotron.simulator.exception.SimulatorInternalException;
import org.enotron.simulator.services.ConfigurationService;
import org.enotron.simulator.services.DataService;
import org.enotron.simulator.services.ExecutionService;
import org.enotron.simulator.utility.ByteArrayHelper;
import org.enotron.simulator.web_service.CallContext;
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
import org.enotron.simulator.web_service.MetadataResponse;
import org.enotron.simulator.web_service.StartGridsimFault;
import org.enotron.simulator.web_service.StartGridsimRequest;
import org.enotron.simulator.web_service.StartGridsimResponse;
import org.enotron.simulator.web_service.StopGridsimFault;
import org.enotron.simulator.web_service.StopGridsimRequest;
import org.enotron.simulator.web_service.StopGridsimResponse;
import org.enotron.simulator.web_service.TimeScope;
import org.enotron.simulator.web_service.ValuesMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/energysimrs")
public class SimulatorRestController {
	private Logger _logger = Logger.getLogger(this.getClass().getName());
	

	@Autowired
	private ConfigurationService simulatorConfigService;
	private DataService simulatorDataService;
	private ExecutionService simulatorExecutionService;
	private EnergySimulatorEndpoint wsEndpoint;
	
	private DatatypeFactory factory;
	
	public SimulatorRestController() throws DatatypeConfigurationException {
		_logger.info("Starting application");
		factory = DatatypeFactory.newInstance();
	}
	
	@PostConstruct
	private void setUpWsEndpoint() {
		//just make a general instance of EndPoint
		wsEndpoint = new EnergySimulatorEndpoint(simulatorConfigService, simulatorDataService, simulatorExecutionService);
	}

	
	@RequestMapping("/metadata")
	public MetadataResponse getMetadata() throws ParseException {
		
		return wsEndpoint.getMetadata();
	}
	
	@RequestMapping("/listGridsims")
	public ListGridsimsResponse listGridsims() throws ParseException {
		
		return wsEndpoint.listGridsims();
	}
	
	@RequestMapping("/createGridsim")
	public CreateGridsimResponse createGridsim(
			@RequestParam(value="name",required=true)String name,
			@RequestParam(value="type",required=true)String type,
			@RequestParam(value="description",required=false,defaultValue="")String description,
			@RequestParam(value="latitude",required=false,defaultValue="0.0")double latitude,
			@RequestParam(value="longitude",required=false,defaultValue="0.0")double longitude,
			@RequestParam(value="blockSize",required=false,defaultValue="100")int blockSize)
			throws CreateGridsimFault {
		
		CreateGridsimRequest createGridSimPayload = new CreateGridsimRequest();
		createGridSimPayload.setSimulatorName(name);
		createGridSimPayload.setSimulatorType(type);
		createGridSimPayload.setDescription(description);
		createGridSimPayload.setLatitude(latitude);
		createGridSimPayload.setLongitude(longitude);
		createGridSimPayload.setBlockSize(blockSize);
		
		return wsEndpoint.createGridsim(createGridSimPayload);

	}
	
	
	@RequestMapping("/deleteGridSim")
	public DeleteGridsimResponse deleteGridSim(
			@RequestParam(
					value="simulatorId",
					required=true)
						String simulatorId,
			@RequestParam(
					value="userName",
					required=false)
						String userName,
			@RequestParam(
					value="callReference",
					required=false)
						String callReference			
			) throws DeleteGridsimFault {
		
		
		_logger.info("Deleting Simulator " + simulatorId + 
				" through REST. User: " + userName + ", Ref: " + callReference);
		DeleteGridsimRequest req = new DeleteGridsimRequest();
		req.setSimulatorid(ByteArrayHelper.fromHexString(simulatorId));
		
		CallContext callContext = new CallContext();
		callContext.setUserName(userName);
		callContext.setCallingApplication(callReference);
		req.setCallContext(callContext);
		
		return wsEndpoint.deleteGridSim(req);
		
	}
	
	
	
	@RequestMapping("/getData")
	public GetDataResponse getData(
			@RequestParam(
				value="simulatorId",
				required=true)
					String simulatorId,
			@RequestParam(
				value="startTime",
				required=false)
					Date startTime,
			@RequestParam(
				value="endTime",
				required=false)
					Date endTime,
			@RequestParam(
				value="valuesMode",
				required=false,
				defaultValue="MIN_MEAN_MAX_STDDEV")
					String valuesMode,
			@RequestParam(
				value="groupBy",
				required=true,
				defaultValue="TENMINUTE")
					String groupBy
		) throws GetDataFault {

		GetDataRequest getDataPayload = new GetDataRequest();
		
		getDataPayload.setSimulatorid(ByteArrayHelper.fromHexString(simulatorId));

		GregorianCalendar startCal = new GregorianCalendar();
		startCal.setTime(startTime);
		getDataPayload.setStartTime(factory.newXMLGregorianCalendar(startCal));
		
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(startTime);
		getDataPayload.setEndTime(factory.newXMLGregorianCalendar(endCal));
		
		getDataPayload.setGroupBy(TimeScope.valueOf(groupBy));

		getDataPayload.setValuesMode(ValuesMode.fromValue(valuesMode));
		
		return wsEndpoint.getData(getDataPayload);
	}
	
	
	@RequestMapping("/startGridSim")
	public StartGridsimResponse startGridSim(
			@RequestParam(
					value="simulatorId",
					required=true)
						String simulatorId,
			@RequestParam(
					value="userName",
					required=false)
						String userName,
			@RequestParam(
					value="callReference",
					required=false)
						String callReference
			) throws StartGridsimFault, SimulatorInternalException {

		StartGridsimRequest req = new StartGridsimRequest();
		req.setSimulatorid(ByteArrayHelper.fromHexString(simulatorId));
		
		CallContext callContext = new CallContext();
		callContext.setUserName(userName);
		callContext.setCallingApplication(callReference);
		req.setCallContext(callContext);
		
		return wsEndpoint.startGridSim(req);
	}

	@RequestMapping("/stopGridSim")
	public StopGridsimResponse stopGridSim(
			@RequestParam(
					value="simulatorId",
					required=true)
						String simulatorId,
			@RequestParam(
					value="userName",
					required=false)
						String userName,
			@RequestParam(
					value="callReference",
					required=false)
						String callReference

			) throws StopGridsimFault, SimulatorInternalException {

		StopGridsimRequest req = new StopGridsimRequest();
		req.setSimulatorid(ByteArrayHelper.fromHexString(simulatorId));
		
		CallContext callContext = new CallContext();
		callContext.setUserName(userName);
		callContext.setCallingApplication(callReference);
		req.setCallContext(callContext);
		
		return wsEndpoint.stopGridSim(req);
	}
	
	public ConfigurationService getSimulatorConfigService() {
		return simulatorConfigService;
	}


	public void setSimulatorConfigService(
			ConfigurationService simulatorConfigService) {
		this.simulatorConfigService = simulatorConfigService;
	}
}
