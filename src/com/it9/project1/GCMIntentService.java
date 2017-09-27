package com.it9.project1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
		super("762804241082");
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String title=intent.getStringExtra("title");
		String address=intent.getStringExtra("address");
		String infomer=intent.getStringExtra("infomer");
		String indication=intent.getStringExtra("indication");
		String repoter=intent.getStringExtra("repoter");
		String number=intent.getStringExtra("number");
		try {
			title=URLDecoder.decode(title, "UTF-8");
			address=URLDecoder.decode(address, "UTF-8");
			infomer=URLDecoder.decode(infomer, "UTF-8");
			indication=URLDecoder.decode(indication, "UTF-8");
			repoter=URLDecoder.decode(repoter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Intent popupIntent = new Intent(getApplicationContext(), CustomDialog.class);
		Myapplication app=(Myapplication)getApplicationContext();
		app.addList(title, address, infomer, indication, repoter, number);
		
		PendingIntent pie= PendingIntent.getActivity(getApplicationContext(), 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
		try {
			pie.send();
		} catch (CanceledException e) {
			Log.i("service error", e.getMessage());
		}
		
	}
	

	@Override
	protected void onRegistered(Context arg0, String regId) {
		Log.e("키를 등록합니다", regId);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.e("키를 제거합니다","제거되었습니다.");
	}

}
