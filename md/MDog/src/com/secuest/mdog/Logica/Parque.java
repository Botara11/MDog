package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class Parque implements Serializable {
	
	private String nombre;
	private String ciudad;
	private Double lat;
	private Double lon;
	private String image;
	
	private String direccion;
	
	
	
	public Parque(String nombre, String ciudad, String image, String direccion,  Double lat, Double lon) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.lat = lat;
		this.lon = lon;
		this.image=image;
		this.direccion=direccion;
	}
	
	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
	
}
