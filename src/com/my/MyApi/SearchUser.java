package com.my.MyApi;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SearchUser extends ListActivity{
	MyDatabase db;
	public static final String title = "title";
	public static final String amount = "amount";
	public static final String user = "userpay";
	public static final String month = "month";
	public static final String day = "day";
	public static final String year = "year";
	TextView t,t1,t2,t3,t4,err,cur;
	EditText e;
	String use;
	Button s,s1;
	private String usertitle;
	private String useruser;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // storing string resources into Array
        setContentView(R.layout.searchuser);
        db = new MyDatabase(this);
        ArrayList<User> u = new ArrayList<User>();
        u = db.getAllUsers(getIntent().getExtras().getString("the text"));
        use =getIntent().getExtras().getString("the text");
        usertitle = getIntent().getExtras().getString("the usertitle");
        Log.d(usertitle, "aSas");
        useruser = getIntent().getExtras().getString("the username");
        Boolean buser=!useruser.isEmpty();
		Boolean btitle =!usertitle.isEmpty();
		if (buser==true && btitle == false){
			u = db.getByUser(useruser,use);
		}
		else if(buser == false && btitle == true){
			u = db.getByTitle(usertitle,use);
		}
		else if(buser == true && btitle == true){
			u = db.getUserTitle(useruser,usertitle,use);
		}
		else{
			Toast.makeText(getApplicationContext(), "Atleast One Field Should be Entered", Toast.LENGTH_SHORT).show();
			
		}
		if (u.size()==0)
        {
        	err=(TextView)findViewById(R.id.erro1);
        	err.setVisibility(View.VISIBLE);
        	
        }
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (User i:u) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(user, i.userpay);
			map.put(title, i.title);
			map.put(amount,i.amount);
			map.put(day,Integer.toString(i.day));
			map.put(month, Integer.toString(i.month));
			map.put(year, Integer.toString(i.year));
			map.put("notes",i.notes);
			map.put("chk",db.retcur(getIntent().getExtras().getString("the text")));
			data.add(map);
		}	
        ListView lv = getListView();
        
        lv.setAdapter(new MyAdapter(this, data));
        
        
        

        // listening to single list item on click
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
    	e.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				e.hasFocus();
			}
		});
    	s.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String s2 = t1.getText().toString();
				String s3 = t2.getText().toString();
				String s4 = t3.getText().toString();
				String s5= t4.getText().toString();
				db.del(use,s2,s3,s4,s5);
				Intent i = new Intent(SearchUser.this, SearchUser.class);
	    		i.putExtra("the text", use);
	    		startActivity(i);
			}
		});
    		/*s1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ad = t1.getText().toString();
				String s3 = t2.getText().toString();
				String s4 = t3.getText().toString();
				String s5= t4.getText().toString();
				String s6=cur.getText().toString();
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.putExtra("address",ad);
				String msg="This is to inform you that a sum of "+s6+s3+" is still pending regarding "+s5+" on "+s4+". \nKindly return the same whenever possible.\n"+use+"\n\n(Sent via MyApp)";
		        sendIntent.putExtra("sms_body", msg); 
		        sendIntent.setType("vnd.android-dir/mms-sms");
		        startActivity(sendIntent);
			}
		});*/
    l.hasFocus();
    	
    }
    
    	
    
    }
    
    
          
    }   





