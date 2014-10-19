package com.secuest.mdog.utils;

import java.util.ArrayList;

public class Lista_entrada {
	private int idImagen; 
	private String [] array; 
	private String nombreImage;
	private String imagenPost;
	private String array1;
	private ArrayList<Integer> array_image;
	private String[] array2;
	  
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
	public Lista_entrada (int idImagen, int tam, String array1) { 
	    this.idImagen = idImagen; 
	    this.array1 = array1; 
	}
	
	public Lista_entrada (String nombreImage, int tam, String array1) { 
	    this.nombreImage = nombreImage; 
	    this.array1 = array1; 
	}
	public Lista_entrada (String nombreImage, String imagenPost, int tam, String array1) { 
	    this.nombreImage = nombreImage;
		this.imagenPost = imagenPost;
		this.array1 = array1; 
	}
	
	
	public Lista_entrada(ArrayList<Integer> array_image, int tam,
			String[] array2) {
		this.array_image=array_image;
		this.array2=array2;
		// TODO Auto-generated constructor stub
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
