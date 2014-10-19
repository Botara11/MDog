package com.secuest.mdog.utils;

import com.secuest.mdog.DrawerActivity;
import com.secuest.mdog.Pager;
import com.secuest.mdog.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class Zoom2 extends Activity {

	private ImageView img;
	private Matrix matrix = new Matrix();
	private float scale = 1f;
	private ScaleGestureDetector SGD;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zoom_imagen);
		img = (ImageView)findViewById(R.id.zoom_view);
		SGD = new ScaleGestureDetector(this,new ScaleListener());

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		SGD.onTouchEvent(ev);
		return true;
	}

	private class ScaleListener extends ScaleGestureDetector.
	SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			scale = Math.max(0.1f, Math.min(scale, 5.0f));
			matrix.setScale(scale, scale);
			img.setImageMatrix(matrix);
			return true;
		}
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
			finish();}
		return true;
	}


}
