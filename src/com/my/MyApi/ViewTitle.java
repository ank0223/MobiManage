package com.my.MyApi;

import java.util.Calendar;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ViewTitle extends Activity implements OnClickListener {
    private int PICK_CONTACT = 0;
	/** Called when the activity is first created. */
Button b,b1,b2,b3,b4,b5,b6,butview,viewgroup;
MyDatabase db;
TextView t,t1,t2;
EditText e,e1,e2,x,edi;
LinearLayout l,l1,l2,l3,l4; 
DatePicker d;
private int year,maxyear;
private int month,maxmonth;
private int day,maxday;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewtitle);
       b1=(Button)findViewById(R.id.addano);
       b2=(Button)findViewById(R.id.done);
       e2 = (EditText)findViewById(R.id.userstext);
       e2.setText("Self");
       b1.setOnClickListener(this);
       b2.setOnClickListener(this);
       e2.setOnClickListener(this);
       db = new MyDatabase(this); 
       setCurrentDateOnView();
       
    }
	public void onClick(View v){
		if(v.getId()== R.id.userstext){
            Intent intent = new Intent(Intent.ACTION_PICK);
             intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
             startActivityForResult(intent, PICK_CONTACT);

       }
		
		else if(v.getId()==R.id.addano)
	{
		e=(EditText)findViewById(R.id.titletext);
		e1=(EditText)findViewById(R.id.amounttext);
		e2=(EditText)findViewById(R.id.userstext);
		t=(TextView)findViewById(R.id.title);	
		edi=(EditText)findViewById(R.id.editt);
		if ((e1.getText().toString().equals("")) ||(e2.getText().toString().equals(""))||(e.getText().toString().equals(""))){
			Toast.makeText(getApplicationContext(), "Please Provide All the details", Toast.LENGTH_SHORT).show();
		
		}
		else if(chkdate()==0)
			Toast.makeText(getApplicationContext(), "Enter Valid Date", Toast.LENGTH_SHORT).show();
		else{
			db.addData( getIntent().getExtras().getString("the text"), e.getText().toString() ,e1.getText().toString(),e2.getText().toString(),day,month,year,edi.getText().toString());
		e1.setText("");
		e2.setText("Self");
		}
	}	
	else if(v.getId() ==R.id.done)
	{
		x=(EditText)findViewById(R.id.editText1);
		e=(EditText)findViewById(R.id.titletext);
		e1=(EditText)findViewById(R.id.amounttext);
		e2=(EditText)findViewById(R.id.userstext);
		t=(TextView)findViewById(R.id.title);	
		e=(EditText)findViewById(R.id.titletext);
		edi=(EditText)findViewById(R.id.editt);
		int c=0;
		if ((e1.getText().toString().isEmpty() == false)&&(e2.getText().toString().isEmpty() == false)&&(e.getText().toString().isEmpty() == false))
		{	
			if(chkdate()==0)
			{	
				Toast.makeText(getApplicationContext(), "Enter Valid Date", Toast.LENGTH_SHORT).show();
				c=1;
			}	
				else
			{		
				db.addData( getIntent().getExtras().getString("the text"), e.getText().toString() ,e1.getText().toString(),e2.getText().toString(),day,month,year,edi.getText().toString());
			}
		}
		if (c==0)
		{	
		e.setText("");
		e1.setText("");
		e2.setText("");
		String Username =getIntent().getExtras().getString("the text");
		Intent i = new Intent(ViewTitle.this, Tabview.class);
		i.putExtra("the text", Username);
		startActivity(i);
		overridePendingTransition(R.anim.push_left,R.anim.push_up_out);
		}	
		
	}
			
	}
	public void setCurrentDateOnView() {
		d = (DatePicker) findViewById(R.id.pickdate);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		d.init(year, month, day, null);
	}
	public int chkdate()
	{
		d = (DatePicker) findViewById(R.id.pickdate);
		final Calendar c = Calendar.getInstance();
		year=d.getYear();
		month=d.getMonth()+1;
		day=d.getDayOfMonth();
		maxyear = c.get(Calendar.YEAR);
		maxmonth = c.get(Calendar.MONTH)+1;
		maxday = c.get(Calendar.DAY_OF_MONTH);
		if (year<maxyear)
			return 1;
		else if(year==maxyear)
		{
			if(month<maxmonth)
				return 1;
			else if(month==maxmonth)
			{
				if(day<=maxday)
					return 1;
			}	
		}
		return 0;
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) 
    {

      if (requestCode == PICK_CONTACT&& intent != null)
      {         
          Cursor cursor =  managedQuery(intent.getData(), null, null, null, null);
          cursor.moveToNext();
         // String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.));
          int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
          String  name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)); 
          e2.setText(name);
          
      }
    }
}
