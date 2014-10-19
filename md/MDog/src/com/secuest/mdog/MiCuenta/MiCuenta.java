package com.secuest.mdog.MiCuenta;
//MiCuen
import java.io.File;
import java.io.FileNotFoundException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TableRow;
import android.widget.TextView;

import com.secuest.mdog.R;
import com.secuest.mdog.Test;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Perfiles.PerfilParque;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.RWFile;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;

public class MiCuenta extends Fragment implements OnTabChangeListener, OnMenuItemClickListener {

	protected static final int 	FOTO_CAMARA 	= 2;
	protected static final int 	RECORTAR 		= 3;
	private static final int 	FOTO_GALERIA 	= 4;
	protected static final int 	COMENTARIO_MURO = 5;
	protected static final int 	ANADIR_PERRO_RESULT = 6;
	private KeyListener originalKeyListener;
	private KeyListener originalKeyListenerCiudad;

	//private ArrayList<Perro> PerroDefecto;
	public static final String ARG_PLANET_NUMBER = "planet_number";
	private static final String TAG = "FragmentTabs";
	public static final String TAB_PERFIL = "Mi Perfil";
	public FragmentManager fm;
	public android.support.v4.app.FragmentTransaction ft;
	public static final String TAB_WORDS = "Muro";
	public static final String TAB_NUMBERS = "Mis Parques";
	public static final String TAB_CITAS = "Mis Citas";
	//public static final String TAB_MURO = "Mi Muro";

	private Button boton;
	private View rootView;
	private View VistaListItemPerros;
	private TabHost mTabHost;
	private int mCurrentTab;
	private AsyncCaller async;
	private Dueno miUser;
	private String Nombre;
	private String Ciudad;
	
	private Button botonCancelar;
	private Button botonAceptar;
	private TableRow TableRowMiCuenta;
	private EditText newtextNombre;
	private Button botonEditarPerfil;
	private ImageView newImagePerfilDueno;
	private EditText newtextCiudad;
	
		
	private MisPerrosListFragment misPerrosList;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}


	public MiCuenta(){ }


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_micuenta, null);
		VistaListItemPerros = inflater.inflate(R.layout.list_item_misperros, null);
		int i = getArguments().getInt(ARG_PLANET_NUMBER);
		String planet = getResources().getStringArray(R.array.planets_array)[i];
		mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		setupTabs();

		boton= (Button) rootView.findViewById(R.id.button_prin);
		getActivity().setTitle(planet);//anadir titulo action bar
		fm = getFragmentManager();
		async = new AsyncCaller();
		

		
		
		

		//************************BASE DE DATOS
		File cacheDir = getActivity().getBaseContext().getCacheDir();
		Test test = new Test(getResources(),cacheDir);
		test.Load();
		//miUser = test.jes;
		
		Dueno dueno = (Dueno) getArguments().getSerializable("Dueno");
		if(dueno != null)
			miUser=dueno;
		else
			miUser = test.kate;
		
		final RWFile rw = new RWFile();
		rw.guardarDueno(test.kate);
		
		
		//***********************BASE DE DATOS

		//************************ANIMACION
		final Animation animFadein; 
		animFadein = AnimationUtils.loadAnimation(getActivity(),
				R.anim.bounce); 
		//************************ANIMACION
		
		botonCancelar = (Button) rootView.findViewById(R.id.cancelarMiCuenta);
		botonAceptar = (Button) rootView.findViewById(R.id.AceptarMiCuenta);
		TableRowMiCuenta = (TableRow) rootView.findViewById(R.id.TableRowMiCuenta);
		newtextNombre = (EditText) rootView.findViewById(R.id.NombreDueno);
		botonEditarPerfil = (Button) rootView.findViewById(R.id.ButtonEditarPerfil);
		newImagePerfilDueno = (ImageView) rootView.findViewById(R.id.ImageViewFotoDueno);
		newtextCiudad = (EditText) rootView.findViewById(R.id.textCiudad);
		//final Button botonAceptar = (Button) rootView.findViewById(R.id.button_aceptar);
		
		Nombre=miUser.getNick();
		Ciudad=miUser.getCiudad();
		
		System.out.println("Imagen:"+miUser.getImage().toString()+":");
		if(!miUser.getImage().toString().equals(""))
			try {
				newImagePerfilDueno.setImageBitmap(new Cache().cargarImagen(miUser.getImage(), getActivity().getBaseContext()));
			} catch (FileNotFoundException e) {
				newImagePerfilDueno.setImageResource(R.drawable.sombra_perro);
				e.printStackTrace();
			}
		newtextNombre.setText(miUser.getNick());
		newtextCiudad.setText(miUser.getCiudad());
		
		final OnClickListener ImagenClickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				PopupMenu popupMenu = new PopupMenu(getActivity(), view);
				popupMenu.setOnMenuItemClickListener((OnMenuItemClickListener) MiCuenta.this);
				popupMenu.inflate(R.layout.popup_genero_layout);
				popupMenu.show();
			}
		};

		
		originalKeyListener = newtextNombre.getKeyListener();
		originalKeyListenerCiudad = newtextCiudad.getKeyListener();

		newImagePerfilDueno.setOnClickListener(null);
		botonEditarPerfil.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
					newtextNombre.setKeyListener(originalKeyListener);
					newtextNombre.setFocusableInTouchMode(true);
					//newtextNombre.setBackgroundResource(android.R.drawable.editbox_dropdown_dark_frame);
					newtextCiudad.setKeyListener(originalKeyListenerCiudad);
					newtextCiudad.setFocusableInTouchMode(true);
					//newtextCiudad.setBackgroundResource(android.R.drawable.editbox_dropdown_dark_frame);
					newImagePerfilDueno.setOnClickListener(ImagenClickListener);
					newImagePerfilDueno.startAnimation(animFadein);
					//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_save);
					botonEditarPerfil.setVisibility(View.INVISIBLE);
					boton.setEnabled(false);
					boton.setVisibility(View.INVISIBLE);
					newtextNombre.startAnimation(animFadein);
					newtextCiudad.startAnimation(animFadein);
					TableRowMiCuenta.setVisibility(View.VISIBLE);
					
					misPerrosList.mostrarEliminar();
					
			}});


		botonAceptar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				newtextNombre.setFocusableInTouchMode(false);
				newtextNombre.setKeyListener(null);
				newtextNombre.setBackgroundColor(000000);
				newtextCiudad.setFocusableInTouchMode(false); 
				newtextCiudad.setKeyListener(null);
				newtextCiudad.setBackgroundColor(000000);
				newImagePerfilDueno.setOnClickListener(null);
				newImagePerfilDueno.clearAnimation();
				//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
				botonEditarPerfil.setVisibility(View.VISIBLE);
				boton.setEnabled(true);
				boton.setVisibility(View.VISIBLE);
				TableRowMiCuenta.setVisibility(View.INVISIBLE);
				newtextNombre.clearAnimation();
				newtextCiudad.clearAnimation();
				miUser.setNick(newtextNombre.getText().toString());
				miUser.setCiudad(newtextCiudad.getText().toString());
				rw.guardarDueno(miUser);
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
			    misPerrosList.esconderEliminar();
				//****************  Guardar datos en la base de DATOS ****************//
				//********************************************************************//
			}
	});

		
		botonCancelar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				newtextNombre.setFocusableInTouchMode(false);
				newtextNombre.setKeyListener(null);
				newtextNombre.setBackgroundColor(000000);
				newtextCiudad.setFocusableInTouchMode(false); 
				newtextCiudad.setKeyListener(null);
				newtextCiudad.setBackgroundColor(000000);
				newImagePerfilDueno.setOnClickListener(null);
				newImagePerfilDueno.clearAnimation();
				//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
				botonEditarPerfil.setVisibility(View.VISIBLE);
				boton.setEnabled(true);
				boton.setVisibility(View.VISIBLE);
				TableRowMiCuenta.setVisibility(View.INVISIBLE);
				newtextNombre.clearAnimation();
				newtextCiudad.clearAnimation();
				newtextNombre.setText(Nombre);
				newtextCiudad.setText(Ciudad);
				try {
					newImagePerfilDueno.setImageBitmap(new Cache().cargarImagen(miUser.getImage(), getActivity().getBaseContext()));
				} catch (FileNotFoundException e) {
					newImagePerfilDueno.setImageResource(R.drawable.sombra_persona);
					e.printStackTrace();
				}
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
			    misPerrosList.esconderEliminar();
			}
	});
		
		
		newtextNombre.setOnTouchListener(new OnTouchListener() {


			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				
				
				newtextNombre.clearAnimation();
				
				return false;
			}
		});

		newtextCiudad.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newtextCiudad.clearAnimation();
				return false;
			}
		});

		//RESETEO DE LOS FRAGMENT
		String [] iter = new String[] {TAB_CITAS,TAB_NUMBERS,TAB_PERFIL,TAB_WORDS};

		//mejor seria borrarlas al salir para liberar mem
		for( i=0;i<4;i++)
			if(fm.findFragmentByTag(iter[i])!=null){
				System.out.println("FRAGMENT encontrado");
				fm.beginTransaction().remove(fm.findFragmentByTag(iter[i])).commit();
				fm.popBackStack();
			}
		return rootView;
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab

		misPerrosList=new MisPerrosListFragment(TAB_PERFIL, miUser.getPerros());
		
		fm.beginTransaction()
		.replace(R.id.linearLayoutListViewPerros, misPerrosList, TAB_PERFIL)
		.commit();
		boton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i =new Intent(getActivity(), PerfilPerroPrivado.class);
				i.putExtra("isAnadirPerro",1);
				i.putExtra("Perro", (new Perro("nombre","macho", "", 99, null, "", "", null, "", "", 0, false, "", 0)));
				getActivity().startActivityForResult(i,ANADIR_PERRO_RESULT);
			}
		});

	}

	private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_PERFIL, R.string.tab_miperfil, R.id.tab1));
		mTabHost.addTab(newTab(TAB_WORDS, R.string.tab_words, R.id.tab2));
		mTabHost.addTab(newTab(TAB_NUMBERS, R.string.tab_numbers, R.id.tab3));
		mTabHost.addTab(newTab(TAB_CITAS, R.string.tab_citas, R.id.tab4));
		//mTabHost.addTab(newTab(TAB_MURO, R.string.tab_muro, R.id.tab5));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		Log.d(TAG, "buildTab(): tag=" + tag);

		View indicator = LayoutInflater.from(getActivity()).inflate(R.layout.tab,null);
		((TextView) indicator.findViewById(R.id.text)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_PERFIL.equals(tabId)) {
			async.updateTab(tabId, R.id.tab1);
			mCurrentTab = 0;
			newtextNombre.setFocusableInTouchMode(false);
			newtextNombre.setKeyListener(null);
			newtextNombre.setBackgroundColor(000000);
			newtextCiudad.setFocusableInTouchMode(false); 
			newtextCiudad.setKeyListener(null);
			newtextCiudad.setBackgroundColor(000000);
			newImagePerfilDueno.setOnClickListener(null);
			newImagePerfilDueno.clearAnimation();
			//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
			botonEditarPerfil.setVisibility(View.VISIBLE);
			boton.setEnabled(true);
			boton.setVisibility(View.VISIBLE);
			TableRowMiCuenta.setVisibility(View.INVISIBLE);
			newtextNombre.clearAnimation();
			newtextCiudad.clearAnimation();
			newtextNombre.setText(Nombre);
			newtextCiudad.setText(Ciudad);
			misPerrosList.esconderEliminar();
			boton.setText("Añadir Perro");
			boton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent i =new Intent(getActivity(), PerfilPerroPrivado.class);
					i.putExtra("isAnadirPerro",1);
					i.putExtra("Perro", (new Perro("nombre","macho", "", 99, null, "", "", null, "", "", 0, false, "", 0)));
					getActivity().startActivityForResult(i,ANADIR_PERRO_RESULT);
				}
			});


			return;
		}
		if (TAB_WORDS.equals(tabId)) {
			async.updateTab(tabId, R.id.tab2);
			mCurrentTab = 1;
			newtextNombre.setFocusableInTouchMode(false);
			newtextNombre.setKeyListener(null);
			newtextNombre.setBackgroundColor(000000);
			newtextCiudad.setFocusableInTouchMode(false); 
			newtextCiudad.setKeyListener(null);
			newtextCiudad.setBackgroundColor(000000);
			newImagePerfilDueno.setOnClickListener(null);
			newImagePerfilDueno.clearAnimation();
			//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
			botonEditarPerfil.setVisibility(View.VISIBLE);
			boton.setEnabled(true);
			boton.setVisibility(View.VISIBLE);
			TableRowMiCuenta.setVisibility(View.INVISIBLE);
			newtextNombre.clearAnimation();
			newtextCiudad.clearAnimation();
			newtextNombre.setText(Nombre);
			newtextCiudad.setText(Ciudad);
			misPerrosList.esconderEliminar();
			boton.setText("Editar");
			return;
		}
		if (TAB_NUMBERS.equals(tabId)) {
			async.updateTab(tabId, R.id.tab3);
			mCurrentTab = 2;
			newtextNombre.setFocusableInTouchMode(false);
			newtextNombre.setKeyListener(null);
			newtextNombre.setBackgroundColor(000000);
			newtextCiudad.setFocusableInTouchMode(false); 
			newtextCiudad.setKeyListener(null);
			newtextCiudad.setBackgroundColor(000000);
			newImagePerfilDueno.setOnClickListener(null);
			newImagePerfilDueno.clearAnimation();
			//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
			botonEditarPerfil.setVisibility(View.VISIBLE);
			boton.setEnabled(true);
			boton.setVisibility(View.VISIBLE);
			TableRowMiCuenta.setVisibility(View.INVISIBLE);
			newtextNombre.clearAnimation();
			newtextCiudad.clearAnimation();
			newtextNombre.setText(Nombre);
			newtextCiudad.setText(Ciudad);
			misPerrosList.esconderEliminar();
			boton.setText("Añadir Parque");
			boton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					//Perro defecto = new Perro("Nombre", "100", 2, Raza.Raza, "Escribe sobre sus aficiones", "Escribe sobre su personalidad", new Date[]{new Date(1920, 4, 20),new Date(1990, 9, 10)}, "Paridiocardialisis", "Pal cor");
					startActivity(new Intent(getActivity(), PerfilParque.class));
					//Intent i =new Intent(getActivity(), PerfilParque.class);
					//i.putExtra("Perro", defecto);
					//startActivity(i);

				}
			});
			return;
		}
		if (TAB_CITAS.equals(tabId)) {
			async.updateTab(tabId, R.id.tab4);
			boton.setText("Buscar Cita");
			mCurrentTab = 3;
			newtextNombre.setFocusableInTouchMode(false);
			newtextNombre.setKeyListener(null);
			newtextNombre.setBackgroundColor(000000);
			newtextCiudad.setFocusableInTouchMode(false); 
			newtextCiudad.setKeyListener(null);
			newtextCiudad.setBackgroundColor(000000);
			newImagePerfilDueno.setOnClickListener(null);
			newImagePerfilDueno.clearAnimation();
			//botonEditarPerfil.setBackgroundResource(R.drawable.ic_action_edit);
			botonEditarPerfil.setVisibility(View.VISIBLE);
			boton.setEnabled(true);
			boton.setVisibility(View.VISIBLE);
			TableRowMiCuenta.setVisibility(View.INVISIBLE);
			newtextNombre.clearAnimation();
			newtextCiudad.clearAnimation();
			newtextNombre.setText(Nombre);
			newtextCiudad.setText(Ciudad);
			misPerrosList.esconderEliminar();
			boton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					
					
				}
			});
			return;
		}
		/*if (TAB_MURO.equals(tabId)) {
			async.updateTab(tabId, R.id.tab5);
			mCurrentTab = 4;
			boton.setText("Muro");
			return;
		}*/
	}
	
	
	

	
	private class AsyncCaller extends AsyncTask<Void, Void, Void>
	{
		private String tabId;
		private int placeholder;

		private void updateTab(String tabId, int placeholder) {
			this.tabId=tabId;
			this.placeholder=placeholder;

			doInBackground();

		}


		@Override
		protected Void doInBackground(Void... params) {
			if (fm.findFragmentByTag(tabId) == null) {
				if (TAB_PERFIL.equals(tabId)) {
					fm.beginTransaction()
					.replace(placeholder, new MisPerrosListFragment(tabId,miUser.getPerros()), tabId)
					.commit();
				}
				if (TAB_WORDS.equals(tabId)) {
					fm.beginTransaction()
					.replace(placeholder, new Muro(tabId), tabId)
					.commit();
				}
				else if  (TAB_NUMBERS.equals(tabId)) {
					fm.beginTransaction()
					.replace(placeholder, new ParquesListFragment(tabId,miUser.getParques()), tabId)
					.commit();
				}

				else if  (TAB_CITAS.equals(tabId)) {

					//System.out.println("FRAGMENT replace citas");
					fm.beginTransaction()
					.replace(placeholder,new CitasListFragment(tabId,miUser.getCitas()), tabId)
					.commit();

				}
				else System.out.println("No existe "+tabId);
			}		return null;
		}

	}

	public boolean onMenuItemClick(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.item_comedy:
			Intent cameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getActivity().startActivityForResult(cameraInt, FOTO_CAMARA);  
			return true;
		case R.id.item_movies:
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			getActivity().startActivityForResult(i, FOTO_GALERIA);
			return true;
		default:
			return false;
		}

	}



	
	
	public void anadirPerro(Perro p){
		misPerrosList.anadirPerro(p);
		
	}
	

	public void modificarPerro(Perro p,int pos) {
		misPerrosList.modificarPerro(p,pos);
		
	}


}



