package eu.trentorise.smartcampus.services.ou.test;

import it.sayservice.platform.core.common.exception.ServiceException;
import it.sayservice.platform.servicebus.test.DataFlowTestHelper;

import java.util.HashMap;
import java.util.Map;

import eu.trentorise.smartcampus.services.smartplannerdata.impl.GetCarParkingDataFlow;
import eu.trentorise.smartcampus.services.smartplannerdata.impl.GetCarSharingDataFlow;
import eu.trentorise.smartcampus.services.smartplannerdata.impl.GetTransitStopsDataFlow;

public class TestDataFlow {

	public static void main(String[] args) throws ServiceException {
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agencyId", "12");
		Map<String, Object> out = helper.executeDataFlow(
				"eu.trentorise.smartcampus.services.smartplannerdata.SmartplannerdataService", 
				"GetTransitStops", 
				new GetTransitStopsDataFlow(), 
				map);
		System.err.println(out);
	}
}
