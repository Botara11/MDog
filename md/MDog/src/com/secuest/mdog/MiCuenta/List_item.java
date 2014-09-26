package com.secuest.mdog.MiCuenta;



public class List_item {
	private String idImagenMiperro;
	private String idImagenPerroCita;
	private String [] array; 
	private boolean request;
	
	
	
	public List_item(String idImagenMiperro, String idImagenPerroCita, int i, String[] array, boolean request) {
		this.idImagenMiperro = idImagenMiperro; 
		this.idImagenPerroCita = idImagenPerroCita; 
	    this.array = array; 
	    this.request=request;
	    }
	
	public boolean getRequest(){
		return request;
	}
	
	public String getImageMiPerro(){
		return idImagenMiperro;
	}
	
	public String getImagePerroCita(){
		return idImagenPerroCita;
	}
	public String get_texto(int pos) { 
	    return array[pos]; 
	}
	
}
