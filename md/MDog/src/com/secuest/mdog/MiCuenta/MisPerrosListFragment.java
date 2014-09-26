package com.secuest.mdog.MiCuenta;
//ListFragme

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.secuest.mdog.ActivityCam;
import com.secuest.mdog.Portada;
import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;




public class MisPerrosListFragment extends ListFragment {

	private Lista_adaptador mAdapter;
	private ArrayList<Perro> misPerros;
	private ArrayList<Lista_entrada> datos;
	private static final int MODIFICAR_PERRO = 7;


	public MisPerrosListFragment(String tag,ArrayList<Perro> misPerros) {

		this.misPerros = misPerros;
	}

	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		datos = new ArrayList<Lista_entrada>();
		
		
		for(int i=0;i<misPerros.size();i++)
			datos.add(new Lista_entrada(misPerros.get(i).getImage(), 2, new String[]{ misPerros.get(i).getNombre(), misPerros.get(i).getRaza().toString()}));
		
		
		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mAdapter = new Lista_adaptador(getActivity(), R.layout.list_item_misperros, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textsuperior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 
						  
						TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_texto(1)); 
						
						String nom = ((Lista_entrada) entrada).getNombreImage();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						if (imagen_entrada != null && !nom.equals("") )
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
							}
						
						ImageView imagenEliminar = (ImageView) view.findViewById(R.id.imageEliminar);
						imagenEliminar.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								System.out.println("Clickado");								
							}
						});
					}
				}
			};

		}
		getListView().setAdapter(mAdapter);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i =new Intent(getActivity(), PerfilPerroPrivado.class);
		i.putExtra("Perro",misPerros.get(position));
		i.putExtra("Posicion", position);
		getActivity().startActivityForResult(i, MODIFICAR_PERRO);
	}
	
	
	
	
	
	public void mostrarEliminar(){
		for(int i=0;i<getListView().getChildCount();i++){
			ImageView img = (ImageView)((View)getListView().getChildAt(i)).findViewById(R.id.imageEliminar);
			img.setVisibility(View.VISIBLE);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				System.out.println("Clcik");
				
				
					
				}
			});
		}
	}

	
	public void esconderEliminar(){
		for(int i=0;i<getListView().getChildCount();i++){
			ImageView img = (ImageView)((View)getListView().getChildAt(i)).findViewById(R.id.imageEliminar);
			img.setVisibility(View.INVISIBLE);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				System.out.println("Clcik");
				
				
					
				}
			});
		}
	}
	
	
	public void anadirPerro(Perro p){
		misPerros.add(p);
		datos.add(new Lista_entrada(p.getImage(), 2, new String[]{ p.getNombre(), p.getRaza().toString()}));
		getListView().setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}


	public void modificarPerro(Perro p, int pos) {
		misPerros.set(pos, p);
		datos.set(pos, (new Lista_entrada(p.getImage(), 2, new String[]{ p.getNombre(), p.getRaza().toString()})));
		getListView().setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	
}
