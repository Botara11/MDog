package com.secuest.mdog.Mapa;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.secuest.mdog.DrawerActivity;
//import com.example.genial.R;
import com.secuest.mdog.R;


public class Mapa extends Activity {
	boolean band = false; 
	boolean band2 = false; 
	double longi= 0.0; 
	double lati= 0.0; 
	boolean ready = false;
	Marker marker = null;
	Context mContext;
	public static final String ARG_PLANET_NUMBER = "planet_number";
	static final LatLng VALENCIA = new LatLng(39.466667, -0.375);
	static final LatLng KIEL = new LatLng(39.406667, -0.405);
	

	public Mapa(){}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
    




        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapi)).getMap();

		

		
		// Move the camera instantly to Valencia with a zoom of 11.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(VALENCIA, 11));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

		
		map.addMarker(new MarkerOptions().position(new LatLng(39.4759789,-0.3796762))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker)));
		
		
		
		map.addMarker(new MarkerOptions().position(new LatLng(39.4806743,-0.3834313))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.4765917,-0.3831977))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.4696681,-0.3690785))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.4746869,-0.3888839))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));

		map.addMarker(new MarkerOptions().position(new LatLng(39.461965, -0.395407))
				.title("VALENCIA") 
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.457128, -0.365066))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		
		
		
		map.addMarker(new MarkerOptions().position(new LatLng(39.466186, -0.395500))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.458797, -0.376017))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.462840, -0.351855))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.467147, -0.353014))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.476555, -0.356919))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		map.addMarker(new MarkerOptions().position(new LatLng(39.482252, -0.397174))
				.title("VALENCIA")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_violeta)));
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener()
		{
		    @Override public void onInfoWindowClick(Marker arg0) {
		        final String ssid = arg0.getTitle(); 
		    	System.out.println("Clickado");
		    }
		}); 

	}


	
	


}
