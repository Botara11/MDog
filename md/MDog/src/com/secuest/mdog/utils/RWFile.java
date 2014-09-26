package com.secuest.mdog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.secuest.mdog.Amigos.Amigos;
import com.secuest.mdog.Logica.Cita;
import com.secuest.mdog.Logica.Dueno;
import com.secuest.mdog.Logica.Parque;
import com.secuest.mdog.Logica.Perro;
import com.secuest.mdog.Logica.Raza;

import android.os.Environment;

public class RWFile {

	public RWFile(){

	}

	public Dueno cargarDueno(){

		String[] d = read("dueno").split("<,>");
		Dueno dueno = new Dueno(d[0], d[1], d[2], d[3], cargarPerros(), cargarParques(), (new ArrayList<Cita>()),cargarAmigos());
		return dueno;

	}

	public void guardarDueno(Dueno d){
		//Dueno d = new Dueno(id, image, nick, ciudad, perros, parques, citas, amigos)
		String result = d.getId()+"<,>"+d.getImage()+"<,>"+d.getNick()+"<,>"+d.getCiudad();
		write("dueno",result);
		guardarPerros(d.getPerros());
		guardarParques(d.getParques());
		guardarAmigos(d.getAmigos());


	}


	public ArrayList<Dueno> cargarAmigos(){
		int num = Integer.parseInt(read("sizeamigo"));
		String[] s;
		ArrayList<Dueno> a = new ArrayList<Dueno>();
		String result = read("parque");
		String main[] = result.split("</amigos>");
		for(int i=0;i<num;i++){
			s = main[i].split("<,>");
			a.add( new Dueno(s[0], s[1], s[2], "", null, null, null, null));
		}
		return a;
	}

	public void guardarAmigos(ArrayList<Dueno> a){
		String result = "";
		for (int i=0;i<a.size();i++){
			result = result+a.get(i).getId()+"<,>"+a.get(i).getImage()+"<,>"+a.get(i).getImage()+"</amigos>"; 
		}
		write("amigo", result);
		write("sizeamigo",a.size()+"");
	}

	public void guardarParques(ArrayList<Parque> p){
		//Parque pa = new Parque(nombre, ciudad, image, direccion, lat, lon)
		String result="";
		for(int i=0;i<p.size();i++)
			result = result+p.get(i).getNombre()+"<,>"+p.get(i).getCiudad()+
			"<,>"+p.get(i).getImage()+"<,>"+p.get(i).getDireccion()
			+"<,>"+p.get(i).getLat()+"<,>"+p.get(i).getLon()+"</Parques>";
		write("parque",result);
		write("sizeparque",p.size()+"");

	}

	public ArrayList<Parque> cargarParques(){
		int num = Integer.parseInt(read("sizeparque"));
		String[] s;
		ArrayList<Parque> a = new ArrayList<Parque>();
		String result = read("parque");
		String main[] = result.split("</Parques>");
		for(int i=0;i<num;i++){
			s = main[i].split("<,>");
			a.add( new Parque(s[0], s[1], s[2], s[3], Double.parseDouble(s[4]), Double.parseDouble(s[5])));
		}
		return a;
	}

	public ArrayList<Perro> cargarPerros(){//6,7,8 fechas
		int num = Integer.parseInt(read("sizeperro"));
		String result = read("perro");
		ArrayList<Perro> arrayP = new ArrayList<Perro>();
		String[] esta = result.split("</perro>");
		for(int i=0;i<num;i++){
			String[] s = esta[i].split("<,>");

			GregorianCalendar d1 = new GregorianCalendar();d1.setTimeInMillis(Long.parseLong(s[7]));
			GregorianCalendar d2 = new GregorianCalendar();d2.setTimeInMillis(Long.parseLong(s[8]));
			GregorianCalendar d3 = new GregorianCalendar();d3.setTimeInMillis(Long.parseLong(s[9]));
			GregorianCalendar[] array = {d1,d2,d3};
			arrayP.add( new Perro(s[0], s[1],s[2], Integer.parseInt(s[3]), Raza.valueOf(s[4]),
					s[5], s[6], array, s[10], s[11], Integer.parseInt(s[12]), 
					Boolean.parseBoolean(s[13]), s[14], Double.parseDouble(s[15])));
			System.out.println("Cargando");
			System.out.println(esta[i]);
		}
		return arrayP;
	}

	public void guardarPerros(ArrayList<Perro> arrayP){
		
		String result="";
		for(int i=0;i<arrayP.size();i++){
			Perro p = arrayP.get(i);

			result = result+p.getNombre()+"<,>"+p.getGenero().toString()+"<,>"+p.getImage()+"<,>"+
					p.getEdad()+"<,>"+p.getRaza()+"<,>"+p.getAficiones()+"<,>"+p.getPersonalidad()
					+"<,>"+p.getFechas()[0].getTimeInMillis()+"<,>"+p.getFechas()[1].getTimeInMillis()
					+"<,>"+p.getFechas()[2].getTimeInMillis()+"<,>"+p.getLastVacuna()+"<,>"+
					p.getNextVacuna()+"<,>"+p.getAdorabilidad()+"<,>"+Boolean.toString(p.isAdopcion())+"<,>"+
					p.getNumChip()+"<,>"+p.getDistancia()+"</perro>";
		}
		write("perro",result);
		write("sizeperro",arrayP.size()+"");

	}


	public String read (String nameFile){
		//Find the directory for the SD Card using the API
		//*Don't* hardcode "/sdcard"

		File newFolder = new File(Environment.getExternalStorageDirectory(), ".xandroid_4BIOS");
		if (!newFolder.exists()) {
			newFolder.mkdir();
		}

		//Get the text file
		File file = new File(newFolder,nameFile);

		//Read text from file
		String res = "";
		StringBuilder text = new StringBuilder();{

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = "";

				while ((line = br.readLine()) != null) {
					text.append(line);
				}
				br.close();
				res= text.toString();
			}
			catch (IOException e) {
				System.out.println("Excep_en_readFile: "+e);
				//write( nameFile, "0"); In this case "Flexiones" I dont want to write
				return null;
				//You'll need to add proper error handling here
			}

		}
		return res;
	}

	public void write(String nameFile,String text){
		try {

			File newFolder = new File(Environment.getExternalStorageDirectory(), ".xandroid_4BIOS");
			if (!newFolder.exists()) {
				newFolder.mkdir();
			}
			try {
				File file = new File(newFolder, nameFile);
				file.createNewFile();

				FileOutputStream fOut = new FileOutputStream(file);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(text);
				myOutWriter.close();
				fOut.close();
			} catch (Exception ex) {
				System.out.println("exeption: " + ex);
			}
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}
}
