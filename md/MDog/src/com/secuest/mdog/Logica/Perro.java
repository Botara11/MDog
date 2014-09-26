package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Perro implements Serializable{

	
	private String image;
	private String nombre;
	private int edad;
	private Raza raza;
	private String aficiones;
	private String personalidad;
	private int adorabilidad;
	private boolean adopcion;
	// 0 y1 vacunas 2 antiparasitaria
	private GregorianCalendar[] fechas;
	private String lastVacuna;
	private String nextVacuna;
	private String numChip;
	private double distancia;
	private String genero;



	public Perro(String nombre, String genero, String image, int edad, Raza raza, 
			String aficiones, String personalidad, GregorianCalendar[] fechas, String lastVacuna,
			String nextVacuna, int adorabilidad, boolean adopcion, 
			String numChip, double distancia) {
		super();
		this.nombre = nombre;
		this.genero = genero;
		this.image = image;
		this.edad = edad;
		this.raza = raza;
		this.aficiones = aficiones;
		this.personalidad = personalidad;
		this.fechas = fechas;
		this.lastVacuna = lastVacuna;
		this.nextVacuna = nextVacuna;
		this.adorabilidad = adorabilidad;
		this.numChip = numChip;
		this.adopcion = adopcion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getImage(){
		return image;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public Raza getRaza() {
		return raza;
	}
	public void setRaza(Raza raza) {
		this.raza = raza;
	}
	public String getAficiones() {
		return aficiones;
	}
	public void setAficiones(String aficiones) {
		this.aficiones = aficiones;
	}
	public String getPersonalidad() {
		return personalidad;
	}
	public void setPersonalidad(String personalidad) {
		this.personalidad = personalidad;
	}
	public GregorianCalendar[] getFechas() {
		return fechas;
	}
	public void setFechas(GregorianCalendar[] fechas) {
		this.fechas = fechas;
	}
	public String getLastVacuna() {
		return lastVacuna;
	}
	public void setLastVacuna(String lastVacuna) {
		this.lastVacuna = lastVacuna;
	}
	public String getNextVacuna() {
		return nextVacuna;
	}
	public void setNextVacuna(String nextVacuna) {
		this.nextVacuna = nextVacuna;
	}
	public void setAdorabilidad(int adorabilidad) {
		this.adorabilidad = adorabilidad;
	}
	public void setAdopcion(boolean adopcion) {
		this.adopcion = adopcion;
	}


	public String getNumChip() {
		return numChip;
	}


	public void setNumChip(String numChip) {
		this.numChip = numChip;
	}


	public int getAdorabilidad() {
		return adorabilidad;
	}


	public boolean isAdopcion() {
		return adopcion;
	}


	public double getDistancia() {
		return distancia;
	}


	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

}
