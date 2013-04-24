package com.my.MyApi;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Object> {
  private final Context context;
  private final ArrayList<HashMap<String, String>> values;

  public MyAdapter(Context context,ArrayList<HashMap<String, String>> values) {
	  super(context, R.layout.list_item);
	  this.context = context;
	  this.values = values;
  }
  public int getCount() {
      return values.size();
  }
  public Object getItem(int position) {
      return position;
  }

  public long getItemId(int position) {
      return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    //View row = inflater.inflate(R.layout.profile, parent, false);
    TextView textView = (TextView)rowView.findViewById(R.id.label);
    EditText edi=(EditText)rowView.findViewById(R.id.notes);
    TextView imageView = (TextView)rowView.findViewById(R.id.label1);
    TextView details = (TextView)rowView.findViewById(R.id.detail);
    TextView date = (TextView)rowView.findViewById(R.id.date);
    TextView currr=(TextView)rowView.findViewById(R.id.curr);
    textView.setText(values.get(position).get("userpay"));
    //EditText cu=(EditText)row.findViewById(R.id.curset);
    currr.setText(values.get(position).get("chk"));
    imageView.setText(values.get(position).get("amount"));
    edi.setText(values.get(position).get("notes"));
    details.setText("Title: "+ values.get(position).get("title"));
    String day=values.get(position).get("day");
    String mon=values.get(position).get("month");
    String yea=values.get(position).get("year");
    date.setText(day+"-"+mon+"-"+yea);
    
    // Change the icon for Windows and iPhone
    return rowView;
  }
  
  
} 