package com.example.androidwifi;

import java.net.ServerSocket;
import java.util.List;

import com.example.androidwifi.adapter.WiFiAadapter;
import com.example.androidwifi.util.WifiAdmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WifiActivity extends Activity {
	/** Called when the activity is first created. */
	private ServerSocket socket;
	private ListView lv;
	private Button scan;
	private Button start;
	private Button stop;
	private Button check;
	private Button found;
	private WifiAdmin mWifiAdmin;
	private boolean flag = false;
	// ɨ�����б�
	private List<ScanResult> list;
	private StringBuffer sb = new StringBuffer();

	// ������
	WiFiAadapter adapted;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wfi_main);
		mWifiAdmin = new WifiAdmin(WifiActivity.this);
		init();
	}

	public void init() {
		lv = (ListView) findViewById(R.id.wifi_main_listview);
		adapted = new WiFiAadapter(WifiActivity.this);

		scan = (Button) findViewById(R.id.scan);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		check = (Button) findViewById(R.id.check);
		found = (Button) findViewById(R.id.wifi_enable);

		scan.setOnClickListener(new MyListener());
		start.setOnClickListener(new MyListener());
		stop.setOnClickListener(new MyListener());
		check.setOnClickListener(new MyListener());
		found.setOnClickListener(new MyListener());

	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.scan:// ɨ������
				getAllNetWorkList();
				break;
			case R.id.start:// ��Wifi
				mWifiAdmin.openWifi();
				Toast.makeText(WifiActivity.this,
						"��ǰwifi״̬Ϊ��" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.stop:// �ر�Wifi
				mWifiAdmin.closeWifi();
				Toast.makeText(WifiActivity.this,
						"��ǰwifi״̬Ϊ��" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.check:// Wifi״̬
				Toast.makeText(WifiActivity.this,
						"��ǰwifi״̬Ϊ��" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.wifi_enable:
				setWifiFound();
				break;
			}
		}

	}

	public void getAllNetWorkList() {
		// ÿ�ε��ɨ��֮ǰ�����һ�ε�ɨ����
		if (sb != null) {
			sb = new StringBuffer();
		}
		// ��ʼɨ������
		mWifiAdmin.startScan();
		list = mWifiAdmin.getWifiList();
		if (list != null) {
			adapted.setList(list);
			lv.setAdapter(adapted);
			lv.setOnItemClickListener(item);
		}
	}

	AdapterView.OnItemClickListener item = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ScanResult result = list.get(position);
			setWifi(result);

		}

	};
	EditText pass;

	private void setWifi(final ScanResult result) {
		View found = LayoutInflater.from(WifiActivity.this).inflate(
				R.layout.wifi_found, null);
		pass = (EditText) found.findViewById(R.id.wifi_found_edit_p);
		new AlertDialog.Builder(WifiActivity.this).setTitle("����")
				.setIcon(R.drawable.abc_ab_bottom_solid_dark_holo)
				.setView(found)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mWifiAdmin.addNetWork(mWifiAdmin.CreateWifiInfo(
								result.SSID.trim(), pass.getText().toString()
										.trim(), 3));
					}
				}).setNegativeButton("ȡ��", null).create().show();
	}

	private void setWifiFound() {
		View found = LayoutInflater.from(WifiActivity.this).inflate(
				R.layout.wifi_found, null);
		pass = (EditText) found.findViewById(R.id.wifi_found_edit_p);
		new AlertDialog.Builder(WifiActivity.this).setTitle("�����ȵ�")
				.setIcon(R.drawable.abc_ab_bottom_solid_dark_holo)
				.setView(found)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						boolean apEnabled = mWifiAdmin.setWifiApEnabled(true,
								"ѲɽС��", pass.getText().toString());
						if (apEnabled) {
							Toast.makeText(WifiActivity.this, "wifi�����ɹ�",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(WifiActivity.this, "wifi����ʧ��",
									Toast.LENGTH_LONG).show();
						}
					}
				}).setNegativeButton("ȡ��", null).create().show();
	}
}