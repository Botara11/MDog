package com.secuest.mdog.Perfiles;
//Perfil de la perso
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.PerroProtect;
import com.secuest.mdog.Logica.Protectora;
import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.R.id;
import com.secuest.mdog.R.layout;
import com.secuest.mdog.R.menu;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.PopupMenu.OnMenuItemClickListener;




@SuppressWarnings("deprecation")
public class PerfilProtectoraPrivado extends Activity implements OnMenuItemClickListener {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_PERFIL = "Perfil";
	public static String TAB_PERROS = "Perros";
	public static final String TAB_PARQUES = "Parques";
	public static final String TAB_MURO = "Muro";
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;
	private TabHost TabHost;
	private ArrayList<String> mItems;
	private ArrayList<PerroProtect> perrosProtect ;
	private KeyListener originalKeyListenerNombre;
	private KeyListener originalKeyListenerCiudad;
	private KeyListener originalKeyListenerWeb;
	private KeyListener originalKeyListenerTelefono;
	private KeyListener originalKeyListenerCorreo;
	private KeyListener originalKeyListenerDireccion;
	private KeyListener originalKeyListenerImagenProtectoraPrivado;
	private int mCurrentTab=0;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.perfil_protectora_privado_layout);
        
        ListView list = (ListView)findViewById(R.id.ListViewPerrosProtectoraPrivado);
        final EditText direccion = (EditText) findViewById(R.id.DireccionProtectoraPrivado);
        final EditText nombre = (EditText) findViewById(R.id.NombreProtectoraPrivado);
        final EditText ciudad = (EditText) findViewById(R.id.CiudadProtectoraPrivado);
        final EditText telefono = (EditText)findViewById(R.id.TelefonoProtectoraPrivado);
        final EditText email = (EditText)findViewById(R.id.CorreoProtectoraPrivado);
        final EditText direccWeb = (EditText) findViewById(R.id.WebProtectoraPrivado);
        final ImageView ImagenProtectora = (ImageView) findViewById(R.id.ImageViewFotoProtectoraPrivado); 
        
        final Button botonAnadirPerro = (Button) findViewById(R.id.button_prinProtectoraPrivado);
        final Button botonEditar = (Button) findViewById(R.id.BotonEditarPerfilProtectoraPrivado);
        final Button botonCancelar = (Button) findViewById(R.id.cancelarProtectoraPrivado);
		final Button botonAceptar = (Button) findViewById(R.id.AceptarProtectoraPrivado);
		final TableRow TableRowProtectoraPrivado = (TableRow) findViewById(R.id.TableRowProtectoraPrivado);
		
		final Animation animFadein;
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
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
				PopupMenu popupMenu = new PopupMenu(PerfilProtectoraPrivado.this, view);
				popupMenu.setOnMenuItemClickListener(PerfilProtectoraPrivado.this);
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
   	 	Protectora protectora = (Protectora) getIntent().getSerializableExtra("Protectora");
   	 	perrosProtect = protectora.getPerros();
        /**** BASE DE DATOS **********/
        
   	 	
   	 	direccion.setText(protectora.getDireccion());
   	 	if(protectora.getTelefono()==0)
   	 		telefono.setText("Sin telefono");
   	 	else
	 		telefono.setText(protectora.getTelefono()+"");
   	 	email.setText(protectora.getEmail());
   	 	direccWeb.setText(protectora.getPaginaWeb());
   	 	direccWeb.setMaxLines(2);
   	 	
		for(int i=0;i<perrosProtect.size();i++)
			datos.add(new Lista_entrada(perrosProtect.get(i).getImage(), 2, new String[]{ perrosProtect.get(i).getNombre(), perrosProtect.get(i).getRaza().toString()}));
		
		
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(this, R.layout.list_item_amigos, datos){
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
								imagen_entrada.setImageBitmap((new Cache()).cargarImagen(nom, getBaseContext()));
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
        TabHost=(TabHost)findViewById(R.id.TabHostProtectoraPrivado);
        TabHost.setup();
      
        final TabSpec spec1=TabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tabperrosprotectoraPrivado);
        spec1.setIndicator("Perros La Canera");
      
      
        final TabSpec spec2=TabHost.newTabSpec("TAB 2");
        spec2.setIndicator("Perfil Protectora");
        spec2.setContent(R.id.tabperfilprotectoraPrivado);
        
       
        
        TabHost.addTab(spec1);
        TabHost.addTab(spec2);
        
        TabHost.setOnTabChangedListener(new OnTabChangeListener()
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
        
        
        
      
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void onTabChanged(String tabId) {
		final Button botonAnadirPerro = (Button) findViewById(R.id.button_prinProtectoraPrivado);
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (mCurrentTab == 0) {
			botonAnadirPerro.setVisibility(View.VISIBLE);
			mCurrentTab=1;
			
			
		}
		else if(mCurrentTab == 1){
			botonAnadirPerro.setVisibility(View.INVISIBLE);
			mCurrentTab=0;
			
		}
    
	}
	
}