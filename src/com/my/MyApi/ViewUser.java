package com.my.MyApi;

import java.util.ArrayList;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUser extends ListActivity implements android.view.View.OnClickListener{
	private int PICK_CONTACT;
	MyDatabase db;
	public static ViewUser self;
	public static final String title = "title";
	public static final String amount = "amount";
	public static final String user = "userpay";
	public static final String month = "month";
	public static final String day = "day";
	public static final String year = "year";
	TextView t,t1,t2,t3,t4,err,cur,e1,e2,ser,tot;
	EditText e,searchuser,searchtitle;
	String use,s2,s3,s4,s5,ad,s6,cr;
	Button s,s1,but,disp;
	int c1=1,sums=0;
	private String usertitle;
	private String useruser;
	ListView lv;
    Boolean buser;
	Boolean btitle;
	 ArrayList<User> u = new ArrayList<User>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewuser);
        self=this;
        TranslateAnimation as= new TranslateAnimation(0,0,100, 0);
        as.setDuration(500);
    	as.setFillAfter(true);
        searchtitle = (EditText)findViewById(R.id.searchtitle);
   	 searchuser = (EditText)findViewById(R.id.searchuser);
        searchuser.setText("Self");
        searchtitle.setText("");
        //searchuser.startAnimation(as);
        //searchtitle.startAnimation(as);
        e1=(TextView)findViewById(R.id.user1);
        //e1.startAnimation(as);
        e2=(TextView)findViewById(R.id.title1);
        //e2.startAnimation(as);
        ser=(TextView)findViewById(R.id.search1);
        searchuser.setOnClickListener(this);
        but = (Button)findViewById(R.id.but);
        //but.startAnimation(as);
        but.setOnClickListener(this);
        disp = (Button)findViewById(R.id.editdone);
        disp.startAnimation(as);
        disp.setOnClickListener(this);
        tot=(TextView)findViewById(R.id.total1);
        //tot.startAnimation(as);
    }
	@SuppressLint({ "NewApi", "NewApi" })
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.but){
		sums=0;	
		db = new MyDatabase(this);
		u = new ArrayList<User>();
        use =getIntent().getExtras().getString("the text");
        cr=db.retcur(use);
        u = db.getAllUsers(use);
        usertitle = searchtitle.getText().toString();
        useruser = searchuser.getText().toString();
        buser=!useruser.isEmpty();
		btitle =!usertitle.isEmpty();
		lv = getListView();
		if (buser==true && btitle == false){
			u = db.getByUser(useruser,use);
			searchuser.setVisibility(View.GONE);
			searchtitle.setVisibility(View.GONE);
			e1.setVisibility(View.GONE);
			e2.setVisibility(View.GONE);
			but.setVisibility(View.GONE);
			ser.setText("Results");
			disp.setVisibility(View.VISIBLE);
			c1=1;
		}
		else if(buser == false && btitle == true){
			u = db.getByTitle(usertitle,use);
			searchuser.setVisibility(View.GONE);
			searchtitle.setVisibility(View.GONE);
			e1.setVisibility(View.GONE);
			e2.setVisibility(View.GONE);
			but.setVisibility(View.GONE);
			ser.setText("Results");
			disp.setVisibility(View.VISIBLE);
			c1=1;	
		}
		else if(buser == true && btitle == true){
			u = db.getUserTitle(useruser,usertitle,use);
			searchuser.setVisibility(View.GONE);
			searchtitle.setVisibility(View.GONE);
			e1.setVisibility(View.GONE);
			e2.setVisibility(View.GONE);
			but.setVisibility(View.GONE);
			ser.setText("Results");
			disp.setVisibility(View.VISIBLE);
			c1=1;
		}
		else{
			Toast.makeText(getApplicationContext(), "Atleast One Field Should be Entered", Toast.LENGTH_SHORT).show();
			c1=0;
		}
		if (u.size()==0 && c1!=0)
        {
        	err=(TextView)findViewById(R.id.erro1);
        	err.setVisibility(View.VISIBLE);
        	
        }
		else if(c1!=0)
		{	
			sums=0;
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (User i:u) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(user, i.userpay);
			map.put(title, i.title);
			map.put(amount,i.amount);
			sums+=Integer.parseInt(i.amount);
			map.put(day,Integer.toString(i.day));
			map.put(month, Integer.toString(i.month));
			map.put(year, Integer.toString(i.year));
			map.put("notes",i.notes);
			map.put("chk",db.retcur(getIntent().getExtras().getString("the text")));
			data.add(map);
		}	
        tot=(TextView)findViewById(R.id.total1);
    	tot.setVisibility(View.VISIBLE);
    	tot.setText("Total: "+cr+" "+Integer.toString(sums));
        lv = getListView();
        lv.setVisibility(View.VISIBLE);
        lv.setAdapter(new MyAdapter(this, data));
		}
		}	
		else if (v.getId() == R.id.searchuser){
			sums=0;
			Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent, PICK_CONTACT);
		}
		else if(v.getId() == R.id.delete){
			db.del(use,s2,s3,s4,s5);
			lv.setVisibility(View.GONE);
			tot=(TextView)findViewById(R.id.total1);
        	tot.setVisibility(View.GONE);
			if (buser==true && btitle == false){
				c1=1;
				u = db.getByUser(useruser,use);
			}
			else if(buser == false && btitle == true){
				c1=1;
				u = db.getByTitle(usertitle,use);	
			}	
			else if(buser == true && btitle == true){
				c1=1;
				u = db.getUserTitle(useruser,usertitle,use);	
			}
			if (u.size()==0 && c1!=0)
	        {
	        	err=(TextView)findViewById(R.id.erro1);
	        	err.setVisibility(View.VISIBLE);
	        	
	        }
			else if (c1!=0)
			{	
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	        for (User i:u) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(user, i.userpay);
				map.put(title, i.title);
				map.put(amount,i.amount);
				sums+=Integer.parseInt(i.amount);
				map.put(day,Integer.toString(i.day));
				map.put(month, Integer.toString(i.month));
				map.put(year, Integer.toString(i.year));
				map.put("notes",i.notes);
				map.put("chk",db.retcur(getIntent().getExtras().getString("the text")));
				data.add(map);
			}	
	        tot=(TextView)findViewById(R.id.total1);
	    	tot.setVisibility(View.VISIBLE);
	    	tot.setText("Total: "+cr+" "+Integer.toString(sums));
	        lv = getListView();
	        lv.setVisibility(View.VISIBLE);
	        lv.setAdapter(new MyAdapter(this, data));
			}
		}
		else if(v.getId() == R.id.editdone){
			disp.setVisibility(View.GONE);
			searchuser.setVisibility(View.VISIBLE);
			searchtitle.setVisibility(View.VISIBLE);
			e1.setVisibility(View.VISIBLE);
			e2.setVisibility(View.VISIBLE);
			but.setVisibility(View.VISIBLE);
			ser.setText("Search");
			lv.setVisibility(View.GONE);
			err=(TextView)findViewById(R.id.erro1);
        	err.setVisibility(View.GONE);
        	tot=(TextView)findViewById(R.id.total1);
        	tot.setVisibility(View.GONE);
			
		}
		/*else if(v.getId() == R.id.smss){
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			sendIntent.putExtra("address",ad);
			String msg="This is to inform you that a sum of "+db.retcur(use)+s3+" is still pending regarding "+s5+" on "+s4+". \nKindly return the same whenever possible.\n"+use+"\n\n(Sent via Xpence)";
	        sendIntent.putExtra("sms_body", msg); 
	        sendIntent.setType("vnd.android-dir/mms-sms");
	        startActivity(sendIntent);
		}*/
		}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) 
    {

      if (requestCode == PICK_CONTACT&& intent != null)
      {         
          Cursor cursor =  managedQuery(intent.getData(), null, null, null, null);
          cursor.moveToNext();
         // String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.));
          String  name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)); 
          searchuser.setText(name);
          
      }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    s=(Button)v.findViewById(R.id.delete);
    t=(TextView)v.findViewById(R.id.det);
    e=(EditText)v.findViewById(R.id.notes);
    t1=(TextView)v.findViewById(R.id.label);
    t2=(TextView)v.findViewById(R.id.label1);
    t3=(TextView)v.findViewById(R.id.date);
    t4=(TextView)v.findViewById(R.id.detail);
    cur=(TextView)v.findViewById(R.id.curr);
    s2 = t1.getText().toString();
    ad = t1.getText().toString();
	s3 = t2.getText().toString();
	s4 = t3.getText().toString();
	s5= t4.getText().toString();
    if ((t.getVisibility()==View.VISIBLE)){
    	s.setVisibility(View.GONE);
    	//s1.setVisibility(View.GONE);
    	t.setVisibility(View.GONE);
    	e.setVisibility(View.GONE);
    }
    else{
    	/*if(t1.getText().toString().equals("Self"))
    		s1.setVisibility(View.GONE);
    	else
    		s1.setVisibility(View.VISIBLE);*/
    	s.setVisibility(View.VISIBLE);
    	t.setVisibility(View.VISIBLE);
    	e.setVisibility(View.VISIBLE);
    	s.setFocusable(false);
    	e.setFocusable(false);
    	//s1.setFocusable(false);
    	//s.isFocusable();
    	//e.isFocusable();
    	l.hasFocus();
    	s.setOnClickListener(this);
		//s1.setOnClickListener(this);			
		l.hasFocus();
    	}
    }
}    


