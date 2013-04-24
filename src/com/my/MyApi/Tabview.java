package com.my.MyApi;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
 
public class Tabview extends TabActivity {
    // TabSpec Names
    private static final String ALL_SPEC = "Recents";
    private static final String USER_SPEC = "Search";
    private static final String TITLE_SPEC = "Add";
    private static final String PROFILE_SPEC= "Settings";
    private static final String SPEND_SPEC= "Summary";
    String name;
    Bundle b;
    TabHost tabHost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
         tabHost = getTabHost();
 
        // Inbox Tab
        TabSpec allSpec = tabHost.newTabSpec(ALL_SPEC);
        // Tab Icon
        name=getIntent().getExtras().getString("the text");
        allSpec.setIndicator(ALL_SPEC, getResources().getDrawable(R.drawable.recent));
        Intent inboxIntent = new Intent(Tabview.this,ViewAll.class);
        inboxIntent.putExtra("the text", name);
        // Tab Content
        allSpec.setContent(inboxIntent);
 
        // Outbox Tab
        TabHost.TabSpec userSpec = tabHost.newTabSpec(USER_SPEC);
        userSpec.setIndicator(USER_SPEC, getResources().getDrawable(R.drawable.search));
        Intent outboxIntent = new Intent(Tabview.this, ViewUser.class);
        outboxIntent.putExtra("the text", name);
        userSpec.setContent(outboxIntent);
 
        // Profile Tab
        TabHost.TabSpec titleSpec = tabHost.newTabSpec(TITLE_SPEC);
        titleSpec.setIndicator(TITLE_SPEC, getResources().getDrawable(R.drawable.add));
        Intent profileIntent = new Intent(Tabview.this, ViewTitle.class);
        profileIntent.putExtra("the text", name);
        titleSpec.setContent(profileIntent);
 
        TabHost.TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.settings));
        Intent chkIntent = new Intent(Tabview.this, profile.class);
        chkIntent.putExtra("the text", name);
        profileSpec.setContent(chkIntent);
        
        TabHost.TabSpec p = tabHost.newTabSpec(SPEND_SPEC);
        p.setIndicator(SPEND_SPEC, getResources().getDrawable(R.drawable.summary));
        Intent c= new Intent(Tabview.this, Intro.class);
        c.putExtra("the text", name);
        p.setContent(c);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(allSpec);
        tabHost.addTab(p);
        tabHost.addTab(titleSpec); 
        tabHost.addTab(userSpec);
        tabHost.addTab(profileSpec);
        setTabColor(tabHost);
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if( tabId.equals(SPEND_SPEC) ){
                	Intro.self.onCreate(b);
                	setTabColor(tabHost);
                }
                else if(tabId.equals(ALL_SPEC))
                {
                	ViewAll.self.onCreate(b);
                	setTabColor(tabHost);
                }
                else if(tabId.equals(TITLE_SPEC)){
                	setTabColor(tabHost);
                }
                else if(tabId.equals(USER_SPEC))
                {
                	ViewUser.self.onCreate(b);
                	setTabColor(tabHost);
                }
                else if(tabId.equals(PROFILE_SPEC))
                {
                	profile.self.onCreate(b);
                	setTabColor(tabHost);
                }
            }
        });


        }
        
    public static void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#178332")); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#08963a")); // selected
    }
    }


