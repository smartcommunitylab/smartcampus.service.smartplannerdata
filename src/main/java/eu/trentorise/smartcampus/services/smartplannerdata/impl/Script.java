package eu.trentorise.smartcampus.services.smartplannerdata.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.BikeSharingStation;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.CarParkingStation;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.CarSharingStation;

public class Script {

	@SuppressWarnings("unchecked")
	public List<?> createParkings(String data, String agencyId) throws Exception {
		List<Map<String,Object>> maps = new ObjectMapper().readValue(data, List.class);
		List<CarParkingStation> result = new ArrayList<Smartplannerdata.CarParkingStation>();
		for (Map<String,Object> map : maps) {
			Map<String,String> station = (Map<String,String>) map.get("stationId");
			String oAgencyId = (String)station.get("agencyId");
			if (!oAgencyId.equals(agencyId)) continue;

			CarParkingStation.Builder builder = CarParkingStation.newBuilder();
			builder.setAgencyId(oAgencyId);
			builder.setId((String)station.get("id"));
			builder.setStationId(builder.getId()+"@"+builder.getAgencyId());
			builder.setAddress((String)map.get("id"));
			builder.setAvailablePlaces((Integer)map.get("posts"));
			List<Double> position = (List<Double>) map.get("position");
			builder.setLat(position.get(0));
			builder.setLng(position.get(1));
			result.add(builder.build());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> createCarSharings(String data, String agencyId) throws Exception {
		List<Map<String,Object>> maps = new ObjectMapper().readValue(data, List.class);
		List<CarSharingStation> result = new ArrayList<Smartplannerdata.CarSharingStation>();
		for (Map<String,Object> map : maps) {
			Map<String,String> station = (Map<String,String>) map.get("stationId");
			String oAgencyId = (String)station.get("agencyId");
			if (!oAgencyId.equals(agencyId)) continue;

			CarSharingStation.Builder builder = CarSharingStation.newBuilder();
			builder.setAgencyId(oAgencyId);
			builder.setId((String)station.get("id"));
			builder.setStationId(builder.getId()+"@"+builder.getAgencyId());
			builder.setAddress((String)map.get("id"));
			builder.setAvailableCars((Integer)map.get("availableSharingVehicles"));
			builder.setAvailableParkingPlaces((Integer)map.get("posts"));
			List<Double> position = (List<Double>) map.get("position");
			builder.setLat(position.get(0));
			builder.setLng(position.get(1));
			result.add(builder.build());
		}
		return result;
	}
	public List<?> createBikeSharings(String data, String agencyId) throws Exception {
		List<Map<String,Object>> maps = new ObjectMapper().readValue(data, List.class);
		List<BikeSharingStation> result = new ArrayList<Smartplannerdata.BikeSharingStation>();
		for (Map<String,Object> map : maps) {
			Map<String,String> station = (Map<String,String>) map.get("stationId");
			String oAgencyId = (String)station.get("agencyId");
			if (!oAgencyId.equals(agencyId)) continue;

			BikeSharingStation.Builder builder = BikeSharingStation.newBuilder();
			builder.setAgencyId(oAgencyId);
			builder.setId((String)station.get("id"));
			builder.setStationId(builder.getId()+"@"+builder.getAgencyId());
			builder.setAddress((String)map.get("id"));
			builder.setAvailableBikes((Integer)map.get("availableSharingVehicles"));
			builder.setAvailableParkingPlaces((Integer)map.get("posts"));
			List<Double> position = (List<Double>) map.get("position");
			builder.setLat(position.get(0));
			builder.setLng(position.get(1));
			result.add(builder.build());
		}
		return result;
	}
}
