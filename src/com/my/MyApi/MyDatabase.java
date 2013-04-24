package com.my.MyApi;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 12;

	private static final String DATABASE_NAME = "Sanchayan";

	private static final String TABLE_LOGIN = "login";
	private static final String username = "username";
	private static final String title = "title";
	private static final String amount = "amount";
	private static final String user = "user";
	private static final String month = "month";
	private static final String day = "day";
	private static final String year = "year";
	private static final String notes="notes";
	private static final String TABLE_DATA = "data2";
	private static final String KEY_USER = "user";
	private static final String KEY_PASS = "pass";

	public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_USER + "," + KEY_PASS + " TEXT, currency"
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		Log.d(KEY_USER,KEY_PASS);
		String CREATE_CONTACTS_DATA = "CREATE TABLE " + TABLE_DATA + "("+ username+","+title+","+amount+","+user +" TEXT,"+day +" int,"+month +" int,"+year+" int,"+notes+")";
		db.execSQL(CREATE_CONTACTS_DATA);
	}
	public void deluser(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = username+" = ?";
		db.delete(TABLE_DATA, where, new String[] {name});
		String where1 = KEY_USER+ " = ?";
		db.delete(TABLE_LOGIN, where1, new String[]{name});
		db.close();
	}
	public void reuser(String name)
	{	
		SQLiteDatabase db1 = this.getWritableDatabase();
		String where = "username";
		db1.delete(TABLE_DATA, where+" = ? ", new String[] {name});
		db1.close();
	}
	// Adding new contact
	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USER, contact.getUser()); // Contact Name
		values.put(KEY_PASS, contact.getPass()); // Contact Phone
		values.put("currency", "Rs.");
		// Inserting Row
		
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}
	public void addData(String un, String ti, String am, String u,int d,int m, int y,String note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(username, un); 
		values.put(title, ti); 
		values.put(amount, am);
		values.put(user, u);
		values.put(day, d);
		values.put(month, m);
		values.put(year, y);
		values.put(notes, note);
		db.insert(TABLE_DATA, null, values);
		db.close();
	}
	
	Contact getCont(String name){

		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_LOGIN + " where user = ? ",new String[] {name});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			Contact contact = new Contact(
					cursor.getString(0), cursor.getString(1),cursor.getString(2));
			return contact;
		}
		else{
			return null;
		}	
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		onCreate(db);
		
	}

	public ArrayList<User> getAllUsers(String name) {
		// TODO Auto-generated method stub
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_DATA+ " WHERE username = ? order by year desc,month desc,day desc", new String[]{name});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				user.notes=cursor.getString(7);
				userList.add(user);
			} while (cursor.moveToNext());
			
		}
		db.close();
		return userList;
	}
	public ArrayList<User> getByUser(String name, String useb) {
		// TODO Auto-generated method stub
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_DATA+ " WHERE user = ? and username =  ? order by year desc,month desc,day desc", new String[]{name,useb});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				user.notes=cursor.getString(7);
				userList.add(user);
			} while (cursor.moveToNext());
		}
		db.close();
		return userList;
	}
	
	
	public ArrayList<User> getBetweenDates(String name, int day1, int month1,int year1,int day2,int month2,int year2) {
		// TODO Auto-generated method stub
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		String q2 ="Select * from "+TABLE_DATA +" where year >=" + year1;
		String q1 = "Select * from (" +q2+ ") where month >=  "+month1;
		String q3 = "SELECT * FROM ("+ q1+ ") where day>= "+day1;
		String q4 = "SELECT * FROM ("+ q3+ ") where year <= "+year2;
		String q5 = "Select * from (" +q4+ ") where month <=  "+month2;
		Cursor cursor=db.rawQuery("SELECT * FROM ("+ q5+ ") where username = ? and day <= ? order by year desc,month desc,day desc", new String[]{name,Integer.toString(day2)});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				user.notes=cursor.getString(7);
				userList.add(user);
			} while (cursor.moveToNext());
		}
		db.close();
		return userList;
	}
	
	public ArrayList<User> getByTitle(String title, String use) {
		// TODO Auto-generated method stub
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_DATA+ " WHERE title = ? and username = ? order by year desc,month desc,day desc", new String[]{title,use});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				user.notes=cursor.getString(7);
				userList.add(user);
			} while (cursor.moveToNext());
		}
		db.close();
		return userList;
	}

	public void del(String use, String s2, String s3, String s4, String s5) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String d,m,y;
		StringTokenizer st=new StringTokenizer(s4,"-",false);
		d=st.nextToken();
		m=st.nextToken();
		y=st.nextToken();
		String m1=s5.substring(7);
		String am=s3.substring(0);
		db.delete(TABLE_DATA, "username = ? and amount= ? and title=? and user=? and day= ? and month= ? and year= ? ", new String[] {use,am,m1,s2,d,m,y});
		db.close();
	}
	public ArrayList<User> getUserTitle(String useruser, String usertitle, String chk) {
		// TODO Auto-generated method stub
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_DATA+ " WHERE title = ? and user = ? and username=? order by year desc,month desc,day desc", new String[]{usertitle,useruser,chk});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				userList.add(user);
				Log.d(userList.toString(), "sad");
			} while (cursor.moveToNext());
		}
		db.close();
		return userList;
	}

	public void chgcur(String str, String str2) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String where = user+" = ?";
		ContentValues dataToInsert = new ContentValues();
		str2=str2.trim();
		dataToInsert.put("currency",str2);
		db.update(TABLE_LOGIN, dataToInsert,where, new String[] {str});
		db.close();
	}

	

	public String retcur(String string) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_LOGIN + " where user = ? ",new String[] {string});
			cursor.moveToFirst();
			
			return cursor.getString(2);
	}

	public String retdaysum(String string, int dd, int mm, int yy) {
		// TODO Auto-generated method stub
		int sum=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_DATA + " where username = ? and day= ? and month=? and year=?",new String[] {string,Integer.toString(dd),Integer.toString(mm),Integer.toString(yy)});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			do
			{	
				sum+=Integer.parseInt(cursor.getString(2));
			}while(cursor.moveToNext());	
			return Integer.toString(sum);
		}
		else
			return "0";
	}

	public String retmonthsum(String string, int mm, int yy) {
		// TODO Auto-generated method stub
		int sum=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_DATA + " where username = ? and month=? and year=?",new String[] {string,Integer.toString(mm),Integer.toString(yy)});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			do
			{	
				sum+=Integer.parseInt(cursor.getString(2));
			}while(cursor.moveToNext());	
			return Integer.toString(sum);
		}
		else
			return "0";
	}

	public String retyearsum(String string, int yy) {
		// TODO Auto-generated method stub
		int sum=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_DATA + " where username = ? and year=?",new String[] {string,Integer.toString(yy)});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			do
			{	
				sum+=Integer.parseInt(cursor.getString(2));
			}while(cursor.moveToNext());	
			return Integer.toString(sum);
		}
		else
			return "0";
	}

	public String rettotal(String string) {
		// TODO Auto-generated method stub
		int sum=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_DATA + " where username = ? and user=?",new String[] {string,"Self"});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			do
			{	
				sum+=Integer.parseInt(cursor.getString(2));
			}while(cursor.moveToNext());	
			return Integer.toString(sum);
		}
		else
			return "0";
	}

	public String retcredit(String string) {
		// TODO Auto-generated method stub
		int sum=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from "+ TABLE_DATA + " where username = ? ",new String[] {string});
		if (cursor.getCount()!=0)
		{
			cursor.moveToFirst();
			do
			{	
				sum+=Integer.parseInt(cursor.getString(2));
			}while(cursor.moveToNext());	
			return Integer.toString(sum);
		}
		else
			return "0";
	}

	public ArrayList<User> getNotUser(String name) {
		ArrayList<User> userList = new ArrayList<User>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_DATA+ " WHERE username = ? and user != ? order by year desc,month desc,day desc", new String[]{name,"Self"});
		if (cursor.getCount()!=0) {
			cursor.moveToFirst();
			do {
				User user = new User();
				user.username =cursor.getString(0);
				user.title=cursor.getString(1);
				user.amount= cursor.getString(2);
				user.userpay = cursor.getString(3);
				user.day = cursor.getInt(4);
				user.month = cursor.getInt(5);
				user.year = cursor.getInt(6);
				user.notes=cursor.getString(7);
				userList.add(user);
			} while (cursor.moveToNext());
			
		}
		db.close();
		return userList;
	}
	
	
}