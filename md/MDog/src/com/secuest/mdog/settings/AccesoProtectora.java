package com.secuest.mdog.settings;

import com.secuest.mdog.DrawerActivity;
import com.secuest.mdog.InicioSesion;
import com.secuest.mdog.R;

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




public class AccesoProtectora extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio_sesion);

		Button registrala = (Button) findViewById(R.id.Registrate);
		registrala.setText("Registrala");
		registrala.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AccesoProtectora.this, RegistroProtectora.class));
			}
		});
		
		Button inicio = (Button) findViewById(R.id.IniciaSesion);
		inicio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AccesoProtectora.this, DrawerActivity.class));
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

