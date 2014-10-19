package com.secuest.mdog.Perfiles;
//Perfil de la perso
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.PerroProtect;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.R.id;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.menu;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.content.res.Resources;





public class PerfilProtectora extends Activity {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_PERFIL = "Perfil";
	public static String TAB_PERROS = "Perros";
	public static final String TAB_PARQUES = "Parques";
	public static final String TAB_MURO = "Muro";
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private TabHost tabHost;
	private ArrayList<String> mItems;
	private ArrayList<PerroProtect> perrosProtect ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.perfilprotectora_layout);
        ListView list = (ListView)findViewById(R.id.ListViewPerrosProtectora);
        EditText direccion = (EditText) findViewById(R.id.DireccionProtectora);
        EditText telefono = (EditText)findViewById(R.id.TelefonoProtectora);
        EditText email = (EditText)findViewById(R.id.CorreoProtectora);
        EditText direccWeb = (EditText) findViewById(R.id.WebProtectora);
        
        
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

        /**** BASE DE DATOS **********/
        ArrayList<PerroProtect> perrosProtect;
   	 	Protectora protectora = (Protectora) getIntent().getSerializableExtra("Protectora");
   	 	perrosProtect = protectora.getPerros();
        /**** BASE DE DATOS **********/
        
   	 	
   	 	direccion.setText(protectora.getDireccion());
   	 	if(protectora.getTelefono()==0)
   	 		telefono.setText("Sin telefono");
   	 	else
	 		telefono.setText(protectora.getTelefono()+"");
   	 	email.setText(protectora.getEmail());
   	 	direccWeb.setText(protectora.getPaginaWeb());
   	 	direccWeb.setMaxLines(2);
   	 	
		for(int i=0;i<perrosProtect.size();i++)
			datos.add(new Lista_entrada(perrosProtect.get(i).getImage(), 2, new String[]{ perrosProtect.get(i).getNombre(), perrosProtect.get(i).getRaza().toString()}));
		
		
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(this, R.layout.list_item_amigos, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textcentrado); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 
						  
						TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_texto(1)); 

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
		
        Resources res = getResources(); // Resource object to get Drawables
        tabHost=(TabHost)findViewById(R.id.TabHost);
        tabHost.setup();
        
        View perros = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perros.findViewById(R.id.text)).setText("Perros");
		
		View perfil = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perfil.findViewById(R.id.text)).setText("Perfil Protectora");
      
        TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tabperrosprotectora);
        spec1.setIndicator(perros);
      
      
        TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator(perfil);
        spec2.setContent(R.id.tabperfilprotectora);
        
       
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        
        
        
      
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}