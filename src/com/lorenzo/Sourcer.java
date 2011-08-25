package com.lorenzo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.lorenzo.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Sourcer extends Activity {
    /** Called when the activity is first created. */
	final String DevApiKey = "1cc52dbd861eae667878aa117dbefcbb";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		////// retrieve url link to view code \\\\\\
        String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		String code = null;
	    String line = null;
        
		////// retrieve html code \\\\\\
		if (url != null) {
			//Log.e(url, "XXXXXXXXXX");
			//Uri uriUrl = Uri.parse(url);
			URL urls = null;
			BufferedReader rd = null;
			try {
				urls = new URL(url);
				rd = new BufferedReader(new InputStreamReader(urls.openStream()));
				String inputLine = "";
				while ((inputLine = rd.readLine()) != null)
				{
					//System.out.println(inputLine);
				}
				rd.close();
				code = inputLine.toString();
				
			} catch (MalformedURLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			  catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
        }
		else 
		{
			Log.e(url,"Errore url non trovato");
	    }	  
			  
		////// post API request to paste bin \\\\\\
        String data;
		try {
		
		data = URLEncoder.encode("api_option", "UTF-8") + "=" + URLEncoder.encode("paste", "UTF-8");
        data += "&" + URLEncoder.encode("api_paste_format", "UTF-8") + "=" + URLEncoder.encode("html5", "UTF-8");
        data += "&" + URLEncoder.encode("api_dev_key", "UTF-8") + "=" + URLEncoder.encode("1cc52dbd861eae667878aa117dbefcbb", "UTF-8");
        data += "&" + URLEncoder.encode("api_paste_code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
        
    	URL url2 = new URL("http://hostname:80/cgi");
	    URLConnection conn = url2.openConnection();
	    conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(data);
	    wr.flush();

	    ////// Get response url \\\\\\
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    while ((line = rd.readLine()) != null) {
	        // Process line...
	    }
	    	wr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MalformedURLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	
		////// Navigate to response url \\\\\\
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(line));
		startActivity(i); 
		
		super.finish();
	 }
    
    
    
}