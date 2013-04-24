package com.my.MyApi;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;

import android.app.ListActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAll extends ListActivity implements OnClickListener{
	MyDatabase db;
	ListView lv;
	public static ViewAll self;
	public static final String title = "title";
	public static final String amount = "amount";
	public static final String user = "userpay";
	public static final String month = "month";
	public static final String day = "day";
	public static final String year = "year";
	TextView t,t1,t2,t3,t4,err,cur,tot;
	ArrayList<User> u;
	EditText e;
	String use,cr;
	int sums=0;
	Button s,s1,viewself,all,credit;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewall);
        self=this;
        TranslateAnimation as= new TranslateAnimation(0,0,100, 0);
        as.setDuration(1500);
    	as.setFillAfter(true);
        viewself = (Button)findViewById(R.id.self);
        viewself.startAnimation(as);
        all=(Button)findViewById(R.id.all);
        all.startAnimation(as);
        credit = (Button)findViewById(R.id.credit);
        credit.startAnimation(as);
        db = new MyDatabase(this);
        u = new ArrayList<User>();
        viewself.setOnClickListener(this);
        all.setOnClickListener(this);
        credit.setOnClickListener(this);
        lv = getListView();
        cr=db.retcur(getIntent().getExtras().getString("the text"));
        //.....................................................
        
        //........................................................
        u = db.getAllUsers(getIntent().getExtras().getString("the text"));
        if (u.size()==0)
        {
        	err=(TextView)findViewById(R.id.erro);
        	err.setVisibility(View.VISIBLE);
        	
        }
        else
        {	
        	sums=0;
        use =getIntent().getExtras().getString("the text");
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (User i:u) {
			
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
        tot=(TextView)findViewById(R.id.total);
    	tot.setVisibility(View.VISIBLE);
    	tot.setText("Total:  "+cr + " "+Integer.toString(sums));
        lv = getListView();
        lv.setAdapter(new MyAdapter(this, data));
        
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
    if ((t.getVisibility()==View.VISIBLE)){
    	s.setVisibility(View.GONE);
    	//s1.setVisibility(View.GONE);
    	t.setVisibility(View.GONE);
    	e.setVisibility(View.GONE);
    }
    else{
    	s.setVisibility(View.VISIBLE);
    	/*if(t1.getText().toString().equals("Self"))
    		s1.setVisibility(View.GONE);
    	else
    		s1.setVisibility(View.VISIBLE);*/
    	t.setVisibility(View.VISIBLE);
    	e.setVisibility(View.VISIBLE);
    	s.setFocusable(false);
    	e.setFocusable(false);
    	//s1.setFocusable(false);
    	l.hasFocus();
    	s.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String s2 = t1.getText().toString();
				String s3 = t2.getText().toString();
				String s4 = t3.getText().toString();
				String s5= t4.getText().toString();
				db.del(use,s2,s3,s4,s5);
				Intent i = new Intent(ViewAll.this, Tabview.class);
	    		i.putExtra("the text", use);
	    		startActivity(i);
			}
		});/*
s1.setOnClickListener(new OnClickListener() {
			
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
				String msg="This is to inform you that a sum of "+s6+s3+" is still pending regarding "+s5+" on "+s4+". \nKindly return the same whenever possible.\n"+use+"\n\n(Sent via Xpence)";
		        sendIntent.putExtra("sms_body", msg); 
		        sendIntent.setType("vnd.android-dir/mms-sms");
		        startActivity(sendIntent);
			}
		});*/
    l.hasFocus();
    	
    }
    
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.credit){
			sums=0;
			lv.setVisibility(View.GONE);
			err=(TextView)findViewById(R.id.erro);
        	err.setVisibility(View.GONE);
        	tot=(TextView)findViewById(R.id.total);
        	tot.setVisibility(View.GONE);
			// TODO Auto-generated method stub
			u = db.getNotUser(getIntent().getExtras().getString("the text"));
		//u = db.gettillDate(getIntent().getExtras().getString("the text"),21,9,2012,22,9,2012);
	        if (u.size()==0)
	        {
	        	err=(TextView)findViewById(R.id.erro);
	        	err.setVisibility(View.VISIBLE);
	        	
	        }
	        else
	        {	
	        use =getIntent().getExtras().getString("the text");
	        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	        for (User i:u) {
				
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
	        tot=(TextView)findViewById(R.id.total);
	    	tot.setVisibility(View.VISIBLE);
	    	tot.setText("Total Credit: "+cr +" "+Integer.toString(sums));
	        lv = getListView();
	        lv.setVisibility(View.VISIBLE);
	        lv.setAdapter(new MyAdapter(this, data));
	        
	        }
		}
		//.........................................................................(viewAll code)
		if(v.getId() == R.id.all){
			sums=0;
			lv.setVisibility(View.GONE);
			err=(TextView)findViewById(R.id.erro);
        	err.setVisibility(View.GONE);
        	tot=(TextView)findViewById(R.id.total);
        	tot.setVisibility(View.GONE);
			// TODO Auto-generated method stub
			u = db.getAllUsers(getIntent().getExtras().getString("the text"));
		//u = db.gettillDate(getIntent().getExtras().getString("the text"),21,9,2012,22,9,2012);
	        if (u.size()==0)
	        {
	        	err=(TextView)findViewById(R.id.erro);
	        	err.setVisibility(View.VISIBLE);
	        	
	        }
	        else
	        {	
	        use =getIntent().getExtras().getString("the text");
	        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	        for (User i:u) {
				
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
	        tot=(TextView)findViewById(R.id.total);
	    	tot.setVisibility(View.VISIBLE);
	    	tot.setText("Total : "+cr+" "+Integer.toString(sums));
	        lv = getListView();
	        lv.setVisibility(View.VISIBLE);
	        lv.setAdapter(new MyAdapter(this, data));
	        
	        }
        
		}
		//......................................................................(viewself code)
		 if(v.getId() == R.id.self){
			 sums=0;
			lv.setVisibility(View.GONE);
			err=(TextView)findViewById(R.id.erro);
        	err.setVisibility(View.GONE);
        	tot=(TextView)findViewById(R.id.total);
        	tot.setVisibility(View.GONE);
					// TODO Auto-generated method stub
					u = db.getByUser("Self",getIntent().getExtras().getString("the text"));
				//u = db.gettillDate(getIntent().getExtras().getString("the text"),21,9,2012,22,9,2012);
			        if (u.size()==0)
			        {
			        	err=(TextView)findViewById(R.id.erro);
			        	err.setVisibility(View.VISIBLE);
			        	
			        }
			        else
			        {	
			        use =getIntent().getExtras().getString("the text");
			        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			        for (User i:u) {
						
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
			        tot=(TextView)findViewById(R.id.total);
			    	tot.setVisibility(View.VISIBLE);
			    	tot.setText("Total Self Expense: "+cr+" "+Integer.toString(sums));
			        lv = getListView();
			        lv.setVisibility(View.VISIBLE);
			        lv.setAdapter(new MyAdapter(this, data));
			        
			        }
					
				}
		}
		
	}
          
    