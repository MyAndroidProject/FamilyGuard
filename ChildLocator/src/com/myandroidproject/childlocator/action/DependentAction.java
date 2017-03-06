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

import android.os.AsyncTask;
import android.util.Log;

import com.myandroidproject.childlocator.properties.Constants;

public class DependentAction {
	
	private String childName,parentName;
	private static InputStream is = null;
	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	
	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	public void addDependent()
	{
		new Dependentwebservice().execute();
	}

	private class Dependentwebservice extends AsyncTask<Void, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			 int status = 0;
				JSONObject json_data;
				try {
					json_data = new JSONObject(result);
					for (int i = 0; i < json_data.length(); i++) {

						if (Integer.parseInt(json_data.getString("status")) == 200) {
							 status = 200;
							
						}
						else
						status = Integer.parseInt(json_data.getString("status"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 if (status == 200) {
					    Log.e("Dependent insert status", Constants.SUCCESS);
					    
					   }else{
						   
							   Log.e("Dependent insert status",String.valueOf(status)+" "+Constants.WEB_SERVICE_ERROR);
						 
					 
					   }
				
			}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String json = "";
			List<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
			// params.add(new BasicNameValuePair("tag", login_tag));
			params1.add(new BasicNameValuePair("childName", getChildName()));
			params1.add(new BasicNameValuePair("parentName", getParentName()));
			

			try {
				// defaultHttpClient
				HttpPost httpPost = null;

				DefaultHttpClient httpClient = new DefaultHttpClient();
				
					httpPost = new HttpPost(Constants.ADD_Dependent);
				Log.e("Service url", Constants.ADD_Dependent);
				
				
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
