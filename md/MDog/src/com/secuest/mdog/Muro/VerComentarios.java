package com.secuest.mdog.Muro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.secuest.mdog.R;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Post;
import com.secuest.mdog.Logica.PostComentario;
import com.secuest.mdog.Perfiles.PerfilDueno;
import com.secuest.mdog.Perfiles.PerfilPerro;
import com.secuest.mdog.utils.Cache;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;
import com.secuest.mdog.Test;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VerComentarios extends Activity{
	
	private Post post;
	private com.secuest.mdog.utils.Lista_adaptador mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ver_comentarios);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		final ListView list = (ListView) findViewById(R.id.listviewComentarios);
		
		
		post = (Post) getIntent().getSerializableExtra("Post");
		final ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
		
		final EditText coment = (EditText) findViewById(R.id.edittextComentario);
		
		ImageButton send = (ImageButton) findViewById(R.id.botonSend);
		
		File cacheDir = getBaseContext().getCacheDir();
		Test test = new Test(getResources(),cacheDir);
		test.Load();
		Dueno miUser = test.kate;
		final String Nombre = miUser.getNick();
		System.out.println("Imagen:"+miUser.getImage().toString()+":");
		final int [] newImagePerfilDueno ={
				R.drawable.una	
		};
		
		
		coment.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,
		            int count) {

		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		        
		    }


			@Override
			public void afterTextChanged(Editable s) {
				final Editable Comentario;
				Comentario=coment.getText();
				
			}
		});

		final ArrayList<PostComentario> arrayComentarios= post.getComentarios();
		
		
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				datos.add(new Lista_entrada(newImagePerfilDueno[0], 3, new String[]{ Nombre , null , coment.getText().toString()}));
				mAdapter.notifyDataSetChanged();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	            coment.setText("");
	            coment.setBackgroundColor(Color.argb(255, 255, 255, 255));
	            //Intent intent = null;
				//intent.putExtra("MyClass", arrayComentarios.size());
				
			}});
		
	
		for(int i=0;i<arrayComentarios.size();i++)
			datos.add(new Lista_entrada(arrayComentarios.get(i).getDueno().getImage(), 3, new String[]{ arrayComentarios.get(i).getDueno().getNick() , calcularTiempo(arrayComentarios.get(i).getDate()) , arrayComentarios.get(i).getComentario()}));



		if (mAdapter == null) {
			mAdapter = new Lista_adaptador(this, R.layout.list_item_comentarios, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView nombre = (TextView) view.findViewById(R.id.nombre); 
						if (nombre != null) 
							nombre.setText(((Lista_entrada) entrada).get_texto(0)); 

						TextView fecha = (TextView) view.findViewById(R.id.fecha); 
						if (fecha != null)
							fecha.setText(((Lista_entrada) entrada).get_texto(1)); 


						TextView comentario = (TextView) view.findViewById(R.id.comentario); 
						if (comentario != null)
							comentario.setText(((Lista_entrada) entrada).get_texto(2)); 

						
						String nom = ((Lista_entrada) entrada).getNombreImage();
						ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagen); 
						if (imagen_entrada != null)
							imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
						
					
						
					}
				}
			};
		}

		list.setAdapter(mAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				//Intent i =new Intent(PerfilDueno.this, PerfilPerro.class);
				//i.putExtra("Perro",dueno.getPerros().get(position));
				//startActivity(i);
			} 
		});


	}


	private String calcularTiempo(GregorianCalendar date){

		int min = (int)(date.getTimeInMillis()/1000)/60;
		Calendar c = Calendar.getInstance(); 
		int actual =(int) (c.getTime().getTime()/1000)/60;

		if(actual-min<60)
			return ("Hace "+(actual-min)+" minutos"); 
		else if ((actual-min)<1440)
			return ("Hace "+(int)((actual-min)/60)+" horas");
		else if ((actual-min)<525600)
			return ("Hace "+(int)((actual-min)/1440)+" dias");
		else 
			return ("Hace "+(int)((actual-min)/525600)+" a�os");



		//	System.out.println("Tiempos: "+actual+ " "+ min);
		//	System.out.println("Actual:   "+c.get(Calendar.YEAR)+":"+c.get(Calendar.MONTH)+":"+c.get(Calendar.DAY_OF_MONTH)+"  -  "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":");
		//	System.out.println("Otro:   "+date.get(Calendar.YEAR)+":"+date.get(Calendar.MONTH)+":"+date.get(Calendar.DAY_OF_MONTH)+"  -  "+c.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+":");

	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()) {
		case android.R.id.home:
		finish();   
		}
		return true;
	}

}
