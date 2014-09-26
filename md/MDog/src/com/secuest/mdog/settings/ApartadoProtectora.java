package com.secuest.mdog.settings;

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




public class ApartadoProtectora extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portada);
		
		ViewGroup root = (ViewGroup)findViewById(R.id.root);
		root.removeView((View)findViewById(R.id.botonFacebook));
		root.removeView((View)findViewById(R.id.botonGoogle));
		
		Button accede = (Button)findViewById(R.id.button1);
		Button registrate = (Button) findViewById(R.id.button3);
		
		accede.setText("Accede a tu protectora");
		registrate.setText("Registra tu protectora");
		
		accede.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ApartadoProtectora.this,AccesoProtectora.class));
			}
		});
		registrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ApartadoProtectora.this,RegistroProtectora.class));
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

