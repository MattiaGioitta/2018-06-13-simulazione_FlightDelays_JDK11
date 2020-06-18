package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;
import it.polito.tdp.flightdelays.model.Event.EventType;

public class Simulator {
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	//stato
	private int K;
	private int V;
	private List<Flight> voli;
	private FlightDelaysDAO dao;
	private Map<Integer,Passeggero> passeggeri;
	private Map<Integer,Airport> aeroporti;
	
	public void init(int k, int v) {
		this.K=k;
		this.V=v;
		this.dao = new FlightDelaysDAO();
		this.voli = new ArrayList<>();
		this.passeggeri = new HashMap<>();
		this.aeroporti = new HashMap<>();		
		this.dao.loadAllAirports(aeroporti);
		this.voli = this.dao.loadAllFlights();
		this.queue = new PriorityQueue<>();		
		for(int i=1;i<=K;i++) {
			this.passeggeri.put(i, new Passeggero(i));
		}
		for(Passeggero p : this.passeggeri.values()) {
			Airport a = assegnaAeroporto();
			Flight f = this.dao.getPrimoVolo(a);
			
			this.queue.add(new Event(f.getScheduledDepartureDate(),p,EventType.PARTENZA,f));			
		}
	}

	private Airport assegnaAeroporto() {
		int random = ThreadLocalRandom.current().nextInt(0, this.aeroporti.size());		
		return this.aeroporti.get(random);
	}
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getTipo()) {
		case PARTENZA:
			if(this.passeggeri.get(e.getP().getId()).numeroVoli()<=V) {
			this.passeggeri.get(e.getP().getId()).aggiungiVolo(e.getVolo());
			Event evento = new Event(e.getVolo().getArrivalDate(),e.getP(),EventType.ARRIVO,e.getVolo());
			this.queue.add(evento);	
			}	
			break;
		case ARRIVO:
			if(this.passeggeri.get(e.getP().getId()).numeroVoli()<=V) {
			Airport a = this.aeroporti.get(e.getVolo().getDestinationAirportId());
			Flight f = this.dao.getPrimoVoloDisponibile(a,e.getVolo());
			Event event = new Event(f.getScheduledDepartureDate(),e.getP(),EventType.PARTENZA,f);
			this.queue.add(event);
			}
			break;
		}
		
	}

	public Collection<Passeggero> getPasseggeri() {
		// TODO Auto-generated method stub
		return this.passeggeri.values();
	}

	

}
