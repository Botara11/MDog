package com.secuest.mdog.Perfiles;

import com.secuest.mdog.R;
import com.secuest.mdog.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MiPerfil extends Fragment{
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public MiPerfil(){
    	
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.perfil_layaout, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];
        //ho
        //int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
        //               "drawable", getActivity().getPackageName());
        //((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        //  getActivity().setTitle(planet);
        return rootView;
    }
    
}
