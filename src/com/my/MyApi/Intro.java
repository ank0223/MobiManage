package com.my.MyApi;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
public class Intro extends Activity{
	MyDatabase db;
	TextView dd,mm,yy,cc,ee;
	int maxyear,maxmonth,maxday;
	public static Intro self;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self=this;
        // storing string resources into Array
        setContentView(R.layout.intro);
        db = new MyDatabase(this);
        final Calendar c = Calendar.getInstance();
        maxyear = c.get(Calendar.YEAR);
		maxmonth = c.get(Calendar.MONTH)+1;
		maxday = c.get(Calendar.DAY_OF_MONTH);
        dd=(TextView)findViewById(R.id.dd);
        mm=(TextView)findViewById(R.id.mm);
        yy=(TextView)findViewById(R.id.yy);
        cc=(TextView)findViewById(R.id.cc);
        ee=(TextView)findViewById(R.id.ee);
        String s=db.retcur(getIntent().getExtras().getString("the text"));
        dd.setText(s+" "+db.retdaysum(getIntent().getExtras().getString("the text"),maxday,maxmonth,maxyear));
        mm.setText(s+" "+db.retmonthsum(getIntent().getExtras().getString("the text"),maxmonth,maxyear));
        yy.setText(s+" "+db.retyearsum(getIntent().getExtras().getString("the text"),maxyear));
        String s1=db.rettotal(getIntent().getExtras().getString("the text"));
        String s2=db.retcredit(getIntent().getExtras().getString("the text"));
        int z=Integer.parseInt(s2)-Integer.parseInt(s1);
        cc.setText(s+" "+ Integer.toString(z));
        ee.setText(s+" "+ s1);
	}
}        
        
        	
      
    
    
