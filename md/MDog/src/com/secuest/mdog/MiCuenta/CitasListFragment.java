package com.secuest.mdog.MiCuenta;

//ListFragme
import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Cita;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Perfiles.PerfilPerro;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
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
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;




public class CitasListFragment extends ListFragment implements
LoaderCallbacks<Void> {

	private static final String TAG = "FragmentTabs";

	private String mTag;
	private List_adapt_2layout mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;


	private static final String[] WORDS = { "Lorem"};
	private static final String[] NUMBERS = { "I"};

	private static final int SLEEP = 1000;

	private final int wordBarColor = R.color.word_bar;
	private final int numberBarColor = R.color.number_bar;

	private ArrayList<Cita> misCitas;

	public CitasListFragment(String tag, ArrayList<Cita> misCitas) {
		mTag = tag;
		mTotal = MiCuenta.TAB_WORDS.equals(mTag) ? WORDS.length
				: NUMBERS.length;
		this.misCitas = misCitas;
		Log.d(TAG, "Constructor: tag=" + tag);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);

		mInflater = LayoutInflater.from(getActivity());
		ArrayList<List_item> datos = new ArrayList<List_item>();  


		for(int i=0;i<misCitas.size();i++)
			datos.add(new List_item(misCitas.get(i).getMiperro().getImage(),misCitas.get(i).getPerroCita().getImage(), 2, new String[]{ misCitas.get(i).getMiperro().getNombre(), misCitas.get(i).getPerroCita().getNombre()},misCitas.get(i).getRequest()));


		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new List_adapt_2layout(getActivity(), R.layout.list_item_citas, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						
						if(!(((List_item) entrada).getRequest())){
							LinearLayout root = (LinearLayout) view.findViewById(R.id.root);
							root.removeView(view.findViewById(R.id.layout_request));
						}

						TextView NombreMiPerro = (TextView) view.findViewById(R.id.nombreMiPerro); 
						if (NombreMiPerro != null) 
							NombreMiPerro.setText(((List_item) entrada).get_texto(0)); 

						TextView NombrePerroCita = (TextView) view.findViewById(R.id.nombrePerroCita); 
						if (NombrePerroCita != null)
							NombrePerroCita.setText(((List_item) entrada).get_texto(1)); 

						String nomMiPerro = ((List_item) entrada).getImageMiPerro();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imgMiPerro); 
						if (imagen_entrada != null)
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nomMiPerro, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
								e.printStackTrace();
							}

						String nomPerroCita = ((List_item) entrada).getImagePerroCita();
						imagen_entrada = (ImageView) view.findViewById(R.id.imgPerroCita); 
						if (imagen_entrada != null)
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nomPerroCita, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
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

		Intent i =new Intent(getActivity(), PerfilPerro.class);
		i.putExtra("Perro",misCitas.get(position));
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
		mItems.add(MiCuenta.TAB_WORDS.equals(mTag) ? WORDS[mPosition]
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
