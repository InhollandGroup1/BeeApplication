package com.beeapp.beeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.content.Intent; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.*;

public class MessageActivity extends Activity {

	Button buttonSend; 
	EditText txtTo; 
	EditText txtSubject; 
	EditText txtMessage;
	TextView MyHealthCoor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		// Show the Up button in the action bar.
		setupActionBar();
		
		MyHealthCoor = (TextView)findViewById(R.id.editTextTo);
	       Button SelectHCoordButton = (Button)findViewById(R.id.buttonSelect);
	       SelectHCoordButton.setOnClickListener(SelectHCoordButtonOnClickListener);
	       
		buttonSend = (Button) findViewById(R.id.buttonSend); 
		txtTo = (EditText) findViewById(R.id.editTextTo); 
		txtSubject = (EditText) findViewById(R.id.editTextSubject); 
		txtMessage = (EditText) findViewById(R.id.editTextMessage); 
		buttonSend.setOnClickListener(new OnClickListener(){ 
			public void onClick(View v)	{ 
				String to = txtTo.getText().toString(); 
				String subject = txtSubject.getText().toString(); 
				String message = txtMessage.getText().toString(); 
				if (to != null && to.length() == 0)	{ 
					Toast.makeText(getApplicationContext(), 
					"You forgot to enter the email ID", 
					Toast.LENGTH_SHORT).show();
				}
				else if (to != null && to.length() > 0 && !isEmailValid(to)){ 
					Toast.makeText(getApplicationContext(), 
					"Entered email ID is not Valid", Toast.LENGTH_SHORT).show(); 
				} 
				else if (subject != null && subject.length() == 0){ 
					Toast.makeText(getApplicationContext(), 
					"You forgot to enter the subject", 
					Toast.LENGTH_SHORT).show(); 
				}

				else if (message != null && message.length() == 0){ 
					Toast.makeText(getApplicationContext(), 
					"You forgot to enter the message", 
					Toast.LENGTH_SHORT).show(); 
				}
	
				else if (to != null && subject != null && message != null) { 
					Intent email = new Intent(Intent.ACTION_SEND); 
					email.putExtra(Intent.EXTRA_EMAIL, new String[] { to }); 
					email.putExtra(Intent.EXTRA_SUBJECT, subject); 
					email.putExtra(Intent.EXTRA_TEXT, message); 
					email.setType("message/rfc822"); 
					startActivity(Intent.createChooser(email, 
					"Choose an Email client :"));
				} 
			} 
		}); 
	}

	boolean isEmailValid(CharSequence email){ 
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); 
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
		getMenuInflater().inflate(R.menu.message, menu);
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
	
	private Button.OnClickListener SelectHCoordButtonOnClickListener =
		    new Button.OnClickListener()
		   {
		  @Override
		  public void onClick(View v) {
		   // TODO Auto-generated method stub
		   Intent intent = new Intent();
		           intent.setClass(MessageActivity.this, AndroidListActivity.class);
		          
		           startActivityForResult(intent, 0); 
		  }
		   };

		 @Override
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // TODO Auto-generated method stub
		  super.onActivityResult(requestCode, resultCode, data);
		   if (requestCode==0)
		   {
		    switch (resultCode)
		    { case RESULT_OK:
		    	MyHealthCoor.setText(data.getStringExtra("healthcoordinator"));
		      break;
		     case RESULT_CANCELED:
		      break;
		  
		    }

		   }
		 }
		  

}
