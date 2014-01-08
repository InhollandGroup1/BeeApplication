package com.beeapp.beeapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements TextWatcher {
	
	ListView gallery;
	List<JSONObject> list;
	DiseaseImageAdapter adapter;
	EditText search;
	
	Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            Log.d("debug", "sending query");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("query", search.getText().toString()));
            try {
            	new HttpCallout().execute(new UrlEncodedFormEntity(nameValuePairs));
            } catch (Exception e) {
            	Log.d("debug", "request failed: " + e.getMessage());
            }
        }
    };

	@Override
	protected void onPause() {
		super.onPause();
		timerHandler.removeCallbacks(timerRunnable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList<JSONObject>();
		adapter = new DiseaseImageAdapter(this, R.layout.disease_list_layout, list);
		gallery = (ListView) this.findViewById(R.id.listView1);
		gallery.setAdapter(adapter);
		search = (EditText) this.findViewById(R.id.editText1);
		search.addTextChangedListener(this);
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
		      @Override
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	  JSONObject jO = list.get(position);
		    	  Intent intent = new Intent(MainActivity.this, DiseaseDetailActivity.class);
		    	  intent.putExtra("JSON", jO.toString());
		    	  startActivity(intent);
		      }
	    });
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		Log.d("d", "sending request in 1s");
		timerHandler.removeCallbacks(timerRunnable);
		timerHandler.postDelayed(timerRunnable, 1000);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
	}
	
	class HttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/query.php");
			    httppost.setEntity(args[0]);
			    
			    // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        responseString = EntityUtils.toString(response.getEntity());
		        Log.d("debug", responseString);		        
		        
			} catch (ClientProtocolException e) {
		    	Log.d("debug ClientProtocolException", e.getMessage());
		    } catch (IOException e) {
		    	Log.d("debug IOException", e.getMessage());
		    }
			return responseString;
		}
		
		protected void onPostExecute(String responseString) {
			try {
				JSONObject jObject = new JSONObject(responseString);		        
		        if (jObject.getString("result").equals("success")) {
		        	Log.d("debug", "request successful, results: "+ jObject.getString("count"));
		        	adapter.clear();
		        	JSONArray jArray = jObject.getJSONArray("data");
		            for(int i = 0; i < jArray.length(); i++){
		            	adapter.add(jArray.getJSONObject(i));
		            }
		        } else {
		        	Log.d("debug", "request failed, message: "+ jObject.getString("msg"));
		        }
			} catch (JSONException e) {
		    	Log.d("debug JSONException", e.getMessage());
		    }
        }
		
	}

}
