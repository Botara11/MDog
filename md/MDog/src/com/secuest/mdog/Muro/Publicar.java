package com.secuest.mdog.Muro;


import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Protectora;


public class Publicar extends Activity {

	protected static final int FOTO_CAMARA = 2;
	protected static final int RECORTAR = 3;
	private static final int FOTO_GALERIA = 4;
	private ImageView img;
	Button b;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicar_layout);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		b = (Button) findViewById(R.id.xbutton);
		img = (ImageView) this.findViewById(R.id.fotoPublicar);
		b.setVisibility(View.INVISIBLE);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				img.setVisibility(View.INVISIBLE);
				b.setVisibility(View.INVISIBLE);
				
				//MEMORIZAR QUE NO QUIERE SUBIR LA FOTO
			}
		});
		
		Protectora protectora = (Protectora) getIntent().getSerializableExtra("Protectora");
		Spinner spin = (Spinner) findViewById(R.id.spin);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		spin.setLayoutParams(params);
		if(protectora!=null){

			//************************BASE DE DATOS
			File cacheDir = getBaseContext().getCacheDir();
			Test test = new Test(getResources(),cacheDir);
			test.Load();
			Dueno miUser = test.kate;
			//***********************BASE DE DATOS

			params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 60);
			spin.setLayoutParams(params);
			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item,
					new String[]{"Publicar como: "+miUser.getNick(),
					"Publicar como: "+protectora.getNombre()});
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin.setAdapter(adapter);
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
		menu.findItem(R.id.ic_action_camera).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}




	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {

		case R.id.ic_action_camera:
			// create intent to perform a photo
			android.app.FragmentManager fm = getFragmentManager();
			PopupSelect editNameDialog = new PopupSelect();
			editNameDialog.show(fm, "fragment_edit_name");
			/*
			Intent cameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraInt, CAMERA_PIC_REQUEST);*/
			break;
		case android.R.id.home:
		finish();   
		}
		return true;
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

		switch(requestCode) { 
		
		case FOTO_GALERIA:
		case FOTO_CAMARA:
			if(resultCode == RESULT_OK){  
				Uri selectedImage = imageReturnedIntent.getData();
				
				
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();
				Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
				int a =img.getWidth() * yourSelectedImage.getHeight();
				a=a/yourSelectedImage.getWidth();
				img.setImageBitmap(Bitmap.createScaledBitmap(yourSelectedImage, img.getWidth(), a, false));
	            //img.setImageBitmap(yourSelectedImage);
				img.setVisibility(View.VISIBLE);
				b.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
}


