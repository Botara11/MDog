package com.secuest.mdog.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Cache {

	public Cache(){

	}
	public Bitmap cargarImagen(String nombre, Context basecontext) throws FileNotFoundException{

		File cacheDir = basecontext.getCacheDir();
		File f = new File(cacheDir, nombre);     
		FileInputStream fis = null;
		fis = new FileInputStream(f);

		Bitmap bitmap = BitmapFactory.decodeStream(fis);

		bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
		return bitmap;
		//ImageView viewBitmap = (ImageView) findViewById(R.id.icon2);
		//viewBitmap.setImageBitmap(bitmap);		
	}
	
	

	public void guardarImagenCache(String nombreImagen,Bitmap mutableBitmap,File cacheDir){
		
		File f = new File(cacheDir, nombreImagen); // (IDdueno+num d perro)
			System.out.println("Direccion: "+f.getAbsolutePath());

			try {
				FileOutputStream out = new FileOutputStream(f);
				mutableBitmap.compress(Bitmap.CompressFormat.JPEG,100, out);
				out.flush();
				out.close();
				System.out.println("Se ha guardado la imagen en cache");

			} catch (FileNotFoundException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
	}

}
