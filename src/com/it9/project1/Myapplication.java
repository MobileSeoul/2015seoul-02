package com.it9.project1;

import java.util.ArrayList;

import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

public class Myapplication extends Application{
	
	private String user="1";
	private String userid="";

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	ArrayList<HistoryData> list=new ArrayList<HistoryData>();
	
	
	public void addList(String title,String address,String infomer,String indication,String repoter,String number){
		HistoryData data=new HistoryData();
		data.setTitle(title);
		data.setAddress(address);
		data.setInfomer(infomer);
		data.setIndication(indication);
		data.setRepoter(repoter);
		data.setNumber(number);
		//boolean flg=list.add(data);
		list.add(0, data);
		//return flg;
	}
	public int getSize(){
		return list.size();
	}
	public HistoryData getfirstList(){
		return list.get(0);
	}
	public HistoryData getList(int index){
		return list.get(index);
	}
	
	
}
