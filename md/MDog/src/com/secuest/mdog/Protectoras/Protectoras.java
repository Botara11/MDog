package com.secuest.mdog.Protectoras;
//Listado de Protector
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.MiCuenta.CitasListFragment;
import com.secuest.mdog.MiCuenta.MisPerrosListFragment;
import com.secuest.mdog.MiCuenta.ParquesListFragment;
import com.secuest.mdog.R.array;
import com.secuest.mdog.R.id;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.string;
import com.secuest.mdog.Test;

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


	
public class Protectoras extends Fragment implements OnTabChangeListener{
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private static final String TAG = "FragmentTabs";
	public static final String TAB_PROTECTORAS = "Protectoras";
	public static final String TAB_TODOS_PERROS = "Ver Todos Los Perros";
	
	private AsyncCaller async;
    private View rootView;
	private TabHost mTabHost;
	private int mCurrentTab;
	private FragmentManager fm;
	private ArrayList<Protectora> protectoras;

    public Protectoras(){
    	
    } 
         
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.protectoras_layout, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.planets_array)[i];
        mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
       
       
        async = new AsyncCaller();
        getActivity().setTitle(planet);
        fm = getFragmentManager(); 
		async = new AsyncCaller();
		
		
		/********************* BASE DE DATOS ****************/
		Test t = new Test(getActivity().getResources(), getActivity().getCacheDir());
        t.Load();
		protectoras = new ArrayList<Protectora>();
		protectoras.add(t.elpuig);
		protectoras.add(t.lacanera);
		/********************* BASE DE DATOS ****************/
        
		
		String [] iter = new String[] {TAB_PROTECTORAS,TAB_TODOS_PERROS};
		for( i=0;i<2;i++)
			if(fm.findFragmentByTag(iter[i])!=null){
				System.out.println("FRAGMENT encontrado");
				fm.beginTransaction().remove(fm.findFragmentByTag(iter[i])).commit();
				fm.popBackStack();
			}
		 setupTabs();
        return rootView;
    }
    


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
 
        mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		fm.beginTransaction()
		.replace( R.id.tabprotectoras, new ProtectorasListFragment(TAB_PROTECTORAS,protectoras), TAB_PROTECTORAS)
		.commit();
		
    }
    
    private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_PROTECTORAS, R.string.tab_protectoras, R.id.tabprotectoras));
		mTabHost.addTab(newTab(TAB_TODOS_PERROS, R.string.tab_todos_perros, R.id.tabverperros));
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
		if (TAB_PROTECTORAS.equals(tabId)) {
			async.updateTab(tabId, R.id.tabprotectoras);
			mCurrentTab = 0;
			return;
		}
		if (TAB_TODOS_PERROS.equals(tabId)) {
			async.updateTab(tabId, R.id.tabverperros);
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
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			if (TAB_PROTECTORAS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ProtectorasListFragment(tabId,protectoras), tabId)
			.commit();
			}
			else if  (TAB_TODOS_PERROS.equals(tabId)) {
				fm.beginTransaction()
				.replace(placeholder, new VerTodosPerrosListFragment(tabId), tabId)
				.commit();
			}
			
			
		}
		return null;
	}
    
	}
    


	
    
    

        
    
}