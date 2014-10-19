package com.secuest.mdog.utils_MySql;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class UserFunctions {
	 
	private JSONParser jsonParser;

	JSONObject json = null;
	boolean flag = true;
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String updateuser_tag = "updateuser";
	
	// constructorq
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		new Thread(new Runnable() {
			public void run() {
				JSONParser j = new JSONParser();
				json = j.getJSONFromUrl(params);
				flag=false;
			}
		}).start();     

		while(flag){
			try { Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		}
		return json;
		
	}
	
	
	
	public JSONObject updateUser(String name, String email, String newemail, String password, String newpassword, String city){
		// Building Parameters
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", updateuser_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("newemail", newemail));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("newpassword", newpassword));
		params.add(new BasicNameValuePair("city",city));
		
		// getting JSON Object
		//JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);    LLAMAR COMO EL DE ARRIBA
		
		new Thread(new Runnable() {
			public void run() {
				JSONParser j = new JSONParser();
				json = j.getJSONFromUrl(params);
				flag=false;
			}
		}).start();     

		while(flag){
			try { Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		}
		return json;
		
	}
	
	
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password,String city){
		// Building Parameters
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("city",city));
		
		// getting JSON Object
		//JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);    LLAMAR COMO EL DE ARRIBA
		
		new Thread(new Runnable() {
			public void run() {
				JSONParser j = new JSONParser();
				json = j.getJSONFromUrl(params);
				flag=false;
			}
		}).start();     

		while(flag){
			try { Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		}
		return json;
		
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	public boolean isUpdated(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		HashMap<String, String> d = db.getUserDetails();
		JSONObject json = loginUser(d.get("email"), d.get("user"));
		
		return true;
	}
	
	public void uploadImage(String email, String password, String nameImage, Uri adrImage, String name,Context basecontext){
		File sourceFile;
		if(adrImage==null){
			File cacheDir = basecontext.getCacheDir();
			 sourceFile = new File(cacheDir, name);  
		}else{
			 sourceFile = new File(adrImage.getPath());
			 } 
			try { 

				HttpURLConnection conn = null;
				DataOutputStream dos = null;  
				String lineEnd = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				int bytesRead, bytesAvailable, bufferSize;
				byte[] buffer;
				int maxBufferSize = 1 * 1024 * 1024; 
				//File sourceFile = new File(sourceFileUri); 

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL("http://secuest.comuf.com/UploadToServer.php");

				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection(); 
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("email", email);
				conn.setRequestProperty("password", password);
				conn.setRequestProperty("uploaded_file", nameImage);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd); 
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ nameImage + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available(); 

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);  

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);   

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				int serverResponseCode = 0;
				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();
				System.out.println("Devuleve: "+conn.getContentEncoding());
				
				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				ex.printStackTrace();
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
			} catch (Exception e) {

				e.printStackTrace();
				Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  
			}

		 // End else block 
	}

		
	

	public Drawable downloadImage(String str_image_id){
		String url = "http://secuest.comuf.com/include/getImage.php";
		if (!url.endsWith("?"))
			url += "?";

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("image_id", str_image_id));
		String str_params = URLEncodedUtils.format(params, "UTF-8");
		url += str_params;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			InputStream is_image = response.getEntity().getContent();
			System.out.println("La imagen: "+is_image);
			Drawable drawable = Drawable.createFromStream(is_image, str_image_id);
			return drawable;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	
}
