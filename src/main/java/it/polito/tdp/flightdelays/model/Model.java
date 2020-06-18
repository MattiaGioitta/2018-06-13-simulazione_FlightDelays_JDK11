package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {

	private FlightDelaysDAO dao;
	private Graph<Airport,DefaultWeightedEdge> graph;
	private Map<Integer,Airport> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new FlightDelaysDAO();
		this.idMap = new HashMap<>();
	}
	
	public List<Airline> getAirlines() {
		return this.dao.loadAllAirlines();
	}

	public void createGraph(Airline a) {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadAllAirports(idMap);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(a,idMap);
		for(Adiacenza ad : adiacenze) {
			if(!this.graph.containsVertex(ad.getA1())) {
				this.graph.addVertex(ad.getA1());
			}
			if(!this.graph.containsVertex(ad.getA2())) {
				this.graph.addVertex(ad.getA2());
			}
			if(this.graph.getEdge(ad.getA1(), ad.getA2())==null) {
				Graphs.addEdgeWithVertices(this.graph, ad.getA1(), ad.getA2(),ad.getPeso());
			}
		}
		
		
	}

	public Integer nArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}

	public Integer nVertici() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}

	public List<Adiacenza> peggiori() {
		List<Adiacenza> peggiori = new ArrayList<>();
		for(DefaultWeightedEdge a : this.graph.edgeSet()) {
			peggiori.add(new Adiacenza(this.graph.getEdgeSource(a),this.graph.getEdgeTarget(a),this.graph.getEdgeWeight(a),true));
			
		}
		Collections.sort(peggiori);
		return peggiori;
	}

	public void simula(Integer k, Integer v) {
		this.sim = new Simulator();
		sim.init(k, v);
		sim.run();
	}

	public Collection<Passeggero> getPasseggeri() {
		// TODO Auto-generated method stub
		return this.sim.getPasseggeri();
	}

}
