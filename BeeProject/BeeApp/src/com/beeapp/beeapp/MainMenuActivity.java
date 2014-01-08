package com.beeapp.beeapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Button btnNextSearch = (Button) findViewById(R.id.button1);
		Button btnNextProfile = (Button) findViewById(R.id.button2);
		
		 //Listening to button event
        btnNextSearch.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
 
                startActivity(nextScreen);
 
            }
        });
		
        //Listening to button event
        btnNextProfile.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), UserActivity.class);
 
                startActivity(nextScreen);
 
            }
        });
	}

	/*public boolean profileBtnClicked(MenuItem menuItem) {
		Intent intent = new Intent(MainMenuActivity.this, UserActivity.class);
		startActivity(intent);
		return true;
	}*/
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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

}
