package com.myandroidproject.childlocator.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.myandroidproject.childlocator.activity.R.id;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceChild;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceParent;


public class PostLoginActivity extends Activity {

	TextView role, userName;
	Button logOut;
	
	private SharedPreferences pref;
	private Editor editor;
	private int approle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_login);
		
		role = (TextView) findViewById(id.textViewRole);
		userName = (TextView) findViewById(id.textViewUserName);
		logOut = (Button) findViewById(id.buttonLogOut);
		
		pref = getApplicationContext().getSharedPreferences("localdiskchildlocator",0);
		editor = pref.edit();
		
		String appUserName = pref.getString("userName", "Unknown");
		approle = pref.getInt("logintype", 50);
		
		if (approle == 0) {
			role.setText("Parent Application");
		} else if(approle == 1){
			role.setText("Child Application");
		}else{
			role.setText("Role Unknown");
		}
		
		userName.setText("UserName-"+appUserName);
		
		logOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (approle == 0) {
					stopService(new Intent(getApplicationContext(), HeartBeatServiceParent.class));
				} else if(approle == 1){
					stopService(new Intent(getApplicationContext(), HeartBeatServiceChild.class));
				}
				editor.putInt("loginstatus",5);
				editor.commit();
				finish();
			}
		});
	}

}
