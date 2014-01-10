package com.beeapp.beeapp;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DiseaseDetailActivity extends Activity {
	
	DiseaseDetailAdapter treatmentAdapter;
	DiseaseDetailAdapter symptomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_detail);
		String json = getIntent().getStringExtra("JSON");
		TextView name = (TextView) this.findViewById(R.id.disease_detail_name);
		TextView description = (TextView) this.findViewById(R.id.disease_detail_description);
		Button btn = (Button) this.findViewById(R.id.button1);
		
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d("debug DiseaseDetected", "Link disease to hive");
            }
        });
		//tv.setText(json);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class TreatmentHttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/query_treatment.php");
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
		        	treatmentAdapter.clear();
		        	JSONArray jArray = jObject.getJSONArray("data");
		            for(int i = 0; i < jArray.length(); i++){
		            	treatmentAdapter.add(jArray.getJSONObject(i));
		            }
		        } else {
		        	Log.d("debug", "request failed, message: "+ jObject.getString("msg"));
		        }
			} catch (JSONException e) {
		    	Log.d("debug JSONException", e.getMessage());
		    }
        }
		
	}
	
	class SymptomHttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/query_symptom.php");
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
		        	symptomAdapter.clear();
		        	JSONArray jArray = jObject.getJSONArray("data");
		            for(int i = 0; i < jArray.length(); i++){
		            	symptomAdapter.add(jArray.getJSONObject(i));
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
