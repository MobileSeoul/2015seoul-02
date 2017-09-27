package com.it9.project1;

import com.it9.project1.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialog extends Activity implements OnClickListener{
	
	Button btn1,btn2;
	TextView text,title;
	LinearLayout bg;
	String user="-1";
	String ttitle,infomer,address,tel,number,txt;
	Toast toast;
	SoundPool pool;
	int sound1,sound2,sound3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom);
		setFinishOnTouchOutside(false);
		Myapplication app=(Myapplication)getApplicationContext();
		HistoryData data=app.getfirstList();
		ttitle=data.getTitle();
		infomer=data.getInfomer();
		address=data.getAddress();
		if(infomer.contains("교통사고/서대문")){
			//Log.i("test", String.valueOf(infomer.contains("교통사고/서대문")));
			infomer=infomer.replace("사고/서대", "사고/\n서대");
		}
		tel=data.getRepoter();
		number=data.getNumber();
		txt=number+"\n\n"+address+infomer+"\n\n\n"+"전화번호: "+tel;
		
		title=(TextView)findViewById(R.id.title);
		title.setText(ttitle);
		text=(TextView)findViewById(R.id.text);
		text.setText(txt);
		text.setTextColor(Color.BLACK);
		btn1=(Button)findViewById(R.id.call);
		btn2=(Button)findViewById(R.id.road);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		bg=(LinearLayout)findViewById(R.id.bg);
		
		if(app.getfirstList().getTitle().equals("화재출동")){
			bg.setBackgroundColor(Color.RED);
			title.setTextColor(Color.RED);
		}else if(app.getfirstList().getTitle().equals("구조출동")){
			bg.setBackgroundColor(Color.parseColor("#ff7f00"));
			title.setTextColor(Color.parseColor("#ff7f00"));
		}else if(app.getfirstList().getTitle().equals("구급출동")){
			bg.setBackgroundColor(Color.GREEN);
			title.setTextColor(Color.GREEN);
		}
		if(app.getUser().equals("1")){
			bg.setBackgroundColor(Color.BLACK);
			title.setTextColor(Color.BLACK);
			text.setTextColor(Color.WHITE);
		}
		
		toast=new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(0);
//		if(app.getUser().equals("0")&&app.getfirstList().getTitle().equals("화재출동")){
//			myThread thread=new myThread();
//			thread.start();
//		}
		pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//		sound1=pool.load(this, R.raw.sound1, 1);
//		sound2=pool.load(this, R.raw.sound2, 1);
//		sound3=pool.load(this, R.raw.sound3, 1);
		if(app.getUser().equals("0")){
			if(app.getfirstList().getTitle().equals("화재출동")){
				sound1=pool.load(this, R.raw.sound1, 1);
				runSound(1);
			}else if(app.getfirstList().getTitle().equals("구조출동")){
				sound2=pool.load(this, R.raw.sound2, 1);
				runSound(2);
			}else if(app.getfirstList().getTitle().equals("구급출동")){
				sound3=pool.load(this, R.raw.sound3, 1);
				runSound(3);
			}
		}
	}
	public void runSound(int num){
		switch(num){
		case 1://pool.play(sound1, 1, 1, 0, 1, 1);break;
			pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
					soundPool.play(sound1, 1, 1, 0, 0, 1);
				}
			});break;
		case 2://pool.play(sound2, 1, 1, 0, 0, 1.5f);break;
			pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
					soundPool.play(sound2, 1, 1, 0, 0, 1.5f);
				}
			});break;
		case 3://pool.play(sound3, 1, 1, 0, 0, 1.5f);break;
			pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
					soundPool.play(sound3, 1, 1, 0, 1, 1);
				}
			});break;
		}
	}
	public class myThread extends Thread{
		@Override
		public void run() {
			LayoutInflater inflater = getLayoutInflater();
			View lo=inflater.inflate(R.layout.popup, (ViewGroup)findViewById(R.id.poplayout));
			lo.setBackgroundColor(Color.parseColor("#ffffff"));
			ImageView image=(ImageView)lo.findViewById(R.id.popimage);
			image.setImageResource(R.drawable.circle);
			TextView text=(TextView)lo.findViewById(R.id.poptext);
			text.setTextColor(Color.BLACK);
			text.setText("2");
			SystemClock.sleep(5000);
			
			
			toast.setView(lo);
			toast.show();
			
			SystemClock.sleep(15000);
			text.setText("5");
			toast.show();
			
			finish();
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v==btn1){
			Intent in=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel));
			startActivity(in);
		}else if(v==btn2){
			Intent intent=new Intent(getApplicationContext(),Maps.class);
			String now=getIntent().getStringExtra("now");
			intent.putExtra("now", now);
			startActivity(intent);
			finish();
		}
	}



	
	
	
	
}
