package com.secuest.mdog;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.secuest.mdog.Muro.Publicar;
import com.secuest.mdog.utils_MySql.DatabaseHandler;
import com.secuest.mdog.utils_MySql.UserFunctions;
import com.secuest.mdog.utils_MySql.ola;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InicioSesion extends Activity {
	// JSON Response node names
		private static String KEY_SUCCESS = "success";
		private static String KEY_ERROR = "error";
		private static String KEY_ERROR_MSG = "error_msg";
		private static String KEY_UID = "uid";
		private static String KEY_NAME = "name";
		private static String KEY_EMAIL = "email";
		private static String KEY_PASS = "password";
		private static final String KEY_IMG = "image";
		private static String KEY_CREATED_AT = "created_at";
		private static final String KEY_UPDATED_AT = "updated_at";

		Drawable dra;
		boolean bool=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		final EditText email = (EditText) findViewById(R.id.editText1);
		final EditText password = (EditText) findViewById(R.id.editText2);
		Button IniciaSesion = (Button) findViewById(R.id.IniciaSesion);
		IniciaSesion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				final ImageView ima = (ImageView) findViewById(R.id.imagenTest);
				 
				new Thread(new Runnable() {
					public void run() {
						ola o = new ola();
						dra = o.downloadImage();
						bool=false;
					}
				}).start();
				while(bool){
					try {
					      Thread.sleep(100);
					   } catch (InterruptedException e) {
					      // TODO Auto-generated catch block
					      e.printStackTrace();
					   }
				}
				ima.setImageDrawable(dra);*/
				
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(email.getText().toString(), password.getText().toString());

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL),password.getText().toString(),  json_user.getString("city"), json_user.getString(KEY_IMG),json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT), json_user.getString(KEY_UPDATED_AT));
							
							startActivity(new Intent(InicioSesion.this, DrawerActivity.class));
						
						}else{
						
							// Error in login
							Toast toast = Toast.makeText(InicioSesion.this, "Correo electronico o contrase–a incorrectos", Toast.LENGTH_SHORT);
							toast.show();
							System.out.println("Mensaje");
							Log.d("MiTAG","Incorrect username/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		Button registrate = (Button)findViewById(R.id.TeHasOlvidado);
		registrate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioSesion.this, RestablecerPass.class));

			}
		});

		Button regis = (Button)findViewById(R.id.Registrate);
		regis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(InicioSesion.this, RegistroCorreo.class));
				
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
			startActivity(new Intent(InicioSesion.this, DrawerActivity.class));
		}
		return true;
	}
}