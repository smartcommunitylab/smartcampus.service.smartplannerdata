package eu.trentorise.smartcampus.services.smartplannerdata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.BikeSharingStation;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.CarParkingStation;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.CarSharingStation;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.RouteTimetable;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.TransitStop;
import eu.trentorise.smartcampus.services.smartplannerdata.data.message.Smartplannerdata.TripTime;

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
			builder.setStationId((String)map.get("id"));
			builder.setAddress(station.get("id"));
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
			builder.setStationId((String)map.get("id"));
			builder.setAddress(station.get("id"));
			builder.setAvailableCars((Integer)map.get("availableSharingVehicles"));
			builder.setAvailableParkingPlaces((Integer)map.get("posts"));
			List<Double> position = (List<Double>) map.get("position");
			builder.setLat(position.get(0));
			builder.setLng(position.get(1));
			result.add(builder.build());
		}
		return result;
	}
	@SuppressWarnings("unchecked")
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
			builder.setStationId((String)map.get("id"));
			builder.setAddress(station.get("id"));
			builder.setAvailableBikes((Integer)map.get("availableSharingVehicles"));
			builder.setAvailableParkingPlaces((Integer)map.get("posts"));
			List<Double> position = (List<Double>) map.get("position");
			builder.setLat(position.get(0));
			builder.setLng(position.get(1));
			result.add(builder.build());
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<HashMap> readRoutes(String routesdata) {
		List<Map<String, Object>> maps;
		try {
			maps = new ObjectMapper().readValue(routesdata, List.class);
		} catch (Exception e) {
			return new ArrayList<HashMap>();
		}
		ArrayList<HashMap> result = new ArrayList<HashMap>();
		for (Map<String,Object> map : maps) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			Map<String,String> idMap = (Map<String,String>)map.get("id");
			newMap.putAll(idMap);
			newMap.put("routeLongName",map.get("routeLongName"));
			newMap.put("routeShortName",map.get("routeShortName"));
			result.add(newMap);
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<HashMap> readStops(String routedata, HashMap route) {
		List<Map<String, Object>> maps;
		try {
			maps = new ObjectMapper().readValue(routedata, List.class);
		} catch (Exception e) {
			return new ArrayList<HashMap>();
		}
		ArrayList<HashMap> result = new ArrayList<HashMap>();
		for (Map<String,Object> map : maps) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			newMap.putAll(map);
			newMap.put("route", route);
			result.add(newMap);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap mergeStopData(String stopdata, HashMap stop, HashMap stopDataMap) {
		TransitStop.Builder stopMap = (TransitStop.Builder)stopDataMap.get(stop.get("id"));
		if (stopMap == null) {
			Map<String,Object> route = (Map<String, Object>) stop.get("route");
			stopMap = TransitStop.newBuilder();
			stopDataMap.put(stop.get("id"), stopMap);
			stopMap.setAgencyId(route.get("agency").toString());
			stopMap.setId(stop.get("id").toString());
			stopMap.setStopId(stopMap.getId()+"@"+stopMap.getAgencyId()+"@smartplanner-transitstops");
			stopMap.setLat(Double.parseDouble(stop.get("latitude").toString()));
			stopMap.setLng(Double.parseDouble(stop.get("longitude").toString()));
			stopMap.setName(stop.get("name").toString());
			stopMap.addAllTimetable(new ArrayList<RouteTimetable>());
		}

		List<Map<String, Object>> maps;
		try {
			maps = new ObjectMapper().readValue(stopdata, List.class);
		} catch (Exception e) {
			return stopDataMap;
		}
		
		RouteTimetable.Builder routeTimetable = RouteTimetable.newBuilder();
		Map<String,Object> route = (Map<String, Object>) stop.get("route");
		routeTimetable.setRouteId(route.get("id").toString());
		routeTimetable.setRouteLongName(route.get("routeLongName").toString());
		routeTimetable.setRouteShortName(route.get("routeShortName").toString());
		
		List<TripTime> result = new ArrayList<TripTime>();
		for (Map<String,Object> map : maps) {
			TripTime.Builder tripTime = TripTime.newBuilder();
			Map<String,String> trip = (Map<String, String>) map.get("trip");
			tripTime.setTripId(trip.get("id"));
			tripTime.setTime(Long.parseLong(map.get("time").toString()));
			result.add(tripTime.build());
		}
		routeTimetable.addAllTripTime(result);
		stopMap.addTimetable(routeTimetable.build());
		return stopDataMap;
	}
	
	@SuppressWarnings("rawtypes")
	public List<?> convertStopData(HashMap stopDataMap) {
		List<TransitStop> result = new ArrayList<Smartplannerdata.TransitStop>();
		for (Object value : stopDataMap.values()) {
			TransitStop.Builder builder = (TransitStop.Builder)value;
			result.add(builder.build());
		}
		return result;
	}

}
