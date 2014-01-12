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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends Activity {
	
	EditText fname;
	EditText lname;
	EditText email;
	UserObject user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		// Show the Up button in the action bar.
		setupActionBar();
		fname = (EditText) findViewById(R.id.editText1);
		lname = (EditText) findViewById(R.id.editText2);
		email = (EditText) findViewById(R.id.editText3);
		DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
		user = dh.getUser();
		fname.setText(user.firstname);
		lname.setText(user.lastname);
		email.setText(user.email);
		Button btnBack = (Button) findViewById(R.id.button1);
		Button btnSave = (Button) findViewById(R.id.button2);
		btnBack.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
				startActivity(i);
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("id", Integer.toString(user.id)));
		        nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("fname", fname.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("lname", lname.getText().toString()));
				try {
					new HttpCallout().execute(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
		getMenuInflater().inflate(R.menu.user, menu);
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
	
	class HttpCallout extends AsyncTask<UrlEncodedFormEntity, Void, String> {

		@Override
		protected String doInBackground(UrlEncodedFormEntity... args) {
			String responseString = "";
			try {
				// Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://www.transeasy.cz/databees/save_user.php");
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
		        	UserObject user = new UserObject();
					user.firstname = fname.getText().toString();
					user.lastname = lname.getText().toString();
					user.email = email.getText().toString();
					DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
					dh.setUser(user);
					Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
					startActivity(i);
					Toast.makeText(getApplicationContext(), "Profile saved.", Toast.LENGTH_SHORT).show();
		        } else {
		        	Toast.makeText(getApplicationContext(), "Profile not saved, please try again.", Toast.LENGTH_SHORT).show();
		        }
			} catch (JSONException e) {
		    	Log.d("debug", e.getMessage());
		    }
        }
		
	}
}
