package com.beeapp.beeapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
//import android.app.FragmentManager;

public class MainMenuActivity extends FragmentActivity implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener {
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	// Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
		@Override
		public void show(android.app.FragmentManager manager, String tag) {
			// TODO Auto-generated method stub
			super.show(manager, tag);
		}
    }

	private LocationClient mLocationClient;
	private Location mCurrentLocation;
    
    // Handle results returned to the FragmentActivity by Google Play services
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {
            // ...
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
            /*
             * If the result code is Activity.RESULT_OK, try
             * to connect again
             */
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    /*
                     * Try the request again
                     */
                    // ...
                    break;
                }
            // ...
        }
    }
    
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates","Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Get the error code
            //int errorCode = connectionResult.getErrorCode();
        	int errorCode = 0;
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                //errorFragment.
                errorFragment.show(getFragmentManager(),"Location Updates");
            }
            return false;
        }
    }
        
        /*
         * Called by Location Services when the request to connect the
         * client finishes successfully. At this point, you can
         * request the current location or start periodic updates
         */
        @Override
        public void onConnected(Bundle dataBundle) {
            // Display the connection status
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
          mCurrentLocation = mLocationClient.getLastLocation();
          Log.d("debug", "map? " + mCurrentLocation.getLatitude() + " " + mCurrentLocation.getLongitude());
        }
        
        /*
         * Called by Location Services if the connection to the
         * location client drops because of an error.
         */
        @Override
        public void onDisconnected() {
            // Display the connection status
            Toast.makeText(this, "Disconnected. Please re-connect.",
                    Toast.LENGTH_SHORT).show();
        }
        
        /*
         * Called by Location Services if the attempt to
         * Location Services fails.
         */
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
            if (connectionResult.hasResolution()) {
                try {
                    // Start an Activity that tries to resolve the error
                    connectionResult.startResolutionForResult(
                            this,
                            CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
                } catch (IntentSender.SendIntentException e) {
                    // Log the error
                    e.printStackTrace();
                }
            } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
                //showErrorDialog(connectionResult.getErrorCode());
            	showDialog(connectionResult.getErrorCode());
            }
        }
        
        /*
         * Called when the Activity becomes visible.
         */
        @Override
        protected void onStart() {
            super.onStart();
            // Connect the client.
            mLocationClient.connect();
        }
        /*
         * Called when the Activity is no longer visible.
         */
        @Override
        protected void onStop() {
            // Disconnecting the client invalidates it.
            mLocationClient.disconnect();
            super.onStop();
        }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		
		mLocationClient = new LocationClient(this, this, this);
		//mCurrentLocation = mLocationClient.getLastLocation();
		//Log.d("debug", "map? " + mCurrentLocation.getLatitude() + " " + mCurrentLocation.getLongitude());
		
		Button btnNextSearch = (Button) findViewById(R.id.button1);
		Button btnNextProfile = (Button) findViewById(R.id.button2);
		Button btnAskCoord = (Button) findViewById(R.id.button3);
		Button btnMap = (Button) findViewById(R.id.button4);		
		
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
        
      //Listening to button event
        btnAskCoord.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), MessageActivity.class);
 
                startActivity(nextScreen);
 
            }
        });
        
        btnMap.setOnClickListener(new View.OnClickListener() {        	 
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), MapActivity.class); 
                startActivity(nextScreen);
 
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
