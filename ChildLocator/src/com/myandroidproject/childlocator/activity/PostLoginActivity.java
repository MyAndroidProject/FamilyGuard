package com.myandroidproject.childlocator.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myandroidproject.childlocator.action.DependentAction;
import com.myandroidproject.childlocator.action.UpdateService;
import com.myandroidproject.childlocator.activity.R.id;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceChild;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceParent;


public class PostLoginActivity extends Activity {

	private TextView role, userName, childName;
	private Button logOut;
	private DependentAction dependentAction;
	private SharedPreferences pref;
	private Editor editor;
	private int approle;
	private UpdateService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_login);

		role = (TextView) findViewById(id.textViewRole);
		userName = (TextView) findViewById(id.textViewUserName);
		childName = (TextView) findViewById(id.textViewChildRelated);
		logOut = (Button) findViewById(id.buttonLogOut);

		pref = getApplicationContext().getSharedPreferences(
				"localdiskchildlocator", 0);
		editor = pref.edit();
		
		childName.setText("  ");

		String appUserName = pref.getString("userName", "Unknown");
		approle = pref.getInt("logintype", 50);

		if (approle == 0) {
			role.setText("Parent Application");
			childName.setText("Child-"+pref.getString("childname", "Unknown"));
		} else if (approle == 1) {
			role.setText("Child Application");
		} else {
			role.setText("Role Unknown");
		}

		userName.setText("UserName-" + appUserName);

		logOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (approle == 0) {
					stopService(new Intent(getApplicationContext(),
							HeartBeatServiceParent.class));
				} else if (approle == 1) {
					stopService(new Intent(getApplicationContext(),
							HeartBeatServiceChild.class));
				}
				editor.putInt("loginstatus", 5);
				editor.commit();
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			if (approle == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setMessage("Enter Child UserName");

				final EditText childUserName = new EditText(this);

				builder.setView(childUserName);

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								establishRelation(childUserName.getText().toString().trim());
								dialog.cancel();
							}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();

				alert.show();

			} else {
				Toast.makeText(getApplicationContext(),
						"This is only for parent", Toast.LENGTH_LONG).show();
			}
			return true;

		case R.id.item2:
			if (approle == 0) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setMessage("Enter Child UserName");

				final EditText childUserName = new EditText(this);

				builder.setView(childUserName);

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								removeRelation(childUserName.getText().toString().trim());
								dialog.cancel();
							}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();

				alert.show();

			} else {
				/*Toast.makeText(getApplicationContext(),
						"This is only for parent", Toast.LENGTH_LONG).show();*/
				service = new UpdateService();
				service.setUserName("tutu");
				service.setLocation("paravoor");
				service.setSpeed(22);
				service.setBatteryStatus(33);
				service.doUpdateService();

			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void establishRelation(String userName){
		
		editor.putString("childname", userName);
		editor.commit();
		childName.setText("Child-"+userName);
		String parentName = pref.getString("userName", "Unknown");
		
		dependentAction = new DependentAction();
		dependentAction.setChildName(userName);
		dependentAction.setParentName(parentName);
		dependentAction.addDependent();
		
		/*
		 * 
		 * 
		 * do weservice call 
		 * */
	}
	
	public void removeRelation(String userNmae){
		
		editor.remove("childname");
		editor.commit();
		childName.setText("");
		
		/*
		 * 
		 * 
		 * do weservice call 
		 * */
		
	}

}
