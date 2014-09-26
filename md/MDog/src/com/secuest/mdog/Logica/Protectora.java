package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class Protectora implements Serializable{

	private String nombre;
	private String direccion;
	private String ciudad;
	private String image;
	private double lat;
	private double lng;
	private int telefono;
	private String paginaWeb;
	private ArrayList<PerroProtect> perros;
	private String email;
	
	
	

	public Protectora(String nombre,String direccion, String image, 
			String ciudad, double lat, double lng, int telefono,String email, 
			String paginaWeb, ArrayList<PerroProtect> perros) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.image = image;
		this.ciudad = ciudad;
		this.lat = lat;
		this.lng = lng;
		this.telefono = telefono;
		this.paginaWeb = paginaWeb;
		this.perros = perros;
		this.email = email;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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
	
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getPaginaWeb() {
		return paginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	public ArrayList<PerroProtect> getPerros() {
		return perros;
	}
	public void setPerros(ArrayList<PerroProtect> perros) {
		this.perros = perros;
	}



}
