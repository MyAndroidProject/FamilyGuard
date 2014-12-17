package com.myandroidproject.childlocator.activity;

import com.myandroidproject.childlocator.action.LoginAction;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText username, password;
	private Button login, register;
	private LoginAction action;
	private String user, pass;

	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);

		action = new LoginAction();
		action.setContext(this);

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				user = username.getText().toString();
				action.setUsername(user);
				pass = password.getText().toString();
				action.setPassword(pass);
				// str =action.doLogin();

				action.doLogin();
				checkLoginStatus();

			}
		});
		
		checkLoginStatus();

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		checkLoginStatus();
	}

	public void register() {

		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	private void checkLoginStatus() {

		pref = getApplicationContext().getSharedPreferences(
				"localdiskchildlocator", 0);
		int statusTemp = pref.getInt("loginstatus", 0);

		if (statusTemp == 99) {

			Intent intent = new Intent(getApplicationContext(),
					PostLoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
