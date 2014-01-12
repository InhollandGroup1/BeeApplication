package com.beeapp.beeapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Set the signup link to clickable otherwise it can't be clicked
		TextView tv = (TextView) this.findViewById(R.id.signupLink);		
		tv.setClickable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void onSignupClicked(View v) {
		Log.d("debug", "onSignupClicked");
		Intent i = new Intent(this, SignupActivity.class);
		startActivity(i); 
	}
	
	public void onLoginClicked(View v) throws UnsupportedEncodingException {
		Log.d("debug", "onLoginClicked");
		EditText et;
		et = (EditText) this.findViewById(R.id.enterEmail);
		String email = et.getText().toString();
		et = (EditText) this.findViewById(R.id.enterPassword);
		String password = et.getText().toString();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
		new HttpCallout().execute(new UrlEncodedFormEntity(nameValuePairs));
	}
	
	class HttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/login.php");
			    httppost.setEntity(args[0]);
			    
			    // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        responseString = EntityUtils.toString(response.getEntity());
		        Log.d("debug", responseString);		        
		        
			} catch (ClientProtocolException e) {
		    	Log.d("debug", e.getMessage());
		    } catch (IOException e) {
		    	Log.d("debug", e.getMessage());
		    }
			return responseString;
		}
		
		protected void onPostExecute(String responseString) {
			try {
				JSONObject jObject = new JSONObject(responseString);		        
		        if (jObject.getString("result").equals("success")) {
		        	Log.d("debug", "attempting to start activity");
		        	// Save the user details
		        	DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
		        	UserObject user = new UserObject(jObject.getInt("id"), jObject.getString("email"),
		        			jObject.getString("firstname"), jObject.getString("lastname"));
		        	dh.setUser(user);
		        	// Go to main menu
		        	Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
		    		startActivity(i);
		        } else {
		        	Toast.makeText(getApplicationContext(), jObject.getString("msg"), Toast.LENGTH_SHORT).show();
		        }
			} catch (JSONException e) {
		    	Log.d("debug", e.getMessage());
		    }
        }
		
	}

}
