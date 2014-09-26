package com.secuest.mdog.Perfiles;
//Perfil de la perso
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Parque;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Test;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import android.content.res.Resources;




public class PerfilParque extends Activity {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_PERFIL = "Perfil";
	public static final String TAB_PERROS = "Perros";
	public static final String TAB_PARQUES = "Parques";
	public static final String TAB_MURO = "Muro";
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private TabHost tabHost;
	private ArrayList<String> mItems;
	private Parque parque;
	private ArrayList<Perro> perrosParque;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.perfilparque_layout);
        ListView list = (ListView)findViewById(R.id.ListViewPerrosProtectora);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        

		parque = (Parque) getIntent().getSerializableExtra("Parque");
        System.out.println(parque.getNombre());
        
        EditText nombre = (EditText) findViewById(R.id.NombreParque);
        EditText ciudad = (EditText) findViewById(R.id.textCiudadParque);
        EditText direccion = (EditText) findViewById(R.id.EditTextDireccion);
        
        nombre.setText(parque.getNombre());
        ciudad.setText(parque.getCiudad());
        direccion.setText(parque.getDireccion());
       
        
        /*************  BASE DE DATOS  ***********************/
         Test t = new Test(getResources(), getCacheDir());
         t.Load();
         perrosParque = t.crysti.getPerros();
        ArrayList<Perro> b =t.kate.getPerros();
        for (int i=0;i<b.size();i++)
        	perrosParque.add(b.get(i));
        /*************  BASE DE DATOS  ***********************/
        
        
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  
		for(int i=0;i<perrosParque.size();i++)
			datos.add(new Lista_entrada(perrosParque.get(i).getImage(), 2, new String[]{ perrosParque.get(i).getNombre(), perrosParque.get(i).getRaza().toString()}));
		
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(this, R.layout.list_item_amigos, datos){
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
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
								e.printStackTrace();
							}
					}
					
				}
			};








		}
		
		list.setAdapter(mAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
					Intent i =new Intent(PerfilParque.this, PerfilPerro.class);
					i.putExtra("Perro",perrosParque.get(position));
					startActivity(i);
			   } 
			});
		
		
        Resources res = getResources(); // Resource object to get Drawables
        tabHost=(TabHost)findViewById(R.id.TabHost);
        tabHost.setup();
        
        View perros = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perros.findViewById(R.id.text)).setText("Perros");
		
		View perfil = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perfil.findViewById(R.id.text)).setText("Perfil Parque");
      
        TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tabperrosparque);
        spec1.setIndicator(perros);
      
      
        TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator(perfil);
        spec2.setContent(R.id.tabperfilparque);
        
       
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        
        
        
      
     
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.ic_action_new_event).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
    public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return true;
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}