package com.myandroidproject.childlocator.activity;

import com.myandroidproject.childlocator.action.LoginAction;
import com.myandroidproject.childlocator.properties.Constants;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText username, password;
	private Button register;
	private LoginAction action;
	private String user, pass;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private int radio;

	public void registerAction() {

		action.setUsername(user);
		action.setPassword(pass);
		action.setRole(radio);

		action.doRegister();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		register = (Button) findViewById(R.id.buttonLogOut);

		action = new LoginAction();
		action.setContext(this);

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				user = username.getText().toString().trim();
				pass = password.getText().toString();

				int selectedId = radioGroup.getCheckedRadioButtonId();

				radioButton = (RadioButton) findViewById(selectedId);

				if (radioButton.getText().toString().equalsIgnoreCase("parent"))
					radio = 0;
				else
					radio = 1;

				if (user.length() > 5 && pass.length() > 5) {

					registerAction();
					finish();

				} else {
					Toast.makeText(
							getApplicationContext(),
							Constants.Username_Password_length,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

}
