package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Dueno implements Serializable{


	private String image;
	private String nick;
	private String ciudad;
	private ArrayList <Perro> perros;
	private ArrayList<Parque> parques;
	private ArrayList<Dueno> amigos;
	private ArrayList<Cita> citas;


	public Dueno(String id, String image, String nick, String ciudad,
			ArrayList<Perro> perros, ArrayList<Parque> parques,
			ArrayList<Cita> citas, ArrayList<Dueno> amigos) {
		super();
		this.id = id;
		this.image = image;
		this.nick = nick;
		this.ciudad = ciudad;
		this.perros = perros;
		this.parques = parques;
		this.citas = citas;
		this.amigos = amigos;
	}



	public ArrayList<Dueno> getAmigos() {
		return amigos;
	}

	public void setAmigos(ArrayList<Dueno> amigos) {
		this.amigos = amigos;
	}


	private String id;
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public ArrayList<Perro> getPerros() {
		return perros;
	}
	public void setPerros(ArrayList<Perro> perros) {
		this.perros = perros;
	}
	public ArrayList<Parque> getParques() {
		return parques;
	}
	public void setParques(ArrayList<Parque> parques) {
		this.parques = parques;
	}
	public ArrayList<Cita> getCitas() {
		return citas;
	}
	public void setCitas(ArrayList<Cita> citas) {
		this.citas = citas;
	}

}
