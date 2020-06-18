package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.flightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = DBConnect.getConnection();
			connection.close();
			System.out.println("Test PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
		

		FlightDelaysDAO dao = new FlightDelaysDAO();
		Map<Integer, Airport> map = new HashMap<>();

		//System.out.println(dao.loadAllAirlines());
		dao.loadAllAirports(map);
		System.out.print(dao.getPrimoVolo(map.get(5)));
		//System.out.println(dao.loadAllFlights());
	}

}
