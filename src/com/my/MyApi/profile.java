package com.my.MyApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.*;

public class profile extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
Button b,b1,b2,b3,b4,b5,b6,butview,viewgroup,b7;
public static profile self;
MyDatabase db;
TextView t,t1,t2;
EditText e,e1,e2,x;
LinearLayout l,l1,l2,l3,l4; 
DatePicker d;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TranslateAnimation as= new TranslateAnimation(0,0,100, 0);
        as.setDuration(500);
    	as.setFillAfter(true);
        self=this;
       b5=(Button)findViewById(R.id.reacc);
       b5.startAnimation(as);
       b6=(Button)findViewById(R.id.delacc);
       b6.startAnimation(as);
       b7=(Button)findViewById(R.id.setcur);
       b7.startAnimation(as);
       b5.setOnClickListener(this);
       b6.setOnClickListener(this);
       b7.setOnClickListener(this);
       db = new MyDatabase(this); 
    }
	public void onClick(View v){	
	if(v.getId() ==R.id.delacc)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Please Note").setMessage("This will delete your account with all your data... \nDo you want to continue?");
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
 
            // Write your code here to invoke YES event
            	db.deluser( getIntent().getExtras().getString("the text"));
            	Intent i = new Intent(profile.this, Screen2.class);
        		startActivity(i);
        		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
            
            }
        });
 
        // Setting Negative "NO" Button
		
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
            
            dialog.cancel();
            }
        });
        alertDialog.show();
	}
	else if(v.getId() ==R.id.reacc)
	{
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Please Note").setMessage("This will delete all your data... \nDo you want to continue?");
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
 
            // Write your code here to invoke YES event
            	db.reuser( getIntent().getExtras().getString("the text"));
            	Intent i = new Intent(profile.this, Tabview.class);
        		i.putExtra("the text", getIntent().getExtras().getString("the text"));
        		startActivity(i);
        		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
            
            }
        });
 
        // Setting Negative "NO" Button
		
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
            
            dialog.cancel();
            }
        });
        alertDialog.show();
	}
	else if(v.getId() ==R.id.setcur)
	{
		Button b=(Button)findViewById(R.id.setcur);
		if(b.getText().toString().equals("Change Account Currency"))
		{
			EditText c=(EditText)findViewById(R.id.curset);
			c.setText(db.retcur(getIntent().getExtras().getString("the text")));
			c.setVisibility(View.VISIBLE);
			b.setText("Set Account Currency");
		}
		else
		{
			EditText c=(EditText)findViewById(R.id.curset);
			c.setVisibility(View.GONE);
			b.setText("Change Account Currency");
			if(c.getText().toString().isEmpty()==true)
				c.setText("Rs.");
			db.chgcur( getIntent().getExtras().getString("the text"),c.getText().toString());
			Intent i = new Intent(profile.this, Tabview.class);
    		i.putExtra("the text", getIntent().getExtras().getString("the text"));
    		startActivity(i);
    		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
		}
		
	}
	}
		
	}

