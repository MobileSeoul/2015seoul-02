package com.it9.project1;

import com.it9.project1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Thread th=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				page();
			}
		});
		th.start();
		
	}
	public void page(){
		Intent i=new Intent(getApplicationContext(),SecondActivity.class);
		startActivity(i);
		finish();
	}
}
