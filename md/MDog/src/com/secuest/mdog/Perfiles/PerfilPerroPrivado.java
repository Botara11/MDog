package com.secuest.mdog.Perfiles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;

import com.secuest.mdog.R;
import com.secuest.mdog.BuscarPerrosCerca.BusqRazas;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.utils.Cache;

import android.text.method.KeyListener;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


public class PerfilPerroPrivado extends Activity  implements OnMenuItemClickListener {
	private static final int BUSQ_RAZA = 1;
	protected static final int FOTO_CAMARA = 2;
	protected static final int RECORTAR = 3;
	private static final int FOTO_GALERIA = 4;
	boolean showingfirst=true;
	boolean IS_ANADIR_PERRO = false;
	private KeyListener originalKeyListener;
	private KeyListener originalKeyListenerAficiones;
	private KeyListener originalKeyListenerPersonalidad;
	private KeyListener originalKeyListenerFechaUltVac;
	private KeyListener originalKeyListenerTipoUltVac;
	private KeyListener originalKeyListenerFechaProxVac;
	private KeyListener originalKeyListenerTipoProxVac;
	private KeyListener originalKeyListenerAnti;
	private KeyListener originalKeyListenerChip;
	private Perro perro;
	private EditText newtextRaza;
	private String nombreImagenPerfil="";
	private String genero = "macho";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfilperro_privado_layout);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();

		if(getIntent().getExtras().getInt("isAnadirPerro")==1)
			IS_ANADIR_PERRO = true;
		View perfil = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) perfil.findViewById(R.id.text)).setText("Perfil");
		
		View cartilla = LayoutInflater.from(this).inflate(R.layout.tab,null);
		((TextView) cartilla.findViewById(R.id.text)).setText("Cartilla");
		

		TabSpec spec1=tabHost.newTabSpec("TAB 1");
		spec1.setContent(R.id.tabperfilperroprivado);
		spec1.setIndicator(perfil);


		TabSpec spec2=tabHost.newTabSpec("TAB 2");
		spec2.setIndicator(cartilla);
		spec2.setContent(R.id.tabcartilla);


		final Animation animFadein;
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.bounce);    

		final EditText newtextNombre = (EditText) findViewById(R.id.Nombre);
		final EditText newtextAficiones = (EditText) findViewById(R.id.Aficiones);
		final EditText newtextPersonalidad = (EditText) findViewById(R.id.Personalidad);
		newtextRaza = (EditText) findViewById(R.id.textRaza);
		final Button boton1 = (Button)findViewById(R.id.buttonEditar);
		final Button boton2 = (Button)findViewById(R.id.buttonEditarCartilla);
		final EditText newTextEdad = (EditText) findViewById(R.id.textEdad);
		final ImageView newTextGenero = (ImageView) findViewById(R.id.genero); 
		final EditText newTextAdorable = (EditText) findViewById(R.id.NumeroAdorables);
		final ImageView newImagePerfilPerro = (ImageView) findViewById(R.id.ImageViewFotoPeRRo);

		final EditText newTextFechaUltVac = (EditText) findViewById(R.id.editTextfechaultvac);
		final EditText newTextTipoUltVac = (EditText) findViewById(R.id.EditTexttipoultvac);
		final EditText newTextFechaProxVac = (EditText) findViewById(R.id.EditTextfechaproxvac);
		final EditText newTextTipoProxVac = (EditText) findViewById(R.id.EditTexttipoproxvac);
		final EditText newTextAnti = (EditText) findViewById(R.id.EditTextfechaproxanti);
		final EditText newTextChip = (EditText) findViewById(R.id.EditTextnumchip);

		final Button botonCancelar = (Button) findViewById(R.id.cancelarPerroPrivado);
		final Button botonAceptar = (Button) findViewById(R.id.AceptarPerroPrivado);
		final TableRow TableRowPerroPrivado = (TableRow) findViewById(R.id.TableRowPerroPrivado);
		
		final Button botonCancelarCartilla = (Button) findViewById(R.id.cancelarPerroPrivadoCartilla);
		final Button botonAceptarCartilla = (Button) findViewById(R.id.AceptarPerroPrivadoCartilla);
		final TableRow TableRowPerroPrivadoCartilla = (TableRow) findViewById(R.id.TableRowPerroPrivadoCartilla);
		
		
		


		final LinearLayout Linearaceptarcancelar = (LinearLayout) findViewById(R.id.linearaceptarcancelar);
		final LinearLayout Linearaceptarcancelarcartilla = (LinearLayout) findViewById(R.id.linearaceptarcancelarcartilla);
		//final RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) Linearaceptarcancelar.getLayoutParams();
		//params.height = 50;
		//final RelativeLayout.LayoutParams params1 = (android.widget.RelativeLayout.LayoutParams) Linearaceptarcancelar.getLayoutParams();
		//params1.height=0;
		final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 75);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		final RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);





		/*
		 * 
		 * editTextfechaultvac    EditTexttipoultvac
		 * 
		 * 
		 * 
		 */

		final OnClickListener ImagenClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				PopupMenu popupMenu = new PopupMenu(PerfilPerroPrivado.this, view);
				popupMenu.setOnMenuItemClickListener(PerfilPerroPrivado.this);
				popupMenu.inflate(R.layout.popup_genero_layout);
				popupMenu.show();
			}
		};

		final OnClickListener EdadClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				PopupMenu popup = new PopupMenu(PerfilPerroPrivado.this, view);
				popup.setOnMenuItemClickListener(PerfilPerroPrivado.this);
				popup.inflate(R.layout.popup_edad_layout);
				popup.show();
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
					public boolean onMenuItemClick(MenuItem item) {  
						newTextEdad.setText(item.getTitle());
						return true;  
					}  
				});  
			}
		};

		final OnClickListener GeneroClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				PopupMenu pop = new PopupMenu(PerfilPerroPrivado.this, view);
				pop.setOnMenuItemClickListener(PerfilPerroPrivado.this);
				pop.inflate(R.layout.popup_layout_genero);
				pop.show();
				pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
					public boolean onMenuItemClick(MenuItem item) {  

						switch (item.getItemId()) {

						case R.id.item_macho:
							newTextGenero.setBackgroundResource(R.drawable.macho);
							genero="macho";
							return true;
						case R.id.item_hembra:
							newTextGenero.setBackgroundResource(R.drawable.hembra);
							genero="hembra";
							return true;
						default:
							return false;
						}


					}  
				});  


			}
		};





		perro = (Perro) getIntent().getSerializableExtra("Perro");
		if(perro.getGenero().equals("macho")){
			newTextGenero.setBackgroundResource(R.drawable.macho);
			genero= "macho";
		}else{
			newTextGenero.setBackgroundResource(R.drawable.hembra);
			genero= "hembra";
		}
		if(perro.getImage().equals(""))
			newImagePerfilPerro.setImageDrawable(getResources().getDrawable(R.drawable.sombra_perro));
		else
			try {
				newImagePerfilPerro.setImageBitmap((new Cache()).cargarImagen(perro.getImage(),getBaseContext()));
			} catch (FileNotFoundException e) {
				newImagePerfilPerro.setImageResource(R.drawable.sombra_perro);
				e.printStackTrace();
			}
		newtextNombre.setText(perro.getNombre());
		newtextAficiones.setText(perro.getAficiones());
		newtextPersonalidad.setText(perro.getPersonalidad());
		if(perro.getRaza()!=null)
			newtextRaza.setText(perro.getRaza().toString());
		else
			newtextRaza.setText("raza");
		if(perro.getEdad()!=99)
			newTextEdad.setText(perro.getEdad()+" a�os");
		newTextAdorable.setText(perro.getAdorabilidad()+"");  //Esta por ver lo de el nivel de adorabilidad
		if(perro.getFechas()!=null){
			newTextFechaUltVac.setText(perro.getFechas()[0].get(GregorianCalendar.DAY_OF_MONTH)+"/"+perro.getFechas()[0].get(GregorianCalendar.MONTH)+"/"+perro.getFechas()[0].get(GregorianCalendar.YEAR));
			newTextFechaProxVac.setText(perro.getFechas()[1].get(GregorianCalendar.DAY_OF_MONTH)+"/"+perro.getFechas()[1].get(GregorianCalendar.MONTH)+"/"+perro.getFechas()[1].get(GregorianCalendar.YEAR));
			newTextAnti.setText(perro.getFechas()[2].get(GregorianCalendar.DAY_OF_MONTH)+"/"+perro.getFechas()[2].get(GregorianCalendar.MONTH)+"/"+perro.getFechas()[2].get(GregorianCalendar.YEAR));
		}else{
			newTextFechaUltVac.setText("");newTextFechaProxVac.setText("");newTextAnti.setText("");
		}
		newTextTipoUltVac.setText(perro.getLastVacuna());
		newTextTipoProxVac.setText(perro.getNextVacuna());

		newTextChip.setText(perro.getNumChip());

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);


		final OnClickListener originalClickListener = new OnClickListener() {


			public void onClick(final View v) {
				//Inform the user the button has been clicked
				Intent i = new Intent(PerfilPerroPrivado.this, BusqRazas.class);
				startActivityForResult(i, BUSQ_RAZA);			}
		};





		originalKeyListener = newtextNombre.getKeyListener();
		originalKeyListenerAficiones = newtextAficiones.getKeyListener();
		originalKeyListenerPersonalidad = newtextPersonalidad.getKeyListener();
		originalKeyListenerFechaUltVac= newTextFechaUltVac.getKeyListener();
		originalKeyListenerTipoUltVac = newTextTipoUltVac.getKeyListener();
		originalKeyListenerFechaProxVac= newTextFechaProxVac.getKeyListener();
		originalKeyListenerTipoProxVac= newTextTipoProxVac.getKeyListener();
		originalKeyListenerAnti= newTextAnti.getKeyListener();
		originalKeyListenerChip= newTextChip.getKeyListener();

		boolean defaultValue= false;
		boolean yourValue =getIntent().getBooleanExtra("Anadir", defaultValue);

		if (yourValue==true || IS_ANADIR_PERRO){


			newtextNombre.setKeyListener(originalKeyListener);
			newtextNombre.setFocusableInTouchMode(true);
			newtextNombre.startAnimation(animFadein);
			newtextAficiones.setKeyListener(originalKeyListenerAficiones);
			newtextAficiones.setFocusableInTouchMode(true);
			newtextAficiones.clearFocus();
			newtextAficiones.startAnimation(animFadein);
			newtextPersonalidad.setKeyListener(originalKeyListenerPersonalidad);
			newtextPersonalidad.startAnimation(animFadein);
			newtextPersonalidad.setFocusableInTouchMode(true);
			newtextRaza.setOnClickListener(originalClickListener);
			newtextRaza.startAnimation(animFadein);
			newImagePerfilPerro.setOnClickListener(ImagenClickListener);
			newImagePerfilPerro.startAnimation(animFadein);
			newTextEdad.setOnClickListener(EdadClickListener);
			newTextEdad.startAnimation(animFadein);
			newTextGenero.setOnClickListener(GeneroClickListener);
			//newTextGenero.setRepeatCount(animFadein.INFINITE);
			newTextGenero.startAnimation(animFadein);
			//boton1.setBackgroundResource(R.drawable.ic_action_save);
			boton1.setVisibility(View.INVISIBLE);
			TableRowPerroPrivado.setVisibility(View.VISIBLE);
			Linearaceptarcancelar.setLayoutParams(params);
			System.out.println("Altura: "+params.height);


		} else{


			newtextNombre.setFocusableInTouchMode(false);                    
			newtextNombre.setKeyListener(null);
			newtextNombre.clearAnimation();
			newtextAficiones.setFocusableInTouchMode(false);                    
			newtextAficiones.setKeyListener(null);
			newtextAficiones.clearAnimation();
			newtextPersonalidad.setFocusableInTouchMode(false);                    
			newtextPersonalidad.setKeyListener(null);
			newtextPersonalidad.clearAnimation();
			newtextRaza.setOnClickListener(null);
			newtextRaza.setClickable(false);
			newtextRaza.clearAnimation();
			newImagePerfilPerro.setOnClickListener(null);
			newImagePerfilPerro.clearAnimation();
			newTextEdad.setOnClickListener(null);
			newTextEdad.clearAnimation();
			newTextGenero.setOnClickListener(null);
			newTextGenero.clearAnimation();
			String data=newtextNombre.getText().toString();
			newtextNombre.setText(data);
			//boton1.setBackgroundResource(R.drawable.ic_action_edit);
			boton1.setVisibility(View.VISIBLE);
			TableRowPerroPrivado.setVisibility(View.INVISIBLE);
			Linearaceptarcancelar.setLayoutParams(params1);
			InputMethodManager imm = (InputMethodManager)getSystemService(
					Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(newtextNombre.getWindowToken(), 0);

		}


		boton1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {



				//newTextEdad.setKeyListener(originalKeyListener);
				//newTextEdad.setFocusable(true);
				newtextNombre.setKeyListener(originalKeyListener);
				newtextNombre.setFocusableInTouchMode(true);
				newtextNombre.startAnimation(animFadein);
				newtextAficiones.setKeyListener(originalKeyListenerAficiones);
				newtextAficiones.setFocusableInTouchMode(true);
				newtextAficiones.startAnimation(animFadein);
				newtextPersonalidad.setKeyListener(originalKeyListenerPersonalidad);
				newtextPersonalidad.startAnimation(animFadein);
				newtextPersonalidad.setFocusableInTouchMode(true);
				newtextRaza.setOnClickListener(originalClickListener);
				newtextRaza.startAnimation(animFadein);
				newImagePerfilPerro.setOnClickListener(ImagenClickListener);
				newImagePerfilPerro.startAnimation(animFadein);
				newTextEdad.setOnClickListener(EdadClickListener);
				newTextEdad.startAnimation(animFadein);
				newTextGenero.setOnClickListener(GeneroClickListener);
				//newTextGenero.setRepeatCount(animFadein.INFINITE);
				newTextGenero.startAnimation(animFadein);
				//boton1.setBackgroundResource(R.drawable.ic_action_save);
				boton1.setVisibility(View.INVISIBLE);
				TableRowPerroPrivado.setVisibility(View.VISIBLE);
				Linearaceptarcancelar.setLayoutParams(params);
				System.out.println("Altura: "+params.height);


			}



		});

		botonAceptar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(newtextRaza.getText().toString().equals("raza")){
					Toast toast = Toast.makeText(getApplication(), "Seleccione una RAZA", Toast.LENGTH_SHORT);
					toast.show();
				}
				else{
				Perro p= new Perro(newtextNombre.getText().toString(),genero, nombreImagenPerfil, 0/*Integer.parseInt(newTextEdad.getText().toString().replace(" a�os", ""))*/,
						Raza.valueOf(newtextRaza.getText().toString()), newtextAficiones.getText().toString(), newtextPersonalidad.getText().toString(), 
				/*fechas=null  hacer adopcion y edad*/		null, newTextTipoUltVac.getText().toString(), newTextTipoProxVac.getText().toString(),
					0, false, newTextChip.getText().toString(), 0.0);
				if(IS_ANADIR_PERRO){
					Intent returnIntent = new Intent();
					returnIntent.putExtra("Perro",p);
					setResult(RESULT_OK,returnIntent);	
					finish();
				}
				else{
					//newTextEdad.setKeyListener(null);
					//newTextEdad.setFocusable(false);
					
					Intent returnIntent = new Intent();
					int pos = getIntent().getExtras().getInt("Posicion");
					returnIntent.putExtra("Posicion", pos);
					returnIntent.putExtra("Perro",p);
					setResult(RESULT_OK,returnIntent);	
					
					newtextNombre.setFocusableInTouchMode(false);                    
					newtextNombre.setKeyListener(null);
					newtextNombre.clearAnimation();
					newtextAficiones.setFocusableInTouchMode(false);                    
					newtextAficiones.setKeyListener(null);
					newtextAficiones.clearAnimation();
					newtextPersonalidad.setFocusableInTouchMode(false);                    
					newtextPersonalidad.setKeyListener(null);
					newtextPersonalidad.clearAnimation();
					newtextRaza.setOnClickListener(null);
					newtextRaza.setClickable(false);
					newtextRaza.clearAnimation();
					newImagePerfilPerro.setOnClickListener(null);
					newImagePerfilPerro.clearAnimation();
					newTextEdad.setOnClickListener(null);
					newTextEdad.clearAnimation();
					newTextGenero.setOnClickListener(null);
					newTextGenero.clearAnimation();
					String data=newtextNombre.getText().toString();
					newtextNombre.setText(data);
					//boton1.setBackgroundResource(R.drawable.ic_action_edit);
					boton1.setVisibility(View.VISIBLE);
					TableRowPerroPrivado.setVisibility(View.INVISIBLE);
					Linearaceptarcancelar.setLayoutParams(params1);
					InputMethodManager imm = (InputMethodManager)getSystemService(
							Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(newtextNombre.getWindowToken(), 0);
				}

				//****************  Guardar datos en la base de DATOS ****************//
				//********************************************************************//
			}
			}

		});



		botonCancelar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(IS_ANADIR_PERRO){
					finish();
				}
				else{
					//newTextEdad.setKeyListener(null);
					//newTextEdad.setFocusable(false);
					newtextNombre.setFocusableInTouchMode(false);                    
					newtextNombre.setKeyListener(null);
					newtextNombre.clearAnimation();
					newtextAficiones.setFocusableInTouchMode(false);                    
					newtextAficiones.setKeyListener(null);
					newtextAficiones.clearAnimation();
					newtextPersonalidad.setFocusableInTouchMode(false);                    
					newtextPersonalidad.setKeyListener(null);
					newtextPersonalidad.clearAnimation();
					newtextRaza.setOnClickListener(null);
					newtextRaza.setClickable(false);
					newtextRaza.clearAnimation();
					newImagePerfilPerro.setOnClickListener(null);
					newImagePerfilPerro.clearAnimation();
					newTextEdad.setOnClickListener(null);
					newTextEdad.clearAnimation();
					newTextGenero.setOnClickListener(null);
					newTextGenero.clearAnimation();
					String data=newtextNombre.getText().toString();
					newtextNombre.setText(data);
					//boton1.setBackgroundResource(R.drawable.ic_action_edit);
					boton1.setVisibility(View.VISIBLE);
					TableRowPerroPrivado.setVisibility(View.INVISIBLE);
					Linearaceptarcancelar.setLayoutParams(params1);
					InputMethodManager imm = (InputMethodManager)getSystemService(
							Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(newtextNombre.getWindowToken(), 0);

				}
			}


		});

		newtextPersonalidad.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newtextPersonalidad.clearAnimation();
				return false;
			}
		});
		
		newtextAficiones.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newtextAficiones.clearAnimation();
				return false;
			}
		});
		
		newtextNombre.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newtextNombre.clearAnimation();
				return false;
			}
		});
		
		newTextGenero.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newTextGenero.clearAnimation();
				return false;
			}
		});
		
		newTextEdad.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newTextEdad.clearAnimation();
				return false;
			}
		});
		
		newtextRaza.setOnTouchListener (new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				newtextRaza.clearAnimation();
				return false;
			}
		});
		

		boton2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				

					//newTextEdad.setKeyListener(originalKeyListener);
					//newTextEdad.setFocusable(true);
					newTextFechaUltVac.setKeyListener(originalKeyListenerFechaUltVac);
					newTextFechaUltVac.setFocusableInTouchMode(true);
					newTextTipoUltVac.setKeyListener(originalKeyListenerTipoUltVac);
					newTextTipoUltVac.setFocusableInTouchMode(true);
					newTextFechaProxVac.setKeyListener(originalKeyListenerFechaProxVac);
					newTextFechaProxVac.setFocusableInTouchMode(true);
					newTextTipoProxVac.setKeyListener(originalKeyListenerTipoProxVac);
					newTextTipoProxVac.setFocusableInTouchMode(true);
					newTextAnti.setKeyListener(originalKeyListenerAnti);
					newTextAnti.setFocusableInTouchMode(true);
					newTextChip.setKeyListener(originalKeyListenerChip);
					newTextChip.setFocusableInTouchMode(true);
					TableRowPerroPrivadoCartilla.setVisibility(View.VISIBLE);
					Linearaceptarcancelarcartilla.setLayoutParams(params);
					//boton2.setBackgroundResource(R.drawable.ic_action_save);
					boton2.setVisibility(View.INVISIBLE);

					
									

			}

		});
		
		
		botonAceptarCartilla.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				newTextFechaUltVac.setKeyListener(null);
				newTextFechaUltVac.setFocusableInTouchMode(false);
				newTextTipoUltVac.setKeyListener(null);
				newTextTipoUltVac.setFocusableInTouchMode(false);
				newTextFechaProxVac.setKeyListener(null);
				newTextFechaProxVac.setFocusableInTouchMode(false);
				newTextTipoProxVac.setKeyListener(null);
				newTextTipoProxVac.setFocusableInTouchMode(false);
				newTextAnti.setKeyListener(null);
				newTextAnti.setFocusableInTouchMode(false);
				newTextChip.setKeyListener(null);
				newTextChip.setFocusableInTouchMode(false);
				//boton2.setBackgroundResource(R.drawable.ic_action_edit);
				boton2.setVisibility(View.VISIBLE);
				TableRowPerroPrivadoCartilla.setVisibility(View.INVISIBLE);
				Linearaceptarcancelarcartilla.setLayoutParams(params1);
				showingfirst = true;

				
			}


		});
		
		
		
		botonCancelarCartilla.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				newTextFechaUltVac.setKeyListener(null);
				newTextFechaUltVac.setFocusableInTouchMode(false);
				newTextTipoUltVac.setKeyListener(null);
				newTextTipoUltVac.setFocusableInTouchMode(false);
				newTextFechaProxVac.setKeyListener(null);
				newTextFechaProxVac.setFocusableInTouchMode(false);
				newTextTipoProxVac.setKeyListener(null);
				newTextTipoProxVac.setFocusableInTouchMode(false);
				newTextAnti.setKeyListener(null);
				newTextAnti.setFocusableInTouchMode(false);
				newTextChip.setKeyListener(null);
				newTextChip.setFocusableInTouchMode(false);
				//boton2.setBackgroundResource(R.drawable.ic_action_edit);
				boton2.setVisibility(View.VISIBLE);
				TableRowPerroPrivadoCartilla.setVisibility(View.INVISIBLE);
				Linearaceptarcancelarcartilla.setLayoutParams(params1);
				showingfirst = true;

				
			}


		});

	}




	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.ic_action_new_event).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onMenuItemClick(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.item_comedy:
			Intent cameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraInt, FOTO_CAMARA);  
			return true;
		case R.id.item_movies:
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, FOTO_GALERIA);
			return true;


		default:
			return false;
		}

	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("EL requestcode es :"+requestCode);
		switch(requestCode) {
			case BUSQ_RAZA:  //CODIGO de BusqRazas
				if(resultCode == RESULT_OK){
					String result=data.getStringExtra("result");
					newtextRaza.setText(result);
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
				ImageView img = (ImageView) this.findViewById(R.id.ImageViewFotoPeRRo);
				img.setImageBitmap(selectedBitmap);
				/****************GUARDAR EN BASE DE DATOS*****************/
				/****************GUARDAR EN BASE DE DATOS*****************/
				(new Cache()).guardarImagenCache( "1234",selectedBitmap, this.getCacheDir());
				nombreImagenPerfil="1234";
			}
			break;
		case FOTO_GALERIA:
			Uri selectedImage = data.getData(); 
			recortar(selectedImage);

		}
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



}