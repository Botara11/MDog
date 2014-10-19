package com.secuest.mdog;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.BitmapDrawable;

import com.secuest.mdog.Adopcion.Adopcion;
import com.secuest.mdog.Amigos.Amigos;
import com.secuest.mdog.BuscarPerrosCerca.BuscarPerrosCerca;
import com.secuest.mdog.Citas.Citas;
import com.secuest.mdog.Logica.Cita;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Parque;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.Mapa.SelectMapa;
import com.secuest.mdog.MiCuenta.MiCuenta;
import com.secuest.mdog.MiProtectora.MiPropiaProtectora;
import com.secuest.mdog.Muro.Muro;
import com.secuest.mdog.Muro.Publicar;
import com.secuest.mdog.Protectoras.Protectoras;
import com.secuest.mdog.settings.Settings;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;
import com.secuest.mdog.utils.MySQL_SingIn;
//import android.app.Fragment;
import com.secuest.mdog.utils.RWFile;
import com.secuest.mdog.utils_MySql.DatabaseHandler;
import com.secuest.mdog.utils_MySql.UserFunctions;
import com.secuest.mdog.utils_MySql.uploadImage;

public class DrawerActivity extends FragmentActivity implements DrawerInterface {
	private static final int 	BUSQ_RAZA 		= 1;
	protected static final int 	FOTO_CAMARA 	= 2;
	protected static final int 	RECORTAR 		= 3;
	private static final int 	FOTO_GALERIA 	= 4;
	protected static final int 	COMENTARIO_MURO = 5;
	protected static final int 	ANADIR_PERRO_RESULT = 6;
	protected static final int 	MODIFICAR_PERRO = 7;

	private DrawerLayout mDrawerLayout;
	private ListView List;
	private Lista_adaptador mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout mDrawerLinear;
	private int count = 0, antFragment =99;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDrawerTitles;
	protected String result;
	private boolean isProtectora = false;
	private Protectora protectora;
	private Dueno miUser;
	private Fragment fragment;
	private UserFunctions userFunction;
	private boolean isUserLoggedIn = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_layout);

		userFunction = new UserFunctions();
		System.out.println("esta logueado? ="+userFunction.isUserLoggedIn(getApplicationContext()));
		isUserLoggedIn=userFunction.isUserLoggedIn(getApplicationContext());


		mTitle = mDrawerTitle = getTitle();
		mDrawerTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		List = (ListView) findViewById(R.id.left_drawer);
		mDrawerLinear =(LinearLayout)findViewById(R.id.drawer_view);
		ImageButton settings = (ImageButton) findViewById(R.id.settings);
		final TextView accedeoregistrate = (TextView) findViewById(R.id.accedeoregistrate);

		if(!userFunction.isUserLoggedIn(getApplicationContext()))
			accedeoregistrate.setText("Accede o Registrate");
		else
			accedeoregistrate.setText("Cerrar sesion");
		accedeoregistrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userFunction.isUserLoggedIn(getApplicationContext())){
					userFunction.logoutUser(getApplicationContext());
					accedeoregistrate.setText("Accede o Registrate");
				}
				else
					startActivity(new Intent(DrawerActivity.this,Portada.class));
			}
		});

		/*
		MySQL_SingIn dao = new MySQL_SingIn();
	    try {
			dao.readDataBase();
		} catch (Exception e) {
			System.out.println("ERROR: "+e);
			e.printStackTrace();
		}
		 */

		/****************************************************************************************************/
		/****************************************************************************************************/
		//Protectora Para hacer pruebas y no registrarse cada vez
		//protectora= new Protectora("La canera", "", "", "Valencia", 0.0, 0.0, 0, "mimailss", "", new ArrayList<PerroProtect>());
		protectora= (Protectora) getIntent().getSerializableExtra("Protectora");
		if(protectora!=null){
			//Anadir MIProtectora al Drawer
			isProtectora = true;
			String[] sss = new String [mDrawerTitles.length+1];
			for(int i=0;i<mDrawerTitles.length;i++)

				sss[i]=mDrawerTitles[i];
			sss[mDrawerTitles.length]="MiProtectora";
			mDrawerTitles=sss;
			System.out.println(mDrawerTitles.length +"  "+ sss.length	);
		}
		if(isUserLoggedIn){
			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			final HashMap<String, String> usuario = db.getUserDetails();
			System.out.println(usuario.toString());
			miUser = new Dueno(usuario.get("email"), usuario.get("image"), usuario.get("name"), usuario.get("city"), new ArrayList<Perro>(), new ArrayList<Parque>(), new ArrayList<Cita>(), new ArrayList<Dueno>());
			if(usuario.get("image")!=null){
				new Thread(new Runnable() {
					public void run() {
						UserFunctions o = new UserFunctions();
						Drawable dra = o.downloadImage(usuario.get("image"));
						if(dra!=null){
							Bitmap bitmap = ((BitmapDrawable)dra).getBitmap();
							(new Cache()).guardarImagenCache(usuario.get("image"), bitmap, getCacheDir());
						}
					}
				}).start();


			}
		}

		if(getIntent().getSerializableExtra("Dueno")!=null)
			miUser = (Dueno) getIntent().getSerializableExtra("Dueno");

		/****************************************************************************************************/
		/****************************************************************************************************/





		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  


		//mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		//	R.layout.drawer_list_item, mDrawerTitles));

		String[] sss = new String [mDrawerTitles.length+1];
		for(int i=0;i<mDrawerTitles.length;i++){

			sss[i]=mDrawerTitles[i];
		}

		int[] textureArrayWin = {
				R.drawable.retiro,
				R.drawable.una,
				R.drawable.marker_violeta,
				R.drawable.marker,
				R.drawable.notification,
				R.drawable.silueta_perro,
				R.drawable.ic_action_save,
				R.drawable.retiro,
				R.drawable.una,
				R.drawable.retiro,
				R.drawable.una,
		};


		if(protectora==null){
			
			for(int i=0;i<mDrawerTitles.length;i++)
				datos.add(new Lista_entrada(textureArrayWin[i],2, new String[]{ mDrawerTitles[i], null}));

			if (mDrawerList == null) {
				ArrayList<String> mItems = new ArrayList<String>();

				mDrawerList = new Lista_adaptador(this, R.layout.drawer_list_item, datos){
					@Override
					public void onEntrada(Object entrada, View view) {
						if (entrada != null) {
							TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textcentrado); 
							if (texto_superior_entrada != null) 
								texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 

							/*TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); */

							ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
							if (imagen_entrada != null)
								imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
						}
					}
				};
			}
		}
			else if(protectora!=null){

				//cambiar el color, ya se hara cuando se pongan iconos en el drawer
				for(int i=0;i<sss.length-1;i++)
					datos.add(new Lista_entrada(textureArrayWin[i],2, new String[]{ sss[i], null}));
				System.out.println("Protectora !=null");

				if (mDrawerList == null) {
					ArrayList<String> mItems = new ArrayList<String>();

					mDrawerList = new Lista_adaptador(this, R.layout.drawer_list_item, datos){
						@Override
						public void onEntrada(Object entrada, View view) {
							if (entrada != null) {
								TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textcentrado); 
								if (texto_superior_entrada != null) 
									texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 

								/*TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
								if (texto_inferior_entrada != null)
									texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); */

								ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
								if (imagen_entrada != null)
									imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
							}
						}
					};
				}
			}


		





		settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent (DrawerActivity.this,Settings.class));
			}
		});
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		List.setAdapter(mDrawerList);

		List.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}


		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinear);
		if(antFragment==0)
			menu.findItem(R.id.ic_action_new_event).setVisible(true & !drawerOpen);
		else 
			menu.findItem(R.id.ic_action_new_event).setVisible(false);

		menu.findItem(R.id.omitir).setVisible(false);
		menu.findItem(R.id.ic_action_camera).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons ////////////////////Busqueda esta capado
		switch(item.getItemId()) {
		case R.id.ic_action_new_event:
			Intent i = new Intent(this, Publicar.class);
			i.putExtra("Protectora", protectora);
			//i.putExtra("Dueno", value)
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {	
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}
	///////////////////////////  AQUI HAY QUE LLAMAR A //////////////////////////////////  
	///////////////////////////   A LOS FRAGMENTS      //////////////////////////////////


	private void selectItem(int position) {    
		if(antFragment!=position){

			System.out.println(position);
			Bundle args = new Bundle();

			if (position == 0){
				fragment = new Muro();
				args.putInt(Muro.ARG_PLANET_NUMBER, position);
			}
			else if (position == 1) {
				fragment = new SelectMapa();
				args.putInt(SelectMapa.ARG_PLANET_NUMBER, position);
			}else if (position == 2) {
				fragment = new MiCuenta();
				args.putInt(MiCuenta.ARG_PLANET_NUMBER, position);
				args.putSerializable("Dueno", miUser);
			}else if (position == 3) {
				fragment = new Amigos();
				args.putInt(Amigos.ARG_PLANET_NUMBER, position);
			}else  if (position==4){
				fragment = new Protectoras();
				args.putInt(Protectoras.ARG_PLANET_NUMBER, position);
			}else  if (position==5){
				fragment = new Citas();
				args.putInt(Citas.ARG_PLANET_NUMBER, position);
			}else  if (position==6){
				fragment = new BuscarPerrosCerca();
				args.putInt(BuscarPerrosCerca.ARG_PLANET_NUMBER, position);
			}else  if (position==8){
				fragment = new MiPropiaProtectora();
				args.putInt(BuscarPerrosCerca.ARG_PLANET_NUMBER, position);
				args.putSerializable("Protectora", protectora);
			}else  if (position==7){
				fragment = new Adopcion();
				args.putInt(Adopcion.ARG_PLANET_NUMBER, position);
			}else{
				fragment = new Planeta();
				args.putInt(Planeta.ARG_PLANET_NUMBER, count);
			}

			if(!isUserLoggedIn && position!=0){
				Toast toast = Toast.makeText(this, "Por favor REGISTRATE o INICIA SESION", Toast.LENGTH_SHORT);
				toast.show();
			}else{
				Fragment prub = getSupportFragmentManager().findFragmentById(R.id.mapi);
				if(prub!=null)
					getSupportFragmentManager().beginTransaction().
					remove(getSupportFragmentManager().findFragmentById(R.id.mapi)).commit();
				System.out.println("ELIMINAR MAP count="+count);

				fragment.setArguments(args);
				FragmentManager fmanager = getSupportFragmentManager();
				fmanager.beginTransaction().replace(R.id.content_frame, fragment).commit();


				// update selected item and title, then close the drawer
				if(position<5){
					List.setItemChecked(position, true);
					setTitle(mDrawerTitles[position]);}
			}
		}
		antFragment=position;
		mDrawerLayout.closeDrawer(mDrawerLinear); 
	}

	public void botonMapa(View vista){
		selectItem(1);
	}

	public void botonMiPerfil(View vista){
		selectItem(21);
	}
	public void botonEditarPerfil(View vista){
		selectItem(22);
	}

	@Override
	public void onBackPressed() {
		System.out.println("Pulsado: "+antFragment);
		if(antFragment!=0){
			selectItem(0);
			System.out.println("ATRAAS");
		}else{
			Toast toast = Toast.makeText(this, "Pulsa atr‡s de nuevo para salir", Toast.LENGTH_SHORT);
			toast.show();
			System.out.println("Mensaje");
		} 
		//moveTaskToBack(true);
	}
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}



	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("EL requestcode es :"+requestCode);
		switch(requestCode) {

		case BUSQ_RAZA:  //CODIGO de BusqRazas
			if(resultCode == RESULT_OK){
				result=data.getStringExtra("result");

				Button b = ((Button)findViewById(R.id.busca_but));
				if(b!=null)
					b.setText(result);
				b = ((Button)findViewById(R.id.raza));
				if(b!=null)
					b.setText(result);
				System.out.println(result);
			}
			if (resultCode == RESULT_CANCELED) {
				System.out.println("Resultado de BusqRazas error");
			}
			break;

		case FOTO_CAMARA:  //Captura imagen camara
			if(resultCode == RESULT_OK){  
				Uri selectedImage = data.getData();
				recortar(selectedImage);
			}
			break;

		case RECORTAR:
			if (data != null) {
				// get the returned data
				Bundle extras = data.getExtras();
				// get the cropped bitmap
				Bitmap selectedBitmap = extras.getParcelable("data");
				ImageView img = (ImageView) this.findViewById(R.id.ImageViewFotoDueno);
				img.setImageBitmap(selectedBitmap);
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				HashMap<String, String> usuario = db.getUserDetails(); 
				(new Cache()).guardarImagenCache(usuario.get("uid"),selectedBitmap, this.getCacheDir());
				((MiCuenta) fragment).changImage=true;
			}
			break;
		case FOTO_GALERIA:
			Uri selectedImage = data.getData(); 
			recortar(selectedImage);
			break;
		case ANADIR_PERRO_RESULT:
			if (data != null) {
				Perro p = (Perro) data.getExtras().getSerializable("Perro");
				((MiCuenta)fragment).anadirPerro(p);
			}
			break;
		case MODIFICAR_PERRO:
			if (data != null) {
				Perro p = (Perro) data.getExtras().getSerializable("Perro");
				int pos = data.getExtras().getInt("Posicion");
				((MiCuenta)fragment).modificarPerro(p,pos);
			}
			break;
		}

	}//onActivityResult

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	private void recortar(Uri picUri){

		try {
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 300);
			cropIntent.putExtra("outputY", 300);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, RECORTAR);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}



	public void seleccionDeFragment(int posicion){
		selectItem(posicion);
	}

}
