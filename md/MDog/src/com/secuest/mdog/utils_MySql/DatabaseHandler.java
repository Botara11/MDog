package com.secuest.mdog.utils_MySql;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_LOGIN = "login";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASS = "password";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_CITY = "city";
	private static final String KEY_IMG = "image";
	private static final String KEY_UPDATED_AT = "updated_at";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_PASS + " TEXT,"
				+ KEY_CITY + " TEXT,"
				+ KEY_IMG + " TEXT,"
				+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT,"
				+ KEY_UPDATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
		Log.d("","CREADA BD");
	}

	// Upgrading database
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email,String pass,String ciudad, String image, String uid, String created_at, String updated_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_PASS, pass); // Pass
		values.put(KEY_CITY, ciudad); // Pass
		values.put(KEY_IMG, image); // Pass
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at);
		values.put(KEY_UPDATED_AT, updated_at);// Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}
	
	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String,String> user = new HashMap<String,String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	user.put("name", cursor.getString(1));
        	user.put("email", cursor.getString(2));
        	user.put("password", cursor.getString(3));
        	user.put("city", cursor.getString(4));
        	user.put("image", cursor.getString(5));
        	user.put("uid", cursor.getString(6));
        	user.put("created_at", cursor.getString(7));
        	user.put("updated_at", cursor.getString(8));
        }
        cursor.close();
        db.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status
	 * return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		
		// return row count
		return rowCount;
	}
	
	/**
	 * Re crate database
	 * Delete all tables and create them again
	 * */
	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}

	public void setUserImage(String string) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String strFilter = "uid='" + string+"'";
		ContentValues args = new ContentValues();
		args.put(KEY_IMG, "'"+string+"'");
		db.update(TABLE_LOGIN, args, strFilter, null);
		db.close();
		Log.d("","MODIFICADA BD");
		
	}
	
	public void setUserNameCity(String uid, String name,String city) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String strFilter = "uid='" + uid+"'";
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, "'"+name+"'");
		args.put(KEY_CITY, "'"+city+"'");
		db.update(TABLE_LOGIN, args, strFilter, null);
		db.close();
		Log.d("","MODIFICADA namecity BD");
		
	}

}
