package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flightdelays.model.Adiacenza;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Flight;

public class FlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT id, airline from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("id"), rs.getString("airline")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadAllAirports(Map<Integer, Airport> idMap) {
		String sql = "SELECT id, airport, city, state, country, latitude, longitude FROM airports";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("id"), rs.getString("airport"), rs.getString("city"),
						rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"), rs.getDouble("longitude"));
				idMap.put(airport.getId(), airport);
			}
			
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT id, airline_id, flight_number, origin_airport_id, destination_airport_id, scheduled_departure_date, "
				+ "arrival_date, departure_delay, arrival_delay, elapsed_time, distance FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("id"), rs.getString("airline_id"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("elapsed_time"), rs.getInt("distance"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> getAdiacenze(Airline a, Map<Integer, Airport> idMap) {
		final String sql = "SELECT f.ORIGIN_AIRPORT_ID AS a1,f.DESTINATION_AIRPORT_ID AS a2,AVG(f.ARRIVAL_DELAY) AS peso " + 
				"FROM flights AS f " + 
				"WHERE f.AIRLINE_ID=? " + 
				"GROUP BY f.ORIGIN_AIRPORT_ID,f.DESTINATION_AIRPORT_ID";
		List<Adiacenza> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,a.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza ad = new Adiacenza(idMap.get(res.getInt("a1")),idMap.get(res.getInt("a2")),res.getDouble("peso"));
				lista.add(ad);
			}

			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public Flight getPrimoVolo(Airport a) {
		String sql = "SELECT id, airline_id, flight_number, origin_airport_id, destination_airport_id, scheduled_departure_date, " + 
				"arrival_date, departure_delay, arrival_delay, elapsed_time, distance " + 
				"FROM flights AS f " + 
				"WHERE f.ORIGIN_AIRPORT_ID=? " + 
				"ORDER BY (f.SCHEDULED_DEPARTURE_DATE) " + 
				"LIMIT 1";
		Flight volo = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Integer.toString(a.getId()));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				volo = new Flight(rs.getInt("id"), rs.getString("airline_id"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("elapsed_time"), rs.getInt("distance"));
			}

			conn.close();
			return volo;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}		
	}

	public Flight getPrimoVoloDisponibile(Airport a, Flight flight) {
		String sql = "SELECT id, airline_id, flight_number, origin_airport_id, destination_airport_id, scheduled_departure_date, " + 
				"arrival_date, departure_delay, arrival_delay, elapsed_time, distance " + 
				"FROM flights AS f " + 
				"WHERE f.ORIGIN_AIRPORT_ID=? " + 
				"AND f.SCHEDULED_DEPARTURE_DATE>? " + 
				"ORDER BY (f.SCHEDULED_DEPARTURE_DATE) " + 
				"LIMIT 1";
		Flight volo = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			st.setTimestamp(2, Timestamp.valueOf(flight.getArrivalDate()));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				volo = new Flight(rs.getInt("id"), rs.getString("airline_id"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("elapsed_time"), rs.getInt("distance"));
			}

			conn.close();
			return volo;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}				
	}
}

