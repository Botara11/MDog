package com.secuest.mdog.Mapa;
import com.secuest.mdog.*;
import com.secuest.mdog.MiCuenta.MiCuenta;
import com.secuest.mdog.Perfiles.PerfilDueno;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ListView;

public class SelectMapa extends ListFragment {
	
	private static final String TAG = "FragmentTabs";

	public static final String ARG_PLANET_NUMBER = "planet_number";

	private String mTag;
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private int mTotal;
	private int mPosition;

	private static final int SLEEP = 1000;


	public SelectMapa() {
	}

	public SelectMapa(String tag) {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);

		int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.planets_array)[i];
        getActivity().setTitle(planet);
        
        
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		datos.add(new Lista_entrada(R.drawable.marker_violeta,1,new String[]{ "Parques"}));
		datos.add(new Lista_entrada(R.drawable.marker_violeta,1,new String[]{ "Protectoras"}));
		datos.add(new Lista_entrada(R.drawable.marker_violeta,1,new String[]{ "Veterinarias"}));
		datos.add(new Lista_entrada(R.drawable.marker_violeta,1,new String[]{ "Tiendas de perros"}));
		datos.add(new Lista_entrada(R.drawable.marker_violeta,1,new String[]{ "Peluquerias caninas"}));
		
		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(getActivity(), R.layout.list_item_coniconos, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textsuperior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 
						  
						TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_texto(1)); 

						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						if (imagen_entrada != null)
							imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
					}
				}
			};

		}
		getListView().setAdapter(mAdapter);
		setListAdapter(mAdapter);

		// initiate the loader to do the background work

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		startActivity(new Intent(getActivity(), Mapa.class));

	}


	public Loader<Void> onCreateLoader(int id, Bundle args) {
		AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

			@Override
			public Void loadInBackground() {
				try {
					// simulate some time consuming operation going on in the
					// background
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		// somehow the AsyncTaskLoader doesn't want to start its job without
		// calling this method
		loader.forceLoad();
		return loader;
	}



	public void onLoadFinished(Loader<Void> loader, Void result) {

		// add the new item and let the adapter know in order to refresh the
		// views
		mAdapter.notifyDataSetChanged();

		// advance in your list with one step
		mPosition++;
		if (mPosition < mTotal - 1) {
			//getLoaderManager().restartLoader(0, null, this);
			Log.d(TAG, "onLoadFinished(): loading next...");
		} else {
			Log.d(TAG, "onLoadFinished(): done loading!");
		}
	}

	public void onLoaderReset(Loader<Void> loader) {
	}
}
