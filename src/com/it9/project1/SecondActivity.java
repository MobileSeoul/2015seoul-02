package com.it9.project1;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class SecondActivity extends TabActivity implements OnClickListener{
	Intent intent;
	TabHost th;
	String regId;
	Button push1, push2, push3;
	ListView lv;
	ArrayList<Search> list;
	searchAdapter sa;
	SearchView sv;
	Switch sw1;
	ImageView iv;

	String title = "";// 화재출동/구조출동/구급출동
	String address = "";// 주소
	String infomer = "";// 신고내용
	String indication = "";// 지시사항
	String repoter = "";// 신고자 전화번호
	String number="";
	
	
	private static final String PROJECT_ID = "762804241082";
	private Context appContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		final Myapplication app = (Myapplication) getApplicationContext();
		if(app.getSize()==0){
			app.addList("화재출동", "종로구 세종로 100 광화문kt사옥", "기업은행 광화문kt출장소에서 화재\r\n신고자는 기업은행 직원", "종로 지휘차, 탱크차,펌프차, 구조대100 출동하시오 ", "010-000-0000","재해번호6666666");
			app.addList("구조출동", "종로구 공평동 70", "엘리베이터 5명 갇힘\r\n신고자는 본인", "종로 구조대100 출동하시오", "010-000-0000","재해번호6666777");
			app.addList("구급출동", "종로구 경운동 66-4 아트뱅크하우스 3층", "중국도자박물관에 의식잃은 환자 발생\r\n신고자는 박물관 관리인", "종로 구급차 출동하시오", "010-000-0000","재해번호6666778");
		}
		th = getTabHost();
		TabSpec ts2 = th.newTabSpec("tab2").setIndicator("설정");
		ts2.setContent(R.id.tab2);
		th.addTab(ts2);
		TabSpec ts3 = th.newTabSpec("tab3").setIndicator("출동지령");
		ts3.setContent(R.id.tab3);
		th.addTab(ts3);
		TabSpec ts1 = th.newTabSpec("tab1").setIndicator("검색");
		ts1.setContent(R.id.tab1);
		th.addTab(ts1);
		th.setCurrentTab(1);

		for (int i = 0; i < th.getTabWidget().getChildCount(); i++) {
			LinearLayout relLayout = (LinearLayout) th.getTabWidget()
					.getChildAt(i);
			TextView tv = (TextView) relLayout.getChildAt(1);
			// tv.setTextSize나 tv.setTextColor 혹은 tv.setTextAppearance를 호출.
			tv.setTextSize(20);
		}

		imageChange();
		
		initialize();
		startGCM();
		app.setUserid(regId);

		sw1 = (Switch) findViewById(R.id.switch1);
		if(app.getUser().equals("0"))sw1.setChecked(true);
		else sw1.setChecked(false);
		sw1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					app.setUser("0");
				else
					app.setUser("1");
			}
		});
		iv=(ImageView)findViewById(R.id.IVnormal);
		push1 = (Button) findViewById(R.id.push1);
		push2 = (Button) findViewById(R.id.push2);
		push3 = (Button) findViewById(R.id.push3);
		push1.setOnClickListener(this);
		push2.setOnClickListener(this);
		push3.setOnClickListener(this);

		String[] spinerist = getResources().getStringArray(R.array.car);
		String[] spinerist2= getResources().getStringArray(R.array.set);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, spinerist);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, spinerist2);
		Spinner obj = (Spinner) findViewById(R.id.spinner1);
		Spinner obj2 = (Spinner) findViewById(R.id.spinner2);
		obj.setAdapter(adapter);
		obj2.setAdapter(adapter2);
		obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		lv = (ListView) findViewById(R.id.searchlist);
		list = new ArrayList<Search>();
		list.clear();
		Log.i("for", String.valueOf(app.getSize()));
		for (int i = 0; i < app.getSize(); i++) {
			list.add(new Search(app.getList(i).getTitle(), app.getList(i)
					.getAddress()));
		}
		sa = new searchAdapter(this, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(sa);

		sv = (SearchView) findViewById(R.id.searchview);
		sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				list.clear();
				for (int i = 0; i < app.getSize(); i++) {
					if (app.getList(i).getTitle().contains(query)
							|| app.getList(i).getAddress().contains(query))
						list.add(new Search(app.getList(i).getTitle(), app
								.getList(i).getAddress()));
				}
				sa.notifyDataSetChanged();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
//				list.clear();
//				for (int i = 0; i < app.getSize(); i++) {
//					list.add(new Search(app.getList(i).getTitle(), app.getList(
//							i).getAddress()));
//				}
//				sa.notifyDataSetChanged();
				return false;
			}
		});
		TabWidget tabWidget = (TabWidget) th.getTabWidget();
		View tabwidgetview = (View) tabWidget.getChildAt(2);
		tabwidgetview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				list.clear();
				for (int i = 0; i < app.getSize(); i++) {
					list.add(new Search(app.getList(i).getTitle(), app.getList(
							i).getAddress()));
				}
				sa.notifyDataSetChanged();
				return false;
			}
		});
		
		
		
	}

	@Override
	public void onClick(View v) {
		if (v == push1) {
			title = "화재출동";
			address = "종로구 동승동 1-91번지/가양빌라나동/";
			infomer = "3층건물/지붕에서불꽃과연기/\n신고자는행인/신고건수3건";
			indication = "출동차량 : 총18대\n출동인원 : 총56명";
			repoter = "010-5196-2007";
			number="재해번호6733324";
			sendpush();
		} else if (v == push2) {
			title = "구조출동";
			address = "종로구 신문로1가 163번지/기지국/";
			infomer = "교통사고/서대문->광화문방향/탑승자2명상태파악안됨/\n신고자는운전자";
			indication = "출동대  :  종로구조대 100, 200\n                구급차 6-1\n출동인원 : 11명";
			repoter = "010-5196-2007";
			number="재해번호6733546";
			sendpush();
		} else if (v == push3) {
			title = "구급출동";
			address = "종로구 경운동 90-3번지/종로3가/버스장류장/ ";
			infomer = "버스와 사람 추돌/\n의식,호흡 없음/신고자는 아들";
			indication = "출동대  :  종로구급대 6-1\n                서대문구급대 6-4\n출동인원 : 6명";
			repoter = "010-5196-2007";
			number="재해번호6732459";
			sendpush();
		}
	}
	public void imageChange(){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						iv.setImageResource(R.drawable.normal2);
					}
				});
			}
		});
		thread.start();
	}
	public void sendpush() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Myapplication app = (Myapplication) getApplicationContext();
				try {
					Sender sender = new Sender(
							"AIzaSyBJP2NI1rZ7UjdrgX7Au-poTcSn6w2Qn64");
					Message message = new Message.Builder()
							.addData("title", title)
							.addData("address", address)
							.addData("infomer", infomer)
							.addData("indication", indication)
							.addData("repoter", repoter)
							.addData("number",number).build();
					// Log.i("id", regId);
					Result result = sender.send(message, app.getUserid(), 3);
					if (result.getMessageId() != null) {
						Log.e("push", "Send success");
						// System.out.println("Send success");
					} else {
						String error = result.getErrorCodeName();
						// System.out.println(error);
						Log.e("push failed", error);
					}
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println(err);
					// Log.e("sendpush failed", err);
				}
			}
		});
		thread.start();
	}

	private void initialize() {
		appContext = getApplicationContext();
	}

	private void startGCM() {
		try {
			GCMRegistrar.checkDevice(appContext);
		} catch (Exception e) {
			Log.e("MainActivity", "This device can't use GCM");
			return;
		}
		regId = GCMRegistrar.getRegistrationId(appContext);
		if (CommonUtils.isEmpty(regId)) {
			GCMRegistrar.register(appContext, PROJECT_ID);
		} else {
			// Toast.makeText(appContext, "Exist Registration Id: " + regId,
			// Toast.LENGTH_LONG).show();
		}

	}

	private class searchAdapter extends ArrayAdapter<Search> {
		private LayoutInflater inflater;
		private ArrayList<Search> items;

		private searchAdapter(Context context, int simpleListItem1,
				ArrayList<Search> Search) {
			super(context, simpleListItem1, Search);
			this.items = Search;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view = null;
			if (v == null) {
				view = inflater.inflate(R.layout.search, null);
			} else {
				view = v;
			}
			Search search = items.get(position);
			// final Data data=this.getItem(position);
			if (search != null) {
				TextView searchtitle = (TextView) view
						.findViewById(R.id.searchtitle);
				searchtitle.setText(search.getSearchTitle());
				TextView searchtext = (TextView) view
						.findViewById(R.id.searchtext);
				searchtext.setText(search.getSearchText());
			}
			return view;
		}

	}

	@Override
	protected void onDestroy() {
		GCMRegistrar.onDestroy(appContext);
		super.onDestroy();
	}
	
}
