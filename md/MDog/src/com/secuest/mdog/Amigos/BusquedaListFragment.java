package com.secuest.mdog.Amigos;
//ListFragment de Amig
import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Perfiles.PerfilDueno;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;




public class BusquedaListFragment extends ListFragment implements
LoaderCallbacks<Void> {

	private static final String TAG = "FragmentTabs";

	private String mTag;
	private Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;
	private ArrayList<Dueno> amigos;

	private static final String[] WORDS = { "Lorem"};
	private static final String[] NUMBERS = { "I"};

	private static final int SLEEP = 1000;

	private final int wordBarColor = R.color.word_bar;
	private final int numberBarColor = R.color.number_bar;

	public BusquedaListFragment(String tag, ArrayList<Dueno> amigos) {
		mTag = tag;
		mTotal = Amigos.TAB_AMIGOS.equals(mTag) ? WORDS.length
				: NUMBERS.length;
		this.amigos=amigos;
		Log.d(TAG, "Constructor: tag=" + tag);
	}

	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);

		mInflater = LayoutInflater.from(getActivity());
		ListView ListViewBuscarAmigos = (ListView) getListView().findViewById(R.id.ListViewBuscarAmigos);
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		for(int i=0;i<amigos.size();i++)
			datos.add(new Lista_entrada(amigos.get(i).getImage(), 1, new String[]{ amigos.get(i).getNick()}));
		
		
		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(getActivity(), R.layout.list_item_amigos, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textcentrado); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 
						  
						/*TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); */

			        	String nom = ((Lista_entrada) entrada).getNombreImage();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						if (imagen_entrada != null)
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_persona);
								e.printStackTrace();
							}
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
		Intent i =new Intent(getActivity(), PerfilDueno.class);
		i.putExtra("Dueno",amigos.get(position));
		startActivity(i);
		
	}






	@Override
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



	@Override
	public void onLoadFinished(Loader<Void> loader, Void result) {

		// add the new item and let the adapter know in order to refresh the
		// views
		mItems.add(Amigos.TAB_AMIGOS.equals(mTag) ? WORDS[mPosition]
				: NUMBERS[mPosition]);
		mAdapter.notifyDataSetChanged();

		// advance in your list with one step
		mPosition++;
		if (mPosition < mTotal - 1) {
			getLoaderManager().restartLoader(0, null, this);
			Log.d(TAG, "onLoadFinished(): loading next...");
		} else {
			Log.d(TAG, "onLoadFinished(): done loading!");
		}
	}

	@Override
	public void onLoaderReset(Loader<Void> loader) {
	}
}

