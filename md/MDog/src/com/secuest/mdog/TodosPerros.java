package com.secuest.mdog;

import java.util.ArrayList;

import com.secuest.mdog.utils.Lista_entrada;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import com.secuest.mdog.R;
import com.secuest.mdog.utils.Lista_adaptador;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class TodosPerros extends Activity {
	
	private ListView list;
	private String mTag;
	private Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.todosperros_layout);
        list = (ListView)findViewById(R.id.listview);
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

		datos.add(new Lista_entrada(R.drawable.retiro,2,new String[]{ "Alvin Serengueti", null}));
		datos.add(new Lista_entrada(R.drawable.park1, 2,new String[]{ "Idir Ferrahmenta", null}));
		
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new Lista_adaptador(this, R.layout.list_item_amigos, datos){
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
		
		list.setAdapter(mAdapter);
        
    }
	
	
}


