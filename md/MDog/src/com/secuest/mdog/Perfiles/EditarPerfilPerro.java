package com.secuest.mdog.Perfiles;

import com.secuest.mdog.R;
import com.secuest.mdog.BuscarPerrosCerca.BusqRazas;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.menu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class EditarPerfilPerro extends Activity {
	
	private Button boton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarperfilperro_layout);
        

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
      
        TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tabperfilperroprivadoeditar);
        spec1.setIndicator("Perfil");
      
      
        TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("Cartilla");
        spec2.setContent(R.id.tabcartillaeditar);
        
       
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onclickguardar(View vista){
		startActivity(new Intent(EditarPerfilPerro.this, PerfilPerroPrivado.class));
			}
    
    public void onclickRaza(View vista){
		startActivity(new Intent(EditarPerfilPerro.this, BusqRazas.class));
			}
    
}