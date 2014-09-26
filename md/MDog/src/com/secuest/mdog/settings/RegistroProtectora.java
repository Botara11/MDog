package com.secuest.mdog.settings;

import java.util.ArrayList;

import com.secuest.mdog.DrawerActivity;
import com.secuest.mdog.InicioSesion;
import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.PerroProtect;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;




public class RegistroProtectora extends Activity{
	private EditText correo,pass,pass2,nombre,ciudad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro_protectora);
		
		 correo = (EditText) findViewById(R.id.Email);
		 pass = (EditText) findViewById(R.id.Pass1); 
		 pass2 = (EditText) findViewById(R.id.Pass2);
		 nombre = (EditText) findViewById(R.id.Nombre);
		 ciudad = (EditText) findViewById(R.id.Ciudad);
		Button registrala = (Button) findViewById(R.id.Registrate);
		Button IniciaSesion = (Button) findViewById(R.id.IniciaSesion);
		//root.removeView((View)findViewById(R.id.botonGoogle));
		
		registrala.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Protectora pro = new Protectora(nombre.getText().toString(), "", "", ciudad.getText().toString(), 0.0, 0.0, 0, correo.getText().toString(), "", new ArrayList<PerroProtect>());
				Intent i =new Intent(RegistroProtectora.this,DrawerActivity.class);
    			i.putExtra("Protectora", pro);
    			startActivity(i);
			}
		});
		
		IniciaSesion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegistroProtectora.this, AccesoProtectora.class));
			}
		});
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
	}
	
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
			menu.findItem(R.id.ic_action_new_event).setVisible(false);
			menu.findItem(R.id.omitir).setVisible(false);
			menu.findItem(R.id.ic_action_camera).setVisible(false);			
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

