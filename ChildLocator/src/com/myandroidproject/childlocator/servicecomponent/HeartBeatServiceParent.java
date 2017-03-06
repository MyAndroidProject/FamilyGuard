package com.myandroidproject.childlocator.servicecomponent;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class HeartBeatServiceParent extends Service {

	public static int SERVICE_COUNT;
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	private Timer timer;

	private final class ServiceHandler extends Handler {

		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {

			timer = new Timer();
			timer.scheduleAtFixedRate( new TimerTask() {
	    		private Handler alertUI = new Handler(){
	    			@Override
	    			public void dispatchMessage(Message msg) {
	    			    super.dispatchMessage(msg);
	    			    //show_dialog();
	    			    
	    			}
	    			};
	    			public void run() { 
	    			try {
	    				alertUI.sendEmptyMessage(0);
	    			} catch (Exception e) {e.printStackTrace(); }
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



}
