package com.secuest.mdog.Logica;

import java.io.Serializable;

public class Cita implements Serializable{
	private Perro miperro;
	private Perro perroCita;
	private boolean request;
	
	
	public Cita(Perro miperro, Perro perroCita, boolean request) {
		super();
		this.miperro = miperro;
		this.perroCita = perroCita;
		this.request = request;
	}
	
	
	
	public Perro getMiperro() {
		return miperro;
	}
	public void setMiperro(Perro miperro) {
		this.miperro = miperro;
	}
	public Perro getPerroCita() {
		return perroCita;
	}
	public void setPerroCita(Perro perroCita) {
		this.perroCita = perroCita;
	}
	public boolean getRequest() {
		return request;
	}
	public void setRequest(boolean request) {
		this.request = request;
	}




}