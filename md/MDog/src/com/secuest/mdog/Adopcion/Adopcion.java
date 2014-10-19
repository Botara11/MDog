package com.secuest.mdog.Adopcion;

import com.secuest.mdog.Citas.Lista_perros_disp;

import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.BuscarPerrosCerca.BusqRazas;
import com.secuest.mdog.Logica.Perro;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adopcion extends Fragment {

	public static final String ARG_PLANET_NUMBER = "planet_number";
	public static final String TAB_WORDS = "Mis perros";
	
	public FragmentManager fm;
	public android.support.v4.app.FragmentTransaction ft;

	private View rootView;
	private AsyncCaller async;
	private Spinner genero;
	private Button boton;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}


	public Adopcion(){   	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.citas_layout, null);
		int i = getArguments().getInt(ARG_PLANET_NUMBER);
		String planet = getResources().getStringArray(R.array.planets_array)[i];

		getActivity().setTitle(planet);
		fm = getFragmentManager();
		async = new AsyncCaller();

		genero = (Spinner) rootView.findViewById(R.id.genero);
		
		boton= (Button) rootView.findViewById(R.id.raza);
		boton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity( new Intent(getActivity(), BusqRazas.class));
			}
		});
		//raza = (Spinner) rootView.findViewById(R.id.raza);
		//ciudad = (Spinner) rootView.findViewById(R.id.ciudad);

		//GENERO
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("Macho");
		lista.add("Hembra");
		lista.add("Indistinto");
		lista.add("Genero");
		ArrayAdapterMio<String> adaptador = new ArrayAdapterMio<String>(getActivity(), 
				android.R.layout.simple_spinner_item, lista);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genero.setAdapter(adaptador);
		genero.setSelection(adaptador.getCount());

		//RAZA
		/*lista = new ArrayList<String>();
		lista.add("Teckel");
		lista.add("Westi");
		lista.add("Cooker");
		lista.add("Yorkshire");
		lista.add("Beagle");
		lista.add("Raza");
		adaptador = new ArrayAdapterMio<String>(getActivity(), 
				android.R.layout.simple_spinner_item, lista);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		raza.setAdapter(adaptador);
		raza.setSelection(adaptador.getCount());*/

		/*//CIUDAD
		lista = new ArrayList<String>();
		lista.add("Valencia");
		lista.add("Madrid");
		lista.add("Barcelona");
		lista.add("Ciudad");
		adaptador = new ArrayAdapterMio<String>(getActivity(), 
				android.R.layout.simple_spinner_item, lista);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ciudad.setAdapter(adaptador);
		ciudad.setSelection(adaptador.getCount());
		*/

		//RESETEO DE LOS FRAGMENT
		/*String [] iter = new String[] {TAB_CITAS,TAB_MURO,TAB_NUMBERS,TAB_PERFIL,TAB_WORDS};
		for( i=0;i<5;i++)
			if(fm.findFragmentByTag(iter[i])!=null){
				System.out.println("FRAGMENT encontrado");
				fm.beginTransaction().remove(fm.findFragmentByTag(iter[i])).commit();
				fm.popBackStack();
			}
		 */

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		// manually start loading stuff in the first tab

		async.updateTab(TAB_WORDS, R.id.lista_perros_disp);
	}



	private class AsyncCaller extends AsyncTask<Void, Void, Void>
	{

		private void updateTab(String tabId, int placeholder) {
			doInBackground();

		}



		@Override
		protected Void doInBackground(Void... params) {
			Test t = new Test(getActivity().getResources(),getActivity().getCacheDir());
			t.Load();
			ArrayList<Perro> arrayPerroCita =t.kate.getPerros();

			//System.out.println("FRAGMENT replace citas");
			fm.beginTransaction()
			.replace(R.id.lista_perros_disp, new Lista_perros_disp(TAB_WORDS,arrayPerroCita), TAB_WORDS)
			.commit();
			return null;

		}

	}


	private class ArrayAdapterMio<String> extends ArrayAdapter<String> {

		public ArrayAdapterMio(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);

		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			if (position == getCount()) {
				((TextView)v.findViewById(android.R.id.text1)).setText("");
				((TextView)v.findViewById(android.R.id.text1)).setHint((CharSequence) getItem(getCount())); //"Hint to be displayed"
			}
			return v;
		}       
		@Override
		public int getCount() {
			return super.getCount()-1; // you dont display last item. It is used as hint.
		} 
	}

}

