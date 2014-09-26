package com.secuest.mdog.Muro;


import com.secuest.mdog.R;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class PopupSelect extends DialogFragment {

	protected static final int FOTO_CAMARA = 2;
	protected static final int RECORTAR = 3;
	private static final int FOTO_GALERIA = 4;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Esconde título DialogFragment
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		View view = inflater.inflate(R.layout.popup_layout, container);		
		Button camara = (Button) view.findViewById(R.id.camaraButton);
		Button galeria = (Button) view.findViewById(R.id.galeriaButton);
		System.out.println("Ha entrado");
		
		camara.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//getActivity().startActivity(new Intent(getActivity(),ActivityCam.class));
				Intent cameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				getDialog().dismiss();
				getActivity().startActivityForResult(cameraInt, FOTO_CAMARA); 
				
			}
		});
		
		galeria.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				getDialog().dismiss();
				getActivity().startActivityForResult(i, FOTO_GALERIA);
				
			
			}
		});
		return view;
	}


	/*
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();




	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.popup_layout, null))
	    // Add action buttons
	           .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   //LoginDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}*/
}
