package com.secuest.mdog.Muro;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.Logica.Post;
import com.secuest.mdog.Logica.PostComentario;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;
import com.secuest.mdog.utils.ZoomImagen;

public class Muro extends ListFragment {

	public static final String ARG_PLANET_NUMBER = "planet_number";
	protected static final int COMENTARIO_MURO = 5;
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	public Muro() {
	}

	public Muro(String tag) {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		Test t = new Test(getActivity().getResources(),getActivity().getCacheDir());
		t.Load(); 
		Post post = t.post; 

		final ArrayList<Post> arrayPost = new ArrayList<Post>();
		Post nuevo = new Post("", t.crysti, "Me caguen mi vida", new GregorianCalendar(2013,10,21,22,32), "101", 9, (new ArrayList<PostComentario>()));
		arrayPost.add(post);
		arrayPost.add(nuevo);
		arrayPost.add(post);
		
		//getIntent().getSerializableExtra("MyClass");
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		for(int i=0;i<arrayPost.size();i++)
			datos.add(new Lista_entrada(arrayPost.get(i).getDueno().getImage(),arrayPost.get(i).getImage(),6,new String[]{ arrayPost.get(i).getDueno().getNick(), calcularTiempo(arrayPost.get(i).getDate()), arrayPost.get(i).getComentario(), arrayPost.get(i).getAdorables()+"", arrayPost.get(i).getComentarios().size()+"",i+""}));

		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(getActivity(), R.layout.muro_item, datos){
				@Override
				public void onEntrada(final Object entrada, View view) {
					if (entrada != null) {

						TextView nombre = (TextView) view.findViewById(R.id.nombre); 
						if (nombre != null) 
							nombre.setText(((Lista_entrada) entrada).get_texto(0));


						TextView tiempo = (TextView) view.findViewById(R.id.tiempo); 
						if (tiempo != null) 
							tiempo.setText(((Lista_entrada) entrada).get_texto(1));

						TextView comentario = (TextView) view.findViewById(R.id.comentario); 
						if (comentario != null) 
							comentario.setText(((Lista_entrada) entrada).get_texto(2));

						TextView numAdorables = (TextView) view.findViewById(R.id.numAdorables); 
						if (numAdorables != null) 
							numAdorables.setText(((Lista_entrada) entrada).get_texto(3)); 


						TextView numMensajes = (TextView) view.findViewById(R.id.numMensajes); 
						if (numMensajes != null) 
							numMensajes.setText(((Lista_entrada) entrada).get_texto(4)); 

						String nom = ((Lista_entrada) entrada).getNombreImage();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						
						imagen_entrada.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent u = new Intent(getActivity(),ZoomImagen.class);
								u.putExtra("id", R.drawable.sombra_persona);
								getActivity().startActivity(u);
							}});
						
						if (imagen_entrada != null)
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
								e.printStackTrace();
							}
						
						

						String nom2 = ((Lista_entrada) entrada).getimagenPost();
						com.secuest.mdog.utils.CustomImageView img = (com.secuest.mdog.utils.CustomImageView) view.findViewById(R.id.image2);
						if (img != null)
							try {
								img.setImageBitmap((new Cache()).cargarImagen(nom2, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
								e.printStackTrace();
							}


						Button botonComent = (Button) view.findViewById(R.id.comment_img);
						botonComent.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								int posicion = Integer.parseInt(((Lista_entrada) entrada).get_texto(5));
								if(arrayPost.get(posicion).getComentarios().size()!=0){
									Intent i = new Intent(getActivity(),VerComentarios.class);
									i.putExtra("Post", arrayPost.get(posicion));
									getActivity().startActivityForResult(i, COMENTARIO_MURO);
								}else{
									Toast.makeText(getActivity(), "No existen comentarios", Toast.LENGTH_LONG).show();
								}
									
							}
						});
					}
				}
			};

		}
		getListView().setDividerHeight(0);
		getListView().setAdapter(mAdapter);
		setListAdapter(mAdapter);

		// initiate the loader to do the background work

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
	}

	private String calcularTiempo(GregorianCalendar date){

		int min = (int)(date.getTimeInMillis()/1000)/60;
		Calendar c = Calendar.getInstance(); 
		int actual =(int) (c.getTime().getTime()/1000)/60;

		if(actual-min<60)
			return ("Hace "+(actual-min)+" minutos"); 
		else if ((actual-min)<1440)
			return ("Hace "+(int)((actual-min)/60)+" horas");
		else if ((actual-min)<525600)
			return ("Hace "+(int)((actual-min)/1440)+" dias");
		else 
			return ("Hace "+(int)((actual-min)/525600)+" a�os");



		//	System.out.println("Tiempos: "+actual+ " "+ min);
		//	System.out.println("Actual:   "+c.get(Calendar.YEAR)+":"+c.get(Calendar.MONTH)+":"+c.get(Calendar.DAY_OF_MONTH)+"  -  "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":");
		//	System.out.println("Otro:   "+date.get(Calendar.YEAR)+":"+date.get(Calendar.MONTH)+":"+date.get(Calendar.DAY_OF_MONTH)+"  -  "+c.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+":");

	}


}