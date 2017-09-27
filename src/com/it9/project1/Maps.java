package com.it9.project1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.it9.project1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements OnClickListener {

	Button road, set, cctv, wind;
	Chronometer cm;
	TextView text, chotext;
	GoogleMap gmap;
	String now;
	LinearLayout cholayout;

	ListView lv;
	DataAdapter adapter;
	ArrayList<Data> list;
	
	ImageView iv;
	boolean ivflg=true;
	View map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		Myapplication app = (Myapplication) getApplicationContext();
		road = (Button) findViewById(R.id.button4);
		set = (Button) findViewById(R.id.button5);
		cctv = (Button) findViewById(R.id.cctv);
		text = (TextView) findViewById(R.id.memo);
		wind=(Button)findViewById(R.id.wind);
		randomchange();
		now = app.getfirstList().getInfomer() + "\r\n\r\n"
				+ app.getfirstList().getIndication();
		text.setText(now);

		cm = (Chronometer) findViewById(R.id.chronometer1);
		cholayout = (LinearLayout) findViewById(R.id.cholayout);
		chotext = (TextView) findViewById(R.id.chotext);
		cm.setBase(SystemClock.elapsedRealtime());
		cm.start();
		cm.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				int time = (int) ((SystemClock.elapsedRealtime() - cm.getBase()) / 1000);
				cm.setTextColor(Color.BLACK);
				cm.setTextSize(30);
				chotext.setTextColor(Color.BLACK);
				chotext.setTextSize(30);
				if (time >= 10) {
					chotext.setTextColor(Color.RED);
					cm.setTextColor(Color.RED);
					cholayout.setBackgroundColor(Color.RED);
				}

			}
		});

		road.setOnClickListener(this);
		set.setOnClickListener(this);
		cctv.setOnClickListener(this);
		iv=(ImageView)findViewById(R.id.iv);
		iv.setVisibility(android.view.View.INVISIBLE);
		map=findViewById(R.id.map);
		gmap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		
		LatLng startingPoint = new LatLng(37.57282, 126.97888);
		gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 17));
		gmap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.fire1))
				.position(startingPoint).title(app.getfirstList().getAddress()));
		gmap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.map1))
				.position(new LatLng(37.57248, 126.97854)));
		gmap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.map1))
				.position(new LatLng(37.57239, 126.98003)));
		gmap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.map1))
				.position(new LatLng(37.57332, 126.97863)));

		lv = (ListView) findViewById(R.id.listView1);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		if (app.getfirstList().getTitle().equals("구급출동"))
			ll.setBackgroundColor(Color.BLACK);
		else
			ll.setBackgroundColor(Color.parseColor("#f9f9f9"));
		list = new ArrayList<Data>();
		final String[] names={"강북삼성병원","을지백병원","신촌세브란스","서울대병원"};
		final String[] array={"Full Bed(심정지 가능)","이송가능","MRI고장","이송가능"};
		list.add(new Data(names[0], R.drawable.red));
		list.add(new Data(names[1], R.drawable.green));
		list.add(new Data(names[2], R.drawable.red));
		list.add(new Data(names[3], R.drawable.green));
		adapter = new DataAdapter(this, android.R.layout.simple_list_item_1,
				list);
		lv.setAdapter(adapter);
		if (!app.getfirstList().getTitle().equals("구급출동")) {
			lv.setVisibility(android.view.View.GONE);
		}
		final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialog.setMessage(array[position]).setCancelable(false).setNegativeButton("닫기", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				AlertDialog log=dialog.create();
//				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//				lp.copyFrom(log.getWindow().getAttributes());
//				lp.width = 500;
//				lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				
				log.show();
//				Window window = log.getWindow();
//				window.setAttributes(lp);
				
			}
		});
	}

	private class DataAdapter extends ArrayAdapter<Data> {
		private LayoutInflater inflater;
		private ArrayList<Data> items;

		private DataAdapter(Context context, int simpleListItem1,
				ArrayList<Data> data) {
			super(context, simpleListItem1, data);
			this.items = data;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view = null;
			if (v == null) {
				view = inflater.inflate(R.layout.data, null);
			} else {
				view = v;
			}
			Data data = items.get(position);
			// final Data data=this.getItem(position);
			if (data != null) {
				TextView datatext = (TextView) view.findViewById(R.id.datatext);
				datatext.setText(data.getName());
				ImageView dataimage = (ImageView) view
						.findViewById(R.id.dataimage);
				dataimage.setImageResource(data.getImage());
			}
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		if (v == road) {
			gmap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					37.57282, 126.97888), 17));
		} else if (v == set) {
			if(ivflg==true){
				iv.setVisibility(android.view.View.VISIBLE);
				map.setVisibility(android.view.View.INVISIBLE);
				ivflg=false;
				set.setText("  지도보기  ");
			}else{
				map.setVisibility(android.view.View.VISIBLE);
				iv.setVisibility(android.view.View.INVISIBLE);
				ivflg=true;
				set.setText("위성사진보기");
			}
		} else if (v == cctv) {
			Intent i=new Intent();
			i.setClass(this, Videopopup.class);
			startActivity(i);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings1) {
			Intent i = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE, null);
			i.putExtra("return-data", true);
			startActivityForResult(i, 0);
			return true;
		}else if(id==R.id.action_settings2){
			cmenu menu=new cmenu(this);
			menu.show();
		}
		return super.onOptionsItemSelected(item);
	}
	public class cmenu extends Dialog implements OnClickListener{
		Button btn1,btn2,btn3;
		public cmenu(Context context) {
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.cmenu);
			
			btn3=(Button)findViewById(R.id.btn3);
			btn3.setOnClickListener(this);
		}


		@Override
		public void onClick(View v) {
			if(v==btn3){
				dismiss();
			}
		}
		
	}
	public void randomchange(){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					final int num=(int)((Math.random()*3)+3);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							wind.setText("풍향: 남동풍\r\n풍속: "+num+"m/s");
						}
					});
				}
			}
		});
		thread.start();
	}
}
