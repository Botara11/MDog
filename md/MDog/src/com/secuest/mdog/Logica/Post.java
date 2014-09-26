package com.secuest.mdog.Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;

public class Post implements Serializable{
	private String postid;
	private Dueno dueno;
	private String comentario;
	private GregorianCalendar date;
	private String image;
	private int adorables;
	private ArrayList<PostComentario> comentarios;
	
	
	
	public Post(String postid, Dueno dueno, String comentario, GregorianCalendar date, String image,
			int adorables, ArrayList<PostComentario> comentarios) {
		super();
		this.postid = postid;
		this.dueno = dueno;
		this.comentario = comentario;
		this.date = date;
		this.image = image;
		this.adorables = adorables;
		this.comentarios = comentarios;
	}
	
	
	


	public String getPostid() {
		return postid;
	}




	public void setPostid(String postid) {
		this.postid = postid;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getAdorables() {
		return adorables;
	}
	public void setAdorables(int adorables) {
		this.adorables = adorables;
	}
	public ArrayList<PostComentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(ArrayList<PostComentario> comentarios) {
		this.comentarios = comentarios;
	}

}