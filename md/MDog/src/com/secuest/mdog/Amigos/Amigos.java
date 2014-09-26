package com.secuest.mdog.Amigos;
//Lista  Amigos
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.MiCuenta.CitasListFragment;
import com.secuest.mdog.MiCuenta.MisPerrosListFragment;
import com.secuest.mdog.MiCuenta.ParquesListFragment;
import com.secuest.mdog.R.array;
import com.secuest.mdog.R.id;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;

import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;



public class Amigos extends Fragment implements OnTabChangeListener{
	public static final String ARG_PLANET_NUMBER = "planet_number";
	private static final String TAG = "FragmentTabs";
	public static final String TAB_AMIGOS = "Amigos";
	public static final String TAB_BUSCAR = "Buscar";

	private AsyncCaller async;
	private View rootView;
	private TabHost mTabHost;
	private int mCurrentTab;
	private FragmentManager fm;
	private ArrayList<Dueno> amigos;

	public Amigos() {} 

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.amigos_layout, container, false);
		int i = getArguments().getInt(ARG_PLANET_NUMBER);
		String planet = getResources().getStringArray(R.array.planets_array)[i];
		mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		//ListView ListViewBuscarAmigos = (ListView) rootView.findViewById(R.id.ListViewBuscarAmigos);
		
		setupTabs();

		/***************  BASE DE DATOS ***************/
		Test t = new Test(getActivity().getResources(), getActivity().getCacheDir());
		t.Load();
		 amigos = t.kate.getAmigos();//Y aqui habria que buscar a los due�os por id
		
		/***************  BASE DE DATOS ***************/
		
		
		getActivity().setTitle(planet);
		fm = getFragmentManager(); 
		async = new AsyncCaller();

		//RESETEO DE LOS FRAGMENT
		String [] iter = new String[] {TAB_AMIGOS,TAB_BUSCAR};
		for( i=0;i<2;i++)
			if(fm.findFragmentByTag(iter[i])!=null){
				System.out.println("FRAGMENT encontrado");
				fm.beginTransaction().remove(fm.findFragmentByTag(iter[i])).commit();
				fm.popBackStack();
			}

		return rootView;
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		//updateTab(TAB_AMIGOS, R.id.tabamigos);
		fm.beginTransaction()
		.replace(R.id.tabamigos, new AmigosListFragment(TAB_AMIGOS, amigos), TAB_AMIGOS)
		.commit();

	}

	private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_AMIGOS, R.string.tab_amigos, R.id.tabamigos));
		mTabHost.addTab(newTab(TAB_BUSCAR, R.string.tab_buscar, R.id.tabbuscar));
		
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		Log.d(TAG, "buildTab(): tag=" + tag);

		View indicator = LayoutInflater.from(getActivity()).inflate(R.layout.tab,null);
		((TextView) indicator.findViewById(R.id.text)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_AMIGOS.equals(tabId)) {
			async.updateTab(tabId, R.id.tabamigos);
			mCurrentTab = 0;
			return;
		}
		if (TAB_BUSCAR.equals(tabId)) {
			async.updateTab(tabId, R.id.tabbuscar);
			mCurrentTab = 1;
			
			return;
		}

	}

	private class AsyncCaller extends AsyncTask<Void, Void, Void>
	{
		private String tabId;
		private int placeholder;

		private void updateTab(String tabId, int placeholder) {
			this.tabId=tabId;
			this.placeholder=placeholder;

			doInBackground();

		}

		@Override
		protected Void doInBackground(Void... params) {

			if (fm.findFragmentByTag(tabId) == null) {
				if (TAB_AMIGOS.equals(tabId)) {
					fm.beginTransaction()
					.replace(placeholder, new AmigosListFragment(tabId,amigos), tabId)
					.commit();
				}
				else if  (TAB_BUSCAR.equals(tabId)) {
					
					fm.beginTransaction()
					
					.replace(R.id.ListViewBuscarAmigos, new BusquedaListFragment(tabId, amigos), tabId)
					.commit();
				}
			}return null;
		}

	}

}
