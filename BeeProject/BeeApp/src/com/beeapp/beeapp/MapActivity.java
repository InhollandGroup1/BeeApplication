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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.beeapp.beeapp.DiseaseDetailActivity.TreatmentHttpCallout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	
	private GoogleMap map;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);       

        // Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
        map.setMyLocationEnabled(true);
        LatLng ams = new LatLng(52.3790, 4.901);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ams, 10));
        
        new HttpCallout().execute();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	public void addMarkerToMap(JSONObject json) {
		try {
			String name = json.getString("name");
			String date = json.getString("date_reported");
			String user = json.getString("firstname") + " " + json.getString("lastname");
			double lat = json.getDouble("latitude");
			double lon = json.getDouble("longitude");
			LatLng pos = new LatLng(lat, lon);
			map.addMarker(new MarkerOptions().title(name)
	            .snippet("Reported by " + user + " (on " + date + ")")
	            .position(pos));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class HttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/disease_locations.php");
			    //httppost.setEntity(args[0]);
			    
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
		        	JSONArray jArray = jObject.getJSONArray("data");
		            for(int i = 0; i < jArray.length(); i++){
		            	addMarkerToMap(jArray.getJSONObject(i));
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
