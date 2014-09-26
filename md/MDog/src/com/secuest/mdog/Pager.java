package com.secuest.mdog;

import java.sql.Date;
import java.util.List;
import java.util.Vector;

import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.Perfiles.PerfilParque;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
import com.secuest.mdog.utils.RWFile;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pager extends FragmentActivity {
	
	
	private PagerAdapter mPagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RWFile rw = new RWFile();
		if(rw.read("Fsttime")!=null){
			startActivity(new Intent(Pager.this, DrawerActivity.class));
		}
		setContentView(R.layout.viewpager_layout);
		initialisePaging();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button registrate = (Button)findViewById(R.id.registrate);
		registrate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RWFile rw = new RWFile();
				rw.write("Fsttime", "");
				startActivity(new Intent(Pager.this, Portada.class));

			}
		});
		
		Button inicio_sesion = (Button)findViewById(R.id.inicio_sesion);
		inicio_sesion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RWFile rw = new RWFile();
				rw.write("Fsttime", "");
				startActivity(new Intent(Pager.this, Portada.class));
			}
		});
	}
	
	private void initialisePaging() {
		// TODO Auto-generated method stub
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,Fragment1.class.getName()));
		fragments.add(Fragment.instantiate(this,Fragment2.class.getName()));
		fragments.add(Fragment.instantiate(this,Fragment3.class.getName()));
		mPagerAdapter =new PagerAdapter(this.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(mPagerAdapter);
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
		menu.findItem(R.id.omitir).setVisible(true);
		menu.findItem(R.id.ic_action_camera).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(Pager.this, DrawerActivity.class));
		
		case R.id.omitir:
			startActivity(new Intent(Pager.this, DrawerActivity.class));
		}
		return true;
	}
}
