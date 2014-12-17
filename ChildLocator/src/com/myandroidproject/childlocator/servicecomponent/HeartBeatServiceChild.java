package com.myandroidproject.childlocator.servicecomponent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.myandroidproject.childlocator.action.UpdateService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

public class HeartBeatServiceChild extends Service {

	public static int SERVICE_COUNT;
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	private LocationManager mlocManager = null;
	private LocationListener mlocListener;
	private Timer timer;
	private SharedPreferences preferences;
	private UpdateService service;

	private final class ServiceHandler extends Handler {

		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {

			mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			mlocListener = new MyLocationListener();
			mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
					200, mlocListener);

			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {
					Location loc = getLastKnownLocation();
					if (loc != null) {
						updateDetails(loc);
					}
				}
			}, 0, 1 * 60 * 1000);

		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		SERVICE_COUNT = 1;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mlocManager != null) {
			mlocManager.removeUpdates(mlocListener);
		}
		if (timer != null) {
			timer.cancel();
		}
		SERVICE_COUNT = 0;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		if (1 == SERVICE_COUNT) {

			Message msg = mServiceHandler.obtainMessage();
			msg.arg1 = startId;
			mServiceHandler.sendMessage(msg);
		}
		SERVICE_COUNT++;

		return Service.START_NOT_STICKY;

	}

	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {

			if (loc != null) {
				updateDetails(loc);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}

	public void updateDetails(Location loc) {

		double currentLatitude = loc.getLatitude();
		double currentLongitude = loc.getLongitude();
		JSONObject ret = getLocationInfo(currentLatitude, currentLongitude);
		JSONObject location;
		String currentLocation = null;
		try {
			location = ret.getJSONArray("results").getJSONObject(0);
			currentLocation = location.getString("formatted_address");
		} catch (JSONException e1) {

		} catch (Exception e) {

		}

		float currentBatterylevel = getBatteryLevel();
		float currentDeviceSpeed = loc.getSpeed();
		preferences = getApplicationContext().getSharedPreferences(
				"localdiskchildlocator", 0);
		String userName = preferences.getString("userName", "Unknown");
		
		service = new UpdateService();
		service.setUserName(userName);
		service.setLocation(currentLocation);
		service.setSpeed(currentDeviceSpeed);
		service.setBatteryStatus(currentBatterylevel);
		service.doUpdateService();

		/*..
		 * CALL WEBSERVICE mywebservice(currentLocation, currentDeviceSpeed,
		 * currentBatterylevel);
		 */
	}

	public float getBatteryLevel() {
		Intent batteryIntent = registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		if (level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float) level / (float) scale) * 100.0f;
	}

	private Location getLastKnownLocation() {
		LocationManager mLocationManager = (LocationManager) getApplicationContext()
				.getSystemService(LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mLocationManager.getLastKnownLocation(provider);
			if (l == null) {
				continue;
			}
			if (bestLocation == null
					|| l.getAccuracy() < bestLocation.getAccuracy()) {
				bestLocation = l;
			}
		}
		return bestLocation;
	}

	public JSONObject getLocationInfo(double lat, double lng) {

		HttpGet httpGet = new HttpGet(
				"http://maps.google.com/maps/api/geocode/json?latlng=" + lat
						+ "," + lng + "&sensor=true");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonObject;
	}

}
