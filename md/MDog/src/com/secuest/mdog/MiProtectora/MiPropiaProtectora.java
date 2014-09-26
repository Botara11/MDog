package com.secuest.mdog.MiProtectora;
//Perfil de la perso
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.PerroProtect;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.Perfiles.PerfilPerroPrivado;
import com.secuest.mdog.Perfiles.PerfilProtectoraPrivado;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;




public class MiPropiaProtectora extends Fragment  implements OnTabChangeListener, OnMenuItemClickListener {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_PERFIL = "Perfil";
	public static String TAB_PERROS = "Perros";
	public static final String TAB_PARQUES = "Parques";
	public static final String TAB_MURO = "Muro";
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private TabHost tabHost;
	private ArrayList<String> mItems;
	private ArrayList<PerroProtect> perrosProtect ;
	private View rootView;
	private KeyListener originalKeyListenerNombre;
	private KeyListener originalKeyListenerCiudad;
	private KeyListener originalKeyListenerWeb;
	private KeyListener originalKeyListenerTelefono;
	private KeyListener originalKeyListenerCorreo;
	private KeyListener originalKeyListenerDireccion;
	private KeyListener originalKeyListenerImagenProtectoraPrivado;
	private int mCurrentTab=0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public MiPropiaProtectora(){}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.perfil_protectora_privado_layout, null);

		ListView list = (ListView)rootView.findViewById(R.id.ListViewPerrosProtectoraPrivado);
		final EditText direccion = (EditText) rootView.findViewById(R.id.DireccionProtectoraPrivado);
		final EditText nombre = (EditText) rootView.findViewById(R.id.NombreProtectoraPrivado);
		final EditText ciudad = (EditText) rootView.findViewById(R.id.CiudadProtectoraPrivado);
		final EditText telefono = (EditText)rootView.findViewById(R.id.TelefonoProtectoraPrivado);
		final EditText email = (EditText)rootView.findViewById(R.id.CorreoProtectoraPrivado);
		final EditText direccWeb = (EditText) rootView.findViewById(R.id.WebProtectoraPrivado);
		final ImageView ImagenProtectora = (ImageView) rootView.findViewById(R.id.ImageViewFotoProtectoraPrivado); 

		final Button botonAnadirPerro = (Button) rootView.findViewById(R.id.button_prinProtectoraPrivado);
		final Button botonEditar = (Button) rootView.findViewById(R.id.BotonEditarPerfilProtectoraPrivado);
		final Button botonCancelar = (Button) rootView.findViewById(R.id.cancelarProtectoraPrivado);
		final Button botonAceptar = (Button) rootView.findViewById(R.id.AceptarProtectoraPrivado);
		final TableRow TableRowProtectoraPrivado = (TableRow) rootView.findViewById(R.id.TableRowProtectoraPrivado);



		botonAnadirPerro.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i =new Intent(getActivity(), PerfilPerroPrivado.class);
				i.putExtra("Perro", (new Perro("nombre","macho", "", 99, null, "", "", null, "", "", 0, false, "", 0)));
				startActivity(i);
			}
		});




		final Animation animFadein;
		animFadein = AnimationUtils.loadAnimation(getActivity(),
				R.anim.bounce); 

		originalKeyListenerDireccion = direccion.getKeyListener();
		originalKeyListenerTelefono = telefono.getKeyListener();
		originalKeyListenerWeb = direccWeb.getKeyListener();
		originalKeyListenerCorreo = email.getKeyListener();
		originalKeyListenerNombre = nombre.getKeyListener();
		originalKeyListenerCiudad = ciudad.getKeyListener();

		final OnClickListener ImagenClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				PopupMenu popupMenu = new PopupMenu(getActivity(), view);
				popupMenu.setOnMenuItemClickListener((OnMenuItemClickListener) MiPropiaProtectora.this);
				popupMenu.inflate(R.layout.popup_genero_layout);
				popupMenu.show();
			}
		};



		botonEditar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {



				//newTextEdad.setKeyListener(originalKeyListener);
				//newTextEdad.setFocusable(true);
				direccion.setKeyListener(originalKeyListenerDireccion);
				direccion.setFocusableInTouchMode(true);
				direccion.startAnimation(animFadein);
				telefono.setKeyListener(originalKeyListenerTelefono);
				telefono.setFocusableInTouchMode(true);
				telefono.startAnimation(animFadein);
				direccWeb.setKeyListener(originalKeyListenerWeb);
				direccWeb.setFocusableInTouchMode(true);
				direccWeb.startAnimation(animFadein);
				email.setKeyListener(originalKeyListenerCorreo);
				email.setFocusableInTouchMode(true);
				email.startAnimation(animFadein);
				nombre.setKeyListener(originalKeyListenerNombre);
				nombre.setFocusableInTouchMode(true);
				nombre.startAnimation(animFadein);
				ciudad.setKeyListener(originalKeyListenerCiudad);
				ciudad.setFocusableInTouchMode(true);
				ciudad.startAnimation(animFadein);
				ImagenProtectora.setOnClickListener(ImagenClickListener);
				ImagenProtectora.startAnimation(animFadein);
				botonEditar.setVisibility(View.INVISIBLE);
				TableRowProtectoraPrivado.setVisibility(View.VISIBLE);


			}



		});

		botonAceptar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {


				direccion.setKeyListener(null);
				direccion.setFocusableInTouchMode(false);
				direccion.clearAnimation();
				telefono.setKeyListener(null);
				telefono.setFocusableInTouchMode(false);
				telefono.clearAnimation();
				direccWeb.setKeyListener(null);
				direccWeb.setFocusableInTouchMode(false);
				direccWeb.clearAnimation();
				email.setKeyListener(null);
				email.setFocusableInTouchMode(false);
				email.clearAnimation();
				nombre.setKeyListener(null);
				nombre.setFocusableInTouchMode(false);
				nombre.clearAnimation();
				ciudad.setKeyListener(null);
				ciudad.setFocusableInTouchMode(false);
				ciudad.clearAnimation();
				ImagenProtectora.setOnClickListener(ImagenClickListener);
				ImagenProtectora.clearAnimation();
				botonEditar.setVisibility(View.VISIBLE);
				TableRowProtectoraPrivado.setVisibility(View.INVISIBLE);
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

				//****************  Guardar datos en la base de DATOS ****************//
				//********************************************************************//
			}


		});

		botonCancelar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {


				direccion.setKeyListener(null);
				direccion.setFocusableInTouchMode(false);
				direccion.clearAnimation();
				telefono.setKeyListener(null);
				telefono.setFocusableInTouchMode(false);
				telefono.clearAnimation();
				direccWeb.setKeyListener(null);
				direccWeb.setFocusableInTouchMode(false);
				direccWeb.clearAnimation();
				email.setKeyListener(null);
				email.setFocusableInTouchMode(false);
				email.clearAnimation();
				nombre.setKeyListener(null);
				nombre.setFocusableInTouchMode(false);
				nombre.clearAnimation();
				ciudad.setKeyListener(null);
				ciudad.setFocusableInTouchMode(false);
				ciudad.clearAnimation();
				ImagenProtectora.setOnClickListener(ImagenClickListener);
				ImagenProtectora.clearAnimation();
				botonEditar.setVisibility(View.VISIBLE);
				TableRowProtectoraPrivado.setVisibility(View.INVISIBLE);
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

				//****************  Guardar datos en la base de DATOS ****************//
				//********************************************************************//
			}


		});


		nombre.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				nombre.clearAnimation();
				return false;
			}
		});

		ciudad.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ciudad.clearAnimation();
				return false;
			}
		});

		direccion.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				direccion.clearAnimation();
				return false;
			}
		});

		telefono.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				telefono.clearAnimation();
				return false;
			}
		});

		direccWeb.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				direccWeb.clearAnimation();
				return false;
			}
		});

		email.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				email.clearAnimation();
				return false;
			}
		});



		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		/**** BASE DE DATOS **********/
		ArrayList<PerroProtect> perrosProtect;
		Protectora protectora = (Protectora) getArguments().getSerializable("Protectora");
		perrosProtect = protectora.getPerros();
		/**** BASE DE DATOS **********/

		ImagenProtectora.setImageDrawable(getResources().getDrawable(R.drawable.sombra_persona));
		nombre.setText(protectora.getNombre());
		ciudad.setText(protectora.getCiudad());
		direccion.setText(protectora.getDireccion());
		if(protectora.getTelefono()==0)
			telefono.setText("");
		else
			telefono.setText(protectora.getTelefono()+"");
		email.setText(protectora.getEmail());
		direccWeb.setText(protectora.getPaginaWeb());
		direccWeb.setMaxLines(2);


		for(int i=0;i<perrosProtect.size();i++)
			datos.add(new Lista_entrada(perrosProtect.get(i).getImage(), 2, new String[]{ perrosProtect.get(i).getNombre(), perrosProtect.get(i).getRaza().toString()}));


		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(getActivity(), R.layout.list_item_amigos, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textcentrado); 
						if (texto_superior_entrada != null) 
							texto_superior_entrada.setText(((Lista_entrada) entrada).get_texto(0)); 

						TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textinferior); 
						if (texto_inferior_entrada != null)
							texto_inferior_entrada.setText(((Lista_entrada) entrada).get_texto(1)); 

						String nom = ((Lista_entrada) entrada).getNombreImage();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.image); 
						if (imagen_entrada != null)
							try {
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getActivity().getBaseContext()));
							} catch (FileNotFoundException e) {
								imagen_entrada.setImageResource(R.drawable.sombra_perro);
								e.printStackTrace();
							}
					}
				}
			};



		}

		list.setAdapter(mAdapter);

		Resources res = getResources(); // Resource object to get Drawables
		tabHost=(TabHost)rootView.findViewById(R.id.TabHostProtectoraPrivado);
		tabHost.setup();
		
		View perros = LayoutInflater.from(getActivity()).inflate(R.layout.tab,null);
		((TextView) perros.findViewById(R.id.text)).setText("Perros");
		
		View perfil = LayoutInflater.from(getActivity()).inflate(R.layout.tab,null);
		((TextView) perfil.findViewById(R.id.text)).setText("Perfil");

		TabSpec spec1=tabHost.newTabSpec("TAB 1");
		spec1.setContent(R.id.tabperrosprotectoraPrivado);
		spec1.setIndicator(perros);


		TabSpec spec2=tabHost.newTabSpec("TAB 2");
		spec2.setIndicator(perfil);
		spec2.setContent(R.id.tabperfilprotectoraPrivado);



		tabHost.addTab(spec1);
		tabHost.addTab(spec2);

		tabHost.setOnTabChangedListener(new OnTabChangeListener()
		{
			@Override
			public void onTabChanged(String tabId)
			{
				if (mCurrentTab == 0) {
					botonAnadirPerro.setVisibility(View.INVISIBLE);
					mCurrentTab=1;


				}
				else if(mCurrentTab == 1){
					botonAnadirPerro.setVisibility(View.VISIBLE);
					mCurrentTab=0;

				}
			}
		});




		return rootView;





	}


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

	}

}