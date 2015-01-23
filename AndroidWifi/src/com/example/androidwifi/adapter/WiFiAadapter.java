package com.example.androidwifi.adapter;

import java.util.List;

import com.example.androidwifi.R;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WiFiAadapter extends BaseAdapter {
	private List<ScanResult> list;
	private Context context;

	public WiFiAadapter(Context context) {
		super();
		this.context = context;
	}

	public List<ScanResult> getList() {
		return list;
	}

	public void setList(List<ScanResult> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getList().size();
	}

	@Override
	public ScanResult getItem(int position) {
		// TODO Auto-generated method stub
		return getList().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold vh;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.wifi_list_item, null);
			vh = new ViewHold();
			convertView.setTag(vh);
		}else{
			vh = (ViewHold) convertView.getTag();
		}
		ShowInitView(vh,convertView);
		evaluatio(vh, getItem(position));
		return convertView;
	}
	
	private void ShowInitView(ViewHold vh,View convertView){
		vh.bssid = (TextView) convertView.findViewById(R.id.wifi_bbsid);
		vh.ssid = (TextView) convertView.findViewById(R.id.wifi_SSID);
		vh.capabilities = (TextView) convertView.findViewById(R.id.wifi_capabilities);
		vh.frequency = (TextView) convertView.findViewById(R.id.wifi_frequency);
		vh.level = (TextView) convertView.findViewById(R.id.wifi_level);
	}
	
	private void evaluatio(ViewHold vh,ScanResult entity){
		if(!entity.BSSID.equals("") && entity.BSSID != null){
			vh.bssid.setText(entity.BSSID.toString());
		}else{
			vh.bssid.setText("");
		}
		if(!entity.SSID.equals("") && entity.SSID != null){
			vh.ssid.setText(entity.SSID.toString());
		}else{
			vh.ssid.setText("");
		}
		if(!entity.capabilities.equals("") && entity.capabilities != null){
			vh.capabilities.setText(entity.capabilities);
		}else{
			vh.capabilities.setText("");
		}
		if(entity.frequency > 0){
			vh.frequency.setText(entity.frequency+"");
		}else{
			vh.frequency.setText("");
		}
		if(entity.level > 0){
			vh.level.setText(entity.level+"");
		}else{
			vh.level.setText("");
		}
	}
	
	class ViewHold {
		TextView bssid;
		TextView ssid;
		TextView capabilities;
		TextView frequency;
		TextView level;
	}
}
