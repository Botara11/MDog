package com.secuest.mdog.Citas;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.MiCuenta.MiCuenta;
import com.secuest.mdog.Perfiles.PerfilPerro;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ListView;

public class Lista_perros_disp extends ListFragment {
	
	private static final String TAG = "FragmentTabs";

	private ArrayList<Perro> misPerros;

	private String mTag;
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;


	private static final String[] WORDS = { "Lorem"};
	private static final String[] NUMBERS = { "I"};

	private static final int SLEEP = 1000;

	private final int wordBarColor = R.color.word_bar;
	private final int numberBarColor = R.color.number_bar;

	public Lista_perros_disp() {
	}

	public Lista_perros_disp(String tag,ArrayList<Perro> misPerros) {
		mTag = tag;
		mTotal = MiCuenta.TAB_WORDS.equals(mTag) ? WORDS.length
				: NUMBERS.length;
		this.misPerros = misPerros;
		Log.d(TAG, "Constructor: tag=" + tag);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);

        
        
		mInflater = LayoutInflater.from(getActivity());
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		for(int i=0;i<misPerros.size();i++)
			datos.add(new Lista_entrada(misPerros.get(i).getImage(), 2, new String[]{ misPerros.get(i).getNombre(), misPerros.get(i).getRaza().toString()}));
		
		
		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
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

						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						if (imagen_entrada != null)
							imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
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
		mItems.add(MiCuenta.TAB_WORDS.equals(mTag) ? WORDS[mPosition]
				: NUMBERS[mPosition]);
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
