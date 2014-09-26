package com.secuest.mdog.utils;

public class Lista_entrada {
	private int idImagen; 
	private String [] array; 
	private String nombreImage;
	private String imagenPost;
	  
	public Lista_entrada (int idImagen, int tam, String [] array) { 
	    this.idImagen = idImagen; 
	    this.array = array; 
	}
	
	public Lista_entrada (String nombreImage, int tam, String [] array) { 
	    this.nombreImage = nombreImage; 
	    this.array = array; 
	}
	public Lista_entrada (String nombreImage, String imagenPost, int tam, String [] array) { 
	    this.nombreImage = nombreImage;
		this.imagenPost = imagenPost; 
	    this.array = array; 
	}
	
	
	public String getNombreImage(){
		return nombreImage;
	}
	
	public String getimagenPost(){
		return imagenPost;
	}
	
	public String get_texto(int pos) { 
	    return array[pos]; 
	}
	
	public int get_idImagen() {
	    return idImagen; 
	} 
}
