package com.secuest.mdog.BuscarPerrosCerca;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.secuest.mdog.Logica.Raza;
import com.secuest.mdog.MiCuenta.MiCuenta;
import com.secuest.mdog.utils.Lista_entrada;

import android.sax.TextElementListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import java.util.ArrayList;
import com.secuest.mdog.R;
import com.secuest.mdog.utils.Lista_adaptador;
import com.secuest.mdog.utils.Lista_entrada;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class BusqRazas extends Activity {
	private ListView list;
	private String mTag;
	private Lista_adaptador mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotal;
	private int mPosition;
	private EditText filtro;
	private SearchableAdapter adapter;
	private Lista_adaptador uu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.buscar_raza);

		list = (ListView)findViewById(R.id.lista_perrosraza);
		filtro = (EditText)findViewById(R.id.filtro);
		
		Raza mi=Raza.Akita_Inu;
		List<String> datas = Arrays.asList(mi.getRazas()); 

		adapter = new SearchableAdapter(this, datas);
		list.setAdapter(adapter);

		filtro.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				BusqRazas.this.adapter.getFilter().filter(cs);   
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}


			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});




	}


	public class SearchableAdapter extends BaseAdapter implements Filterable {

		private List<String>originalData = null;
		private List<String>filteredData = null;
		private LayoutInflater mInflater;
		private ItemFilter mFilter = new ItemFilter();

		public SearchableAdapter(Context context, List<String> data) {
			this.filteredData = data ;
			this.originalData = data ;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			if(filteredData==null){
				Log.v("LOG","Warn, null filteredData");
				return 0;
			}else{
				return filteredData.size();
			}
		}

		public Object getItem(int position) {
			return filteredData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid unnecessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is no need
			// to reinflate it. We only inflate a new View when the convertView supplied
			// by ListView is null.
			if (convertView == null) {
				/****************************************************************************************************
				 * /*************************  AQUI el layout LIST_ITEM que corresponda  ******************************
				 * /*************************										     ******************************
				 * /***************************************************************************************************
				 */
				convertView = mInflater.inflate(R.layout.list_item_coniconos, null);

				// Creates a ViewHolder and store references to the two children views
				// we want to bind data to.
				holder = new ViewHolder();

				/****************************************************************************************************
				 * /*************************  AQUI ponemos las imagenes y el texo  **********************************
				 * /*************************										***********************************
				 * /***************************************************************************************************
				 */
				holder.text = (TextView) convertView.findViewById(R.id.textsuperior);
				ImageView imagen_entrada = (ImageView) convertView.findViewById(R.id.image); 
				if (imagen_entrada != null)
					imagen_entrada.setImageResource(R.drawable.marker_violeta);
			
				// Bind the data efficiently with the holder.

				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}

			// If weren't re-ordering this you could rely on what you set last time
			holder.text.setText(filteredData.get(position));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result",((TextView)v.findViewById(R.id.textsuperior)).getText());
					setResult(RESULT_OK,returnIntent);
					finish();
				}
			});
			return convertView;
			
			 
		}
		
		
			
		class ViewHolder {
			TextView text;
		}

		public Filter getFilter() {
			return mFilter;
		}

	
		
		private class ItemFilter extends Filter {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				String filterString = constraint.toString().toLowerCase();

				FilterResults results = new FilterResults();

				final List<String> list = originalData;

				int count = list.size();
				final ArrayList<String> nlist = new ArrayList<String>(count);

				String filterableString ;

				for (int i = 0; i < count; i++) {
					filterableString = list.get(i);
					if (filterableString.toLowerCase().contains(filterString)) {
						nlist.add(filterableString);
					}
				}

				results.values = nlist;
				results.count = nlist.size();

				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				filteredData = (ArrayList<String>) results.values;
				notifyDataSetChanged();
			}

		}
	}

}

