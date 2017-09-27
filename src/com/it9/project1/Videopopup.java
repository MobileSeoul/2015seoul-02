package com.it9.project1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Videopopup extends Activity{

	VideoView view;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		view=(VideoView)findViewById(R.id.video);
		MediaController nc = new MediaController(this);
		nc.setMediaPlayer(view);
		//view.setVideoPath("/res/raw/movie.mp4");
		Myapplication app=(Myapplication) getApplicationContext();
		Uri video = null;
		if(app.getfirstList().getTitle().equals("화재출동")){
			video=Uri.parse("android.resource://"+getPackageName()+"/raw/movie01");
		}else if(app.getfirstList().getTitle().equals("구조출동")){
			video=Uri.parse("android.resource://"+getPackageName()+"/raw/movie02");
		}else if(app.getfirstList().getTitle().equals("구급출동")){
			video=Uri.parse("android.resource://"+getPackageName()+"/raw/movie03");
		}
		view.setMediaController(nc);
		view.setVideoURI(video);
		view.requestFocus();
		view.start();
		
		btn=(Button)findViewById(R.id.close);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
//	public AssetFileDescriptor getAssetFileDescriptor(String filePath){
//		PackageManager manager=this.getPackageManager();
//		PackageInfo info;
//		
//		try{
//			info=manager.getPackageInfo(this.getPackageName(), 0);
//		}catch(NameNotFoundException e){
//			e.printStackTrace();
//			return null;
//		}
//		String thePackageName=this.getPackageName();
//		int thePackageVer=info.versionCode;
//		String zipFilePath="/mnt/sdcard"+"/Android/obb/"+thePackageName+"/main."+thePackageVer+"."+thePackageName+".obb";
//		ZipResourceFile expansionFile=null;
//		AssetFileDescriptor afd=null;
//		try{
//			expansionFile=new ZipResourceFile(zipFilePath);
//			afd=expansionFile.getAssetFileDescriptor(filePath);
//		}catch(IOException e){e.printStackTrace();}
//	}
	
}
