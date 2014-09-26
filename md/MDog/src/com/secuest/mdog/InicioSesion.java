package com.secuest.mdog;

import org.json.JSONException;
import org.json.JSONObject;

import com.secuest.mdog.utils_MySql.DatabaseHandler;
import com.secuest.mdog.utils_MySql.UserFunctions;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class InicioSesion extends Activity {
	// JSON Response node names
		private static String KEY_SUCCESS = "success";
		private static String KEY_ERROR = "error";
		private static String KEY_ERROR_MSG = "error_msg";
		private static String KEY_UID = "uid";
		private static String KEY_NAME = "name";
		private static String KEY_EMAIL = "email";
		private static String KEY_CREATED_AT = "created_at";

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
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(email.getText().toString(), password.getText().toString());

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						Log.d("Mitag", "problems?");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
							finish();
						}else{
							// Error in login
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

		Button inicio_sesion = (Button)findViewById(R.id.Registrate);
		inicio_sesion.setOnClickListener(new OnClickListener() {

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