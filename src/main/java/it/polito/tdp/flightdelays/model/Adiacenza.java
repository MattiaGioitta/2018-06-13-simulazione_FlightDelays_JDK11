package it.polito.tdp.flightdelays.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Airport a1;
	private Airport a2;
	private boolean buono;
	private double peso;
	/**
	 * @param a1
	 * @param a2
	 * @param peso
	 */
	public Adiacenza(Airport a1, Airport a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = calcolaPeso(peso);
	}
	public Adiacenza(Airport a1, Airport a2, double peso,boolean buono) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
		this.buono = buono;
	}
	private double calcolaPeso(double peso2) {
		LatLng primo = new LatLng(a1.getLatitude(), a1.getLongitude());
		LatLng secondo = new LatLng(a2.getLatitude(),a2.getLongitude());
		double distanza = LatLngTool.distance(primo,secondo,LengthUnit.MILE);
		return peso2/distanza;
	}
	/**
	 * @return the a1
	 */
	public Airport getA1() {
		return a1;
	}
	/**
	 * @param a1 the a1 to set
	 */
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	/**
	 * @return the a2
	 */
	public Airport getA2() {
		return a2;
	}
	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return Double.compare(o.getPeso(), peso);
	}
	@Override
	public String toString() {
		return  a1.getName()+" "+ a2.getName() + " " + peso;
	}
	
	

}
