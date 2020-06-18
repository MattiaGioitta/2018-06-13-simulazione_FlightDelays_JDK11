package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		PARTENZA,
		ARRIVO,
	}
	
	private LocalDateTime time;
	private Passeggero p;
	private EventType tipo;
	private Flight volo;
	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * @return the p
	 */
	public Passeggero getP() {
		return p;
	}
	/**
	 * @return the tipo
	 */
	public EventType getTipo() {
		return tipo;
	}
	/**
	 * @return the volo
	 */
	public Flight getVolo() {
		return volo;
	}
	/**
	 * @param time
	 * @param p
	 * @param tipo
	 * @param volo
	 */
	public Event(LocalDateTime time, Passeggero p, EventType tipo, Flight volo) {
		super();
		this.time = time;
		this.p = p;
		this.tipo = tipo;
		this.volo = volo;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
	

}
