package com.secuest.mdog.Protectoras;
//Todos los Perr
import java.util.ArrayList;
import com.secuest.mdog.R;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

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




public class VerTodosPerrosListFragment extends ListFragment implements
LoaderCallbacks<Void> {

	private static final String TAG = "FragmentTabs";

	private String mTag;
	private Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;


	private static final String[] WORDS = { "Lorem"};
	private static final String[] NUMBERS = { "I"};

	private static final int SLEEP = 1000;

	private final int wordBarColor = R.color.word_bar;
	private final int numberBarColor = R.color.number_bar;

	public VerTodosPerrosListFragment() {
	}

	public VerTodosPerrosListFragment(String tag) {
		mTag = tag;
		mTotal = Protectoras.TAB_PROTECTORAS.equals(mTag) ? WORDS.length
				: NUMBERS.length;

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

		datos.add(new Lista_entrada(R.drawable.retiro,2,new String[]{ "Alvin Serengueti", null}));
		
		
		
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
		// do something with the data
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
		mItems.add(Protectoras.TAB_PROTECTORAS.equals(mTag) ? WORDS[mPosition]
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

