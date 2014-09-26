package com.secuest.mdog.utils;

import com.secuest.mdog.R;

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
import android.widget.ImageView;

public class ZoomImagen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.zoom_imagen);
		int resId = getIntent().getIntExtra("id", 0);
		ImageView a = (ImageView)findViewById(R.id.zoom_view);
		a.setImageResource(resId);
	}
}