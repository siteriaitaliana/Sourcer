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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WebpageSourcer extends Activity {
    /** Called when the activity is first created. */
	final String DevApiKey = "1cc52dbd861eae667878aa117dbefcbb";
    String host = "http://pastebin.com/api/api_post.php";
	String code = "";
    String line = "";
    String url = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        url = getIntent().getStringExtra(Intent.EXTRA_TEXT).toString();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        RetrieveCode asynctask1 = new RetrieveCode();
        asynctask1.execute(url);
    }
   
    private class RetrieveCode extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {	
		    	URL urls = null;
				BufferedReader rd = null;
				try {
					urls = new URL(url[0]);
					Log.i("Send-Intent-URL", url[0]);
					rd = new BufferedReader(new InputStreamReader(urls.openStream()));
					String inputLine = "";
					while ((inputLine = rd.readLine()) != null)
					{
						code += inputLine + "\n";
					}
					rd.close();					
				} catch (MalformedURLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				  catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			
			String data = "";
			try {
			data = URLEncoder.encode("api_option", "UTF-8") + "=" + URLEncoder.encode("paste", "UTF-8");
	        data += "&" + URLEncoder.encode("api_paste_format", "UTF-8") + "=" + URLEncoder.encode("html5", "UTF-8");
	        data += "&" + URLEncoder.encode("api_dev_key", "UTF-8") + "=" + URLEncoder.encode("1cc52dbd861eae667878aa117dbefcbb", "UTF-8");
	        data += "&" + URLEncoder.encode("api_paste_expire_date", "UTF-8") + "=" + URLEncoder.encode("10M", "UTF-8");
	        data += "&" + URLEncoder.encode("api_paste_code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
	        
	    	URL url2 = new URL(host);
		    URLConnection conn = url2.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();
		    ////// Get response url - START \\\\\\
		    BufferedReader rd1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    while ((line = rd1.readLine()) != null) {
		    	openBrowser();
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
			return line;
		}
		@Override
		protected void onPostExecute(String result) {
			Toast toast = Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	protected void openBrowser() 
	{
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(line));
		startActivity(i); 
		super.finish();
	}

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }	
  
}