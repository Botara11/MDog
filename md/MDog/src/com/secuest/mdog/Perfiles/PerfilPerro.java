package com.secuest.mdog.Perfiles;
//Perfil del Per
import java.io.FileNotFoundException;

import com.secuest.mdog.ActivityCam;
import com.secuest.mdog.DrawerActivity;
import com.secuest.mdog.Portada;
import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.menu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class PerfilPerro extends Activity {
	
	private Perro perro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.perfilperro_layout);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        
        final EditText newtextNombre = (EditText) findViewById(R.id.Nombre);
        final EditText newtextAficiones = (EditText) findViewById(R.id.Aficiones);
        final EditText newtextPersonalidad = (EditText) findViewById(R.id.Personalidad);
        final EditText newtextRaza = (EditText) findViewById(R.id.textRaza);
    	final EditText newTextEdad = (EditText) findViewById(R.id.textEdad);
    	final ImageView newTextGenero = (ImageView) findViewById(R.id.genero); 
    	final EditText newTextAdorable = (EditText) findViewById(R.id.NumeroAdorables);
    	final ImageView newImagePerfilPerro = (ImageView) findViewById(R.id.ImageViewFotoPeRRo);
    	
    	
    	
    	 perro = (Perro) getIntent().getSerializableExtra("Perro");
        
    	 
    	try {
			newImagePerfilPerro.setImageBitmap((new Cache()).cargarImagen(perro.getImage(),getBaseContext()));
		} catch (FileNotFoundException e) {
			newImagePerfilPerro.setImageResource(R.drawable.sombra_perro);
			e.printStackTrace();
		}
     	newtextNombre.setText(perro.getNombre());
     	newtextAficiones.setText(perro.getAficiones());
     	newtextPersonalidad.setText(perro.getPersonalidad());
     	newtextRaza.setText(perro.getRaza().toString());
     	newTextEdad.setText(perro.getEdad()+" años");
     	newTextAdorable.setText("9");  //Esta por ver lo de el nivel de adorabilidad
     	
        
        
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		//menu.findItem(R.id.action_websearch).setVisible(false);
		//menu.findItem(R.id.ic_action_new_event).setVisible(false);
    	
    	//No aparece ningun icono en el menu porq NO ESTA EL METODO     
    	//public boolean onCreateOptionsMenu(Menu menu)

		return super.onPrepareOptionsMenu(menu);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return true;
	}
    
    public void onclickperfildueno(View vista){
		//startActivity(new Intent(this, PerfilDueno.class));
		Intent i =new Intent(this, PerfilDueno.class);
		
		/********SUSTITUIR LLAMADA BASE DE DATOS*******/
			int DuenoID = Integer.parseInt(perro.getImage())/10;
			System.out.println(DuenoID);
			Test t =new Test(getResources(), getCacheDir());
			t.Load();
		/********SUSTITUIR LLAMADA BASE DE DATOS*******/
		
		i.putExtra("Dueno",(Dueno)t.crysti);
		startActivity(i);
	}
    public void onclickadorable(View vista){
    	int i=0;
    	final EditText newtextAdorable=(EditText)findViewById(R.id.NumeroAdorables);
    	i=Integer.parseInt(newtextAdorable.getText().toString())+1;
    	newtextAdorable.setText(String.valueOf(i));
    	
    }

    
    
}