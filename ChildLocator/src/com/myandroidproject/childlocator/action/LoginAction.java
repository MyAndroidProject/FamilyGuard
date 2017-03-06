package com.myandroidproject.childlocator.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;


import com.myandroidproject.childlocator.properties.Constants;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceChild;
import com.myandroidproject.childlocator.servicecomponent.HeartBeatServiceParent;

public class LoginAction {

	private static InputStream is = null;
	private String username, password;
	private int role;
	private Context context;

	private SharedPreferences pref;
	private Editor editor;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void doRegister() {
		new Loginwebservice().execute("register");
		//postLogin();
	}

	public void doLogin() {
		// if(this.username.equalsIgnoreCase("nikhil") &&
		// this.password.equalsIgnoreCase("tutu"))

		new Loginwebservice().execute("login");

		// return json;

		// return Constants.LOGIN_ERROR;
	}
	
	private void writeToSharedPreferences(){
		
		pref = getContext().getSharedPreferences("localdiskchildlocator", 0);
		editor = pref.edit();
Log.e("writeToSharedPreferences", "localdiskchildlocator");
		editor.putString("userName", getUsername());
		editor.putString("password", getPassword());
		editor.putInt("logintype", getRole());
		editor.putInt("loginstatus", 99);
		
		editor.commit();

	}

	public void postLogin() {

		writeToSharedPreferences();
		if (getRole() == 0) {
			getContext().startService(new Intent(getContext(), HeartBeatServiceParent.class));
		}else if(getRole() == 1){
			getContext().startService(new Intent(getContext(), HeartBeatServiceChild.class));
		}
		

		
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	private class Loginwebservice extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 int loginStatus = 0;
			JSONObject json_data;
			try {
				json_data = new JSONObject(result);
				for (int i = 0; i < json_data.length(); i++) {

					if (Integer.parseInt(json_data.getString("status")) == 200) {
						 loginStatus = 200;
						setUsername(json_data.getString("username"));
						setPassword(json_data.getString("password"));
						setRole(Integer.parseInt(json_data.getString("role")));
					}
					else
						loginStatus = Integer.parseInt(json_data.getString("status"));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if (loginStatus == 200) {
				    Log.e("parsing", getUsername() + getPassword() + getRole());
				    postLogin();
				   }else{
					   if (loginStatus == 500) 
						   Log.e("RegisterError",Constants.Username_Password_Null);
					   else if (loginStatus == 501)
						   Log.e("RegisterError", Constants.Username_Exists);
					else if (loginStatus == 502)
							   Log.e("RegisterError", Constants.DBError);
					   
				 
				   }
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String json = "";
			List<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
			// params.add(new BasicNameValuePair("tag", login_tag));
			params1.add(new BasicNameValuePair("username", getUsername()));
			params1.add(new BasicNameValuePair("password", getPassword()));

			try {
				// defaultHttpClient
				HttpPost httpPost = null;

				DefaultHttpClient httpClient = new DefaultHttpClient();
				if (params[0].toString().equalsIgnoreCase("login"))
				{
					httpPost = new HttpPost(Constants.LOGIN_URL);
				Log.e("Service url",params[0].toString()+" "+ Constants.LOGIN_URL);
				}
				else {
					params1.add(new BasicNameValuePair("role", String
							.valueOf(getRole())));
					httpPost = new HttpPost(Constants.REGISTER_URL);
					Log.e("Service url",params[0].toString()+" "+ Constants.REGISTER_URL);
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params1));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();
				json = sb.toString();
				Log.e("JSON", json);
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}

			return json;
		}
	}

}
