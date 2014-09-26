package com.secuest.mdog.settings;

import com.secuest.mdog.R;
import com.secuest.mdog.R.id;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.menu;

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

public class Settings extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button protectora = (Button) findViewById(R.id.protectora);
		protectora.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Settings.this,ApartadoProtectora.class));
			}
		});
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