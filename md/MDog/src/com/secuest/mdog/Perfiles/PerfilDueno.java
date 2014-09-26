package com.secuest.mdog.Perfiles;
//Perfil de la perso
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.android.gms.internal.du;
import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.content.Intent;
import android.content.res.Resources;




public class PerfilDueno extends Activity {
	
	public static final String TAB_PERFIL = "Perfil";
	public static final String TAB_PERROS = "Perros";
	public static final String TAB_PARQUES = "Parques";
	public static final String TAB_MURO = "Muro";
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private TabHost tabHost;
	//private Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private Dueno dueno;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.perfildueno_layout);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
        ListView list = (ListView) findViewById(R.id.listViewPerrosDueno);
        EditText nombre = (EditText) findViewById(R.id.NombreDueno);
        EditText ciudad = (EditText) findViewById(R.id.textCiudad);
        ImageView image = (ImageView) findViewById(R.id.ImageViewFotoDueno);

        dueno = (Dueno) getIntent().getSerializableExtra("Dueno");
        try {
			image.setImageBitmap((new Cache()).cargarImagen(dueno.getImage(),getBaseContext()));
		} catch (FileNotFoundException e) {
				image.setImageResource(R.drawable.sombra_persona);
		}
        nombre.setText(dueno.getNick());
        ciudad.setText(dueno.getCiudad());
        
        
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        ArrayList<Perro> misPerros= dueno.getPerros();
		for(int i=0;i<misPerros.size();i++)
			datos.add(new Lista_entrada(misPerros.get(i).getImage(), 2, new String[]{ misPerros.get(i).getNombre(), misPerros.get(i).getRaza().toString()}));
		
        
          
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
							}
						
					}
				}
			};
		}
		
		list.setAdapter(mAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
					Intent i =new Intent(PerfilDueno.this, PerfilPerro.class);
					i.putExtra("Perro",dueno.getPerros().get(position));
					startActivity(i);
			   } 
			});
		
        getResources();
        tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        View perfil = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perfil.findViewById(R.id.text)).setText("Perfil");
		
		View muro = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) muro.findViewById(R.id.text)).setText("Muro");
		
		View parques = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) parques.findViewById(R.id.text)).setText("Parques");
      
        TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tabperfildueno);
        spec1.setIndicator(perfil);
      
      
        TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator(muro);
        spec2.setContent(R.id.tabmurodueno);
        
        TabSpec spec3=tabHost.newTabSpec("TAB 3");
        spec3.setContent(R.id.tabparquesdueno);
        spec3.setIndicator(parques);
        
        /*TabSpec spec4=tabHost.newTabSpec("TAB 4");
        spec3.setContent(R.id.tabmurodueno);
        spec3.setIndicator("Wall");*/
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        //tabHost.addTab(spec4);
        
     
    }
    
    
    public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return true;
	}
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.ic_action_new_event).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
   
    
}