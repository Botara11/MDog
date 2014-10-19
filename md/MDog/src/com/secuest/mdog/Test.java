package com.secuest.mdog;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.android.gms.maps.model.LatLng;
import com.secuest.mdog.Logica.Cita;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Parque;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.PerroProtect;
import com.secuest.mdog.Logica.Post;
import com.secuest.mdog.Logica.PostComentario;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.Protectoras.Protectoras;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class Test extends Activity {

	public Parque parque;
	public Dueno kate;
	public Dueno crysti;
	public Dueno Krusty;
	public Perro defecto;
	public Protectora lacanera;
	public Protectora elpuig;
	public Post post;

	private Resources resources;
	private File cacheDir;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}


	public Test(Resources resources,File cacheDir) {
		this.resources=resources;
		this.cacheDir = cacheDir;
	}

	public void Load() {

		//El nombre de la imagen del perro consta de (IDdue�o + n�perro)

		 
		Perro defecto = new Perro("Nombre","macho", "100", 2, Raza.Akita_Inu, "Escribe sobre sus aficiones", "Escribe sobre su personalidad", new GregorianCalendar[]{new GregorianCalendar(2014, 4, 20),new GregorianCalendar(2014, 9, 10),new GregorianCalendar(2014, 9, 10)}, "", "", 5, true,"941000011187155",0.0);
		
		Parque parque = new Parque("Parque del oeste", "Valencia","parq_oeste","Carrer d'Enguera, s/n, 46018 Valencia", 40.430538, -3.725960);
		ArrayList<Parque> arrayParque = new ArrayList<Parque>(); arrayParque.add(parque);

		Perro odie = new Perro("Rambo","macho", "100", 2, Raza.Akita_Inu, "Morder a la gente", "Cari�osito", new GregorianCalendar[]{new GregorianCalendar(2014, 4, 20),new GregorianCalendar(2014, 9, 10),new GregorianCalendar(2014, 11, 6)}, "Paridiocardialisis", "Pal cor", 5, false,"941000011187155",0.0);
		Perro duque = new Perro("Sanson","macho", "101", 5, Raza.Yorkshire_Terrier, "Comer a todas horas", "Comilon", new GregorianCalendar[]{new GregorianCalendar(2014, 4, 20),new GregorianCalendar(2014, 9, 10),new GregorianCalendar(2014, 11, 6)}, "Paridiocardialisis", "Pal cor", 10, true,"941000011187155",0.0);
		ArrayList<Perro> arrayPerroJes = new ArrayList<Perro>(); arrayPerroJes.add(odie); arrayPerroJes.add(duque);ArrayList<Dueno> amigos =new ArrayList<Dueno>();amigos.add(crysti);
		ArrayList<Cita> citas = new ArrayList<Cita>();
		kate = new Dueno("10", "10","Kate", "Valencia", arrayPerroJes, arrayParque,citas, new ArrayList<Dueno>());
		

		Perro perro = new Perro("Moncha","hembra", "110", 7, Raza.Cocker_Spaniel_Americano, "Comer patatas", "Arrancar huevos", new GregorianCalendar[]{new GregorianCalendar(2014, 9, 12),new GregorianCalendar(2014, 9, 12),new GregorianCalendar(2014, 11, 6)}, "Sida", "Lepra", 6, true,"941000011187155",0.0);
		Perro perro2 = new Perro("Popito","macho", "111", 7, Raza.Teckel, "Tomar un baño", "Carinoso", new GregorianCalendar[]{new GregorianCalendar(2014, 9, 12),new GregorianCalendar(2014, 7, 15),new GregorianCalendar(2014, 11, 6)}, "Malaria", "Ébola", 3, false,"941000011187155",0.0);
		ArrayList<Perro> arrayPerro = new ArrayList<Perro>(); arrayPerro.add(perro);arrayPerro.add(perro2); amigos =new ArrayList<Dueno>(); amigos.add(kate);
		crysti = new Dueno("11","11", "Crysti", "Valencia", arrayPerro, arrayParque, citas, amigos);amigos.add(crysti);
		Krusty = new Dueno("11","11", "Krusty", "Valencia", arrayPerro, arrayParque, citas, amigos);
		ArrayList<Dueno> arrayDueno = new ArrayList<Dueno>(); arrayDueno.add(crysti);arrayDueno.add(Krusty);

		ArrayList<Cita> arrayPerroCita = new ArrayList<Cita>();
		Cita cita = new Cita(perro, odie, true);
		Cita cita2 = new Cita(duque, perro2, false);
		arrayPerroCita.add(cita);arrayPerroCita.add(cita2);
		kate.setCitas(arrayPerroCita);
		kate.setAmigos(amigos);


		lacanera = new Protectora("La Canera","Sin direccion","lacanera" ,"Valencia", 3,3 , 0 , "adopcioneslaperrera@gmail.com","http://laperreraadopciones.blogspot.com.es/", new ArrayList<PerroProtect>());
		elpuig = new Protectora("El Puig","Sin direccion","elpuig" ,"Valencia", 3,3, 629287735 , "buzondelciudadano@elpuig.org","http://www.elpuig.es/content/perrera-municipal", new ArrayList<PerroProtect>());

		PerroProtect cuca = new PerroProtect("Cuca", "cuca0", 1,0, 12, Raza.Basset_Hound, "Es muy sociable , alegre y le encanta jugar", "Se lleva bien con otros cachorros y cachorras. Ignora a los gatos de la perrera.", new ArrayList<String>());
		ArrayList<PerroProtect> perrosProtect = new ArrayList<PerroProtect>(); perrosProtect.add(cuca); lacanera.setPerros(perrosProtect);
		
		ArrayList<PostComentario> comentarios = new ArrayList<PostComentario>(); 
		PostComentario coment = new PostComentario(kate, "que super guayy", new GregorianCalendar(2014, 7, 15, 17, 32));  comentarios.add(coment);
		post = new Post("p100",kate, "Mirar la foto de mi pequeque mujajajajaj", new GregorianCalendar  (2014, 8, 16, 23, 12), "100", 9, comentarios);
			 
		 
		guardarImagenCache(R.drawable.hola,"hola");
		guardarImagenCache(R.drawable.perro3,"100");   		//PERRO
		guardarImagenCache(R.drawable.hask,"101");			//PERRO
		guardarImagenCache(R.drawable.una, "10");			//DUENO
		guardarImagenCache(R.drawable.parq_oeste, "parq_oeste");	//PARQUE

		guardarImagenCache(R.drawable.cocker_hembra,"110");   	//PERRO
		guardarImagenCache(R.drawable.teckel,"111");			//PERRO
		guardarImagenCache(R.drawable.chica, "11");			//DUENO

		guardarImagenCache(R.drawable.elpuig, "elpuig");    //PROTECTORA
		guardarImagenCache(R.drawable.lacanera, "lacanera");  //PROTECTORA

		//guardarImagenCache(R.drawable.cuca0, "cuca0");			//PERROPROTECT
		//guardarImagenCache(R.drawable.cuca1, "cuca1");			//PERROPROTECT
		//guardarImagenCache(R.drawable.cuca2, "cuca2");			//PERROPROTECT
		//guardarImagenCache(R.drawable.cuca3, "cuca3");			//PERROPROTECT

	}





	public void guardarImagenCache(int idImagen,String nombreImagen){

		
		File f = new File(cacheDir, nombreImagen); // (IDdueno+num d perro)
		if(!f.exists()){
		Drawable d = resources.getDrawable(idImagen);
		Bitmap mutableBitmap = Bitmap.createBitmap( 500, 500, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mutableBitmap);
		d.setBounds(0, 0, 500, 500);
		d.draw(canvas);

		
			System.out.println("Direccion: "+f.getAbsolutePath());

			try {
				FileOutputStream out = new FileOutputStream(f);
				mutableBitmap.compress(Bitmap.CompressFormat.JPEG,100, out);
				out.flush();
				out.close();

			} catch (FileNotFoundException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
		}
	}

}