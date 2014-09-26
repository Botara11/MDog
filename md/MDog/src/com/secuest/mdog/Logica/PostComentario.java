package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.GregorianCalendar;


public class PostComentario implements Serializable{
	private Dueno dueno;
	private String comentario;
	private GregorianCalendar date;
	
	
	public PostComentario(Dueno dueno, String comentario,
			GregorianCalendar date) {
		super();
		this.dueno = dueno;
		this.comentario = comentario;
		this.date = date;
	}


	public Dueno getDueno() {
		return dueno;
	}


	public void setDueno(Dueno dueno) {
		this.dueno = dueno;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public GregorianCalendar getDate() {
		return date;
	}


	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
}