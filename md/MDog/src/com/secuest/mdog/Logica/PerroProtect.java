package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class PerroProtect implements Serializable{

	private String image;
	private String nombre;
	private int edad;
	private Raza raza;
	private String aficiones;
	private String personalidad;
	private Object peso;
	private int tipoEdad;
	private ArrayList<String> fotos;
	

	
	



	public PerroProtect(String nombre, String image, int edad, int tipoEdad, int peso, Raza raza, String aficiones,
			String personalidad, ArrayList<String> fotos) {
		super();
		this.nombre = nombre;
		this.image = image;
		this.edad = edad;
		this.tipoEdad = tipoEdad;
		this.peso = peso;
		this.raza = raza;
		this.aficiones = aficiones;
		this.personalidad = personalidad;
		this.fotos = fotos;
		
	}
	
	
	public Object getPeso() {
		return peso;
	}


	public void setPeso(Object peso) {
		this.peso = peso;
	}


	public int getTipoEdad() {
		return tipoEdad;
	}


	public void setTipoEdad(int tipoEdad) {
		this.tipoEdad = tipoEdad;
	}


	public ArrayList<String> getFotos() {
		return fotos;
	}


	public void setFotos(ArrayList<String> fotos) {
		this.fotos = fotos;
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
	
	
	
	
}
