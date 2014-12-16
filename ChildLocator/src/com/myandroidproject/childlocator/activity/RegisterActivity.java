package com.myandroidproject.childlocator.activity;

import com.myandroidproject.childlocator.action.LoginAction;
import com.nikhil.childlocator.activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;



public class RegisterActivity extends Activity {
	
	private EditText username,password;
	private Button register;
	private LoginAction action;
	private String user,pass; 
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	
	public void registerAction()
	{
		 user= username.getText().toString();
			action.setUsername(user);
			pass = password.getText().toString();
			action.setPassword(pass);
			username.setText("");
			password.setText("");
			
			
			int selectedId = radioGroup.getCheckedRadioButtonId();
			 
			     radioButton = (RadioButton) findViewById(selectedId);

			    if( radioButton.getText().toString().equalsIgnoreCase("parent"))
			    	action.setRole(0);
			    else
			    	action.setRole(1);
			     
			  action.doRegister();
			
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		username =(EditText)findViewById(R.id.editText1);
		password =(EditText)findViewById(R.id.editText2);
		register =(Button) findViewById(R.id.button1);
		 action = new LoginAction();	
		 action.setContext(this);
		 radioGroup =(RadioGroup)findViewById(R.id.radioGroup1);
		
		 register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
						
					registerAction();
						
					
				}
			});
		 
		 
	}

	
}
