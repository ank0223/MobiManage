package com.my.MyApi;

import android.app.Activity;
import android.app.Activity;  
import android.app.ProgressDialog;  
import android.os.AsyncTask;  
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import android.app.ProgressDialog;
import android.os.AsyncTask; 
public class Screen2 extends Activity {
	String Username,Password;
	MyDatabase db;
	Button b;
	Button z;
	TextView text,xpence;
	
	EditText user,pass;
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        db = new MyDatabase(this);
        TranslateAnimation as= new TranslateAnimation(0,0,100, 0);
    	AlphaAnimation al = new AlphaAnimation(0, 1);
    	al.setDuration(3000);
    	al.setFillAfter(true);
    	//fromXposition- x coordinate from  where animation should start
    	//toXPosition- x coordinate at which animation would end
    	//fromYPosition- y coordinate from where animation should start.
    	//toYPosition- y coordinate at which animation would end.
    	 
    	//You can also set duration for the animation that means you can set for how long the animation should last:
    	as.setDuration(1500);
    	as.setFillAfter(true);
    	
    	//You can now apply the animation to a view
        //close the progress dialog  
        //initialize the View  
    	
        setContentView(R.layout.screen2);
        xpence = (Button)findViewById(R.id.Xpence);
        xpence.startAnimation(al);
        user = (EditText)findViewById(R.id.editText1);
        user.startAnimation(as);
        pass = (EditText)findViewById(R.id.editText2);
        pass.startAnimation(as);
        
        b = (Button)findViewById(R.id.button1);
        b.startAnimation(as);
        z = (Button)findViewById(R.id.button2);
        z.startAnimation(as);
        b.setFocusable(true);
        b.setFocusableInTouchMode(true);
        b.requestFocus();
        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
            	 if(user.getText().toString().equals("Username")){
            		 user.setText("");
            		 user.setTextColor(0xFF000000);
            	 }
            	 else if(user.getText().toString().isEmpty()==true)
            	 {
            		 user.setText("Username");
            		 user.setTextColor(0xFFF6F6F6);
            	 }
             }
           });
         pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
            	 if (pass.hasFocus()==true)
            	 { 
            		 pass.setText("");
            		 pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            		 pass.setTextColor(0xFF000000);
            	 }
            	 else if(pass.hasFocus()==false && pass.getText().toString().isEmpty()==true)
            	 {
            		 pass.setText("Password");
            		 pass.setInputType(InputType.TYPE_CLASS_TEXT);
            		 pass.setTextColor(0xFFF6F6F6);
            	 }
             }
           });


         b.setOnClickListener(new OnClickListener() {
			
			@Override
			 public void onClick(View v){
				Username=user.getText().toString();
				Password=pass.getText().toString();
		    	Contact c = db.getCont(Username);
		    	if (Username.isEmpty()==true || Password.isEmpty()==true)
				{
					Toast.makeText(getApplicationContext(), "Input All Fields", Toast.LENGTH_SHORT).show();
				}
		    	else if(Username.equals("Username"))
		    	{
					Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
				}	
		    	else if (c == null)
		    		Toast.makeText(getApplicationContext(), "Not Signed Up", Toast.LENGTH_SHORT).show();
		    	else
		    	{	
		    	if(c.getPass().equals(Password)){
		    		Intent i = new Intent(Screen2.this, Tabview.class);
		    		i.putExtra("the text", Username);
		    		pass.setText("");
		    		startActivity(i);
		    		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
		    		
		    		
		    	}
		    	else{
		    		Toast.makeText(getApplicationContext(), "Wrong Username/Password", Toast.LENGTH_SHORT).show();
		    	}
		    	}
		    }
		});
        z.setOnClickListener(new OnClickListener(){
        	@Override
			 public void onClick(View v){
        		Username=user.getText().toString();
				Password=pass.getText().toString();
				int len=Password.length();
				Contact a = db.getCont(Username);
				if (Username.isEmpty()==true || Password.isEmpty()==true)
				{
					Toast.makeText(getApplicationContext(), "Input All Fields", Toast.LENGTH_SHORT).show();
				}
				else if(Username.equals("Username"))
		    	{
					Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
				}	
				else if (a == null && len>=4)
				{	
					Username=Username.trim();
					Password=Password.trim();
					a= new Contact(Username, Password);
					db.addContact(a);
					Intent i2 = new Intent(Screen2.this, Tabview.class);
					i2.putExtra("the text", Username);
		    		startActivity(i2);
		    		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
				}
				else if(a==null && len<4)
				{
					Toast.makeText(getApplicationContext(), "Password should have minimum 4 characters", Toast.LENGTH_SHORT).show();
				}	
				else
				{
					Toast.makeText(getApplicationContext(), "Username Already In Use", Toast.LENGTH_SHORT).show();
				}
        	}
        });
        
        }
    
    }
   
