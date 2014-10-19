package com.secuest.mdog.utils_MySql;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.NameValuePair;
import org.json.JSONObject;


import android.graphics.drawable.Drawable;

public class ola{

	public ola(){}


	public Drawable downloadImage(){
		String url = "http://secuest.comuf.com/include/getImage.php";
		if (!url.endsWith("?"))
			url += "?";

		String str_image_id = "miImagen";
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
			Drawable drawable = Drawable.createFromStream(is_image, "miImagen");
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