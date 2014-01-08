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
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Set the signup link to clickable otherwise it can't be clicked
		TextView tv = (TextView) this.findViewById(R.id.addLocation);		
		tv.setClickable(true);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onAddLocationClicked(View v) {
		Log.d("debug", "onAddLocationClicked");
		// TODO: implement
	}
	
	public void onSignupClicked(View v) throws UnsupportedEncodingException {
		Log.d("debug", "onSignupClicked");
		
		EditText et;
		
		et = (EditText) this.findViewById(R.id.firstName);
		String firstname = et.getText().toString();		
		if (firstname.length() < 1) {
			Toast.makeText(getApplicationContext(), "Please enter your first name.", Toast.LENGTH_SHORT).show();
			et.requestFocus();
			return;
		}
		
		et = (EditText) this.findViewById(R.id.lastName);
		String lastname = et.getText().toString();		
		if (lastname.length() < 1) {
			Toast.makeText(getApplicationContext(), "Please enter your last name.", Toast.LENGTH_SHORT).show();
			et.requestFocus();
			return;
		}
		
		et = (EditText) this.findViewById(R.id.email);
		String email = et.getText().toString();		
		if (!isValidEmail(email)) {
			Toast.makeText(getApplicationContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
			et.requestFocus();
			return;
		}
		
		et = (EditText) this.findViewById(R.id.confirmPassword);
		String password2 = et.getText().toString();
		et = (EditText) this.findViewById(R.id.password);
		String password = et.getText().toString();		
		if (password.length() < 4) {
			Toast.makeText(getApplicationContext(), "Please enter a valid password of at least 4 characters.", Toast.LENGTH_SHORT).show();
			et.requestFocus();
			return;
		}		
		if (!password.equals(password2)) {
			Toast.makeText(getApplicationContext(), "Passwords did not match.", Toast.LENGTH_SHORT).show();
			et.requestFocus();
			return;
		}
		
		String isCoordinator = "0";
		CheckBox cb = (CheckBox) this.findViewById(R.id.healthCoordinator);
		if (cb.isChecked()) isCoordinator = "1";		
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
        nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("coordinator", isCoordinator));
        new HttpCallout().execute(new UrlEncodedFormEntity(nameValuePairs));
	}
	
	private boolean isValidEmail(String email) {
		if (!email.contains("@")) return false;
		if (email.length() < 7) return false;
		return true;
	}
	
	class HttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/register.php");
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
		        Toast.makeText(getApplicationContext(), jObject.getString("msg"), Toast.LENGTH_SHORT).show();
		        if (jObject.getString("result").equals("success")) {
		        	Log.d("debug", "attempting to start activity");
		        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
		    		startActivity(i);
		        }
			} catch (JSONException e) {
		    	Log.d("debug", e.getMessage());
		    }
        }
		
	}

}
