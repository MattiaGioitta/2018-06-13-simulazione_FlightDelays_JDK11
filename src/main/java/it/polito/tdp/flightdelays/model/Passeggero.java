package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.List;

public class Passeggero {
	
	private int id;
	private List<Flight> voli;
	private double ritardo;
	
	public Passeggero(int id) {
		this.id = id;
		this.voli = new ArrayList<>();		
	}
	public void aggiungiVolo(Flight volo) {
		this.voli.add(volo);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the voli
	 */
	public List<Flight> getVoli() {
		return voli;
	}
	/**
	 * @param voli the voli to set
	 */
	public void setVoli(List<Flight> voli) {
		this.voli = voli;
	}
	/**
	 * @return the ritardo
	 */
	public double getRitardo() {
		double tot = 0.0;
		for(Flight f : this.voli) {
			tot+=f.getArrivalDelay();
		}
		return tot;
	}
	/**
	 * @param ritardo the ritardo to set
	 */
	public void setRitardo(double ritardo) {
		this.ritardo = ritardo;
	}
	public int numeroVoli() {
		// TODO Auto-generated method stub
		return this.voli.size();
	}
	@Override
	public String toString() {
		return "Passeggero: "+ id + " ritardo=" + this.getRitardo() ;
	}

}
