package com.secuest.mdog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Portada extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.portada);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Button registrate = (Button)findViewById(R.id.button1);
		registrate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Portada.this, RegistroCorreo.class));

			}
		});

		Button inicio_sesion = (Button)findViewById(R.id.button3);
		inicio_sesion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Portada.this, InicioSesion.class));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
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
			startActivity(new Intent(Portada.this, Pager.class));
		}
		return true;
	}

	public void onClickSinggoogle(View vista){
		startActivity(new Intent(Portada.this, DrawerActivity.class));
	}
	public void onClickRegistrate(View vista){
		startActivity(new Intent(Portada.this, RegistroCorreo.class));
	}

	public void onClickPerfilPerros(View vista){
		startActivity(new Intent(Portada.this, TodosPerros.class));
	}

	public void onClickPerfilDueno(View vista){
		startActivity(new Intent(Portada.this, ActivityCam.class));
	}

}