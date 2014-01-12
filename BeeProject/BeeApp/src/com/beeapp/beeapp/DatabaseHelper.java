package com.beeapp.beeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "beeapp_db";
    private static final String TABLE_USER = "user_info";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_FIRSTNAME = "user_firstname";
    private static final String COLUMN_USER_LASTNAME = "user_lastname";
    private static final String DATABASE_TABLE_CREATE =
                "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_FIRSTNAME + " TEXT, " +
                COLUMN_USER_LASTNAME + " TEXT);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("debug", "initializing databasehelper");
    }
    
    public void setUser(UserObject user) {
    	ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.id);
        values.put(COLUMN_USER_EMAIL, user.email);
        values.put(COLUMN_USER_FIRSTNAME, user.firstname);
        values.put(COLUMN_USER_LASTNAME, user.lastname);
 
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_USER;
        Log.d("debug", "deleting old user details");
        db.execSQL(query);
        Log.d("debug", "inserting user details");
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    
    public UserObject getUser() {
    	UserObject user = new UserObject();    	
    	String query = "SELECT "
    			+ COLUMN_USER_ID + ", "
    			+ COLUMN_USER_EMAIL + ", "
    			+ COLUMN_USER_FIRSTNAME + ", "
    			+ COLUMN_USER_LASTNAME
    			+ " FROM " + TABLE_USER
    			+ " LIMIT 1";
    	
    	SQLiteDatabase db = this.getWritableDatabase();    	
    	Cursor cursor = db.rawQuery(query, null);
    	
    	if (cursor.moveToFirst()) {
    		Log.d("debug", "retrieving new user details");
    		cursor.moveToFirst();
    		user.id = cursor.getInt(0);
    		user.email = cursor.getString(1);
    		user.firstname = cursor.getString(2);
    		user.lastname = cursor.getString(3);
    		cursor.close();
    	}
        
    	db.close();
    	return user;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("debug", "creating database");
        db.execSQL(DATABASE_TABLE_CREATE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("debug", "updating database");
		
	}

}
