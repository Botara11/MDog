package com.secuest.mdog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.em;
import com.secuest.mdog.Amigos.Amigos;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Parque;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Cita;
import com.secuest.mdog.utils_MySql.DatabaseHandler;
import com.secuest.mdog.utils_MySql.UserFunctions;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class RegistroCorreo extends Activity {
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_PASS = "password";
	private static String KEY_CREATED_AT = "created_at";
	private static final String KEY_UPDATED_AT = "updated_at";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_correo);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
				
		Button inicio_sesion = (Button)findViewById(R.id.IniciaSesion);
		inicio_sesion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegistroCorreo.this, InicioSesion.class));

			}
		});
		
		final EditText email = (EditText) findViewById(R.id.Email);
		final EditText pass1 = (EditText) findViewById(R.id.Pass1);
		final EditText pass2 = (EditText) findViewById(R.id.Pass2);
		final EditText nombre = (EditText) findViewById(R.id.Nombre);
		final EditText ciudad = (EditText) findViewById(R.id.Ciudad);
		
		
		Button Registrate = (Button) findViewById(R.id.Registrate);
		Registrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				String name = nombre.getText().toString();
				String mail = email.getText().toString();
				String password = pass1.getText().toString();
				String city = ciudad.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, mail, password,city);
				
				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						//registerErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully registred
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL),pass1.getText().toString(),  json.getString("city"),null, json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT), json_user.getString(KEY_UPDATED_AT));						
							// Launch Dashboard Screen
							startActivity(new Intent(RegistroCorreo.this, DrawerActivity.class));
						}else{
							System.out.println("Un peq error json");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			
				
				
				Intent i =new Intent(RegistroCorreo.this,DrawerActivity.class);
				i.putExtra("Dueno", (new Dueno(email.getText().toString(), null, nombre.getText().toString(), ciudad.getText().toString(), (new ArrayList<Perro>()), (new ArrayList<Parque>()), (new ArrayList<Cita>()),(new ArrayList<Dueno>()) )));
				i.putExtra("Email",email.getText().toString());
				i.putExtra("Pass",pass1.getText().toString());
				startActivity(i);
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
			startActivity(new Intent(RegistroCorreo.this, DrawerActivity.class));
		}
		return true;
	}
}