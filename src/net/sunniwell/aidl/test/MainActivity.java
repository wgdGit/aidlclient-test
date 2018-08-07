package net.sunniwell.aidl.test;



import com.example.swaidlcline.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import net.sunniwell.jar.log.SWLogger;
import stb.iptv.outward.aidl.IPTVOutWardAidl;

public class MainActivity extends Activity implements OnClickListener{
	
	private IPTVOutWardAidl mIptvOutWardAidl; 
	private SWLogger log = SWLogger.getLogger(getClass());
	private Button mBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mBtn = (Button) findViewById(R.id.buttonIptv);
		mBtn.setOnClickListener(this);
		Intent iptvService = new Intent();
		iptvService.setClassName("com.itv.android.iptv",
				"stb.iptv.outward.aidl.IPTVOutWardAidlService");
		this.bindService(iptvService, mIptvServiceConnect, Context.BIND_AUTO_CREATE);
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				String parmter[] = {"username","AreaNo","UserToken","framecode",
						"PlatFlag","STBID","UserGroupNMB","epgIP","itvLOGO","EPGGroupNMB"
						,"ProductModel","RomVersion","ManufacturerInfo","MAC","LiveChannels","tradeId"};
				log.d("mIptvOutWardAidlgetParams=="+ mIptvOutWardAidl);
				if (mIptvOutWardAidl == null) {
					Intent iptvService = new Intent();
					iptvService.setClassName("com.itv.android.iptv",
							"stb.iptv.outward.aidl.IPTVOutWardAidlService");
					iptvService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					bindService(iptvService, mIptvServiceConnect, Context.BIND_AUTO_CREATE);
				}
				for (int i = 0; i < parmter.length; i++) {
					String name = parmter[i];
					String value = null;
					if (mIptvOutWardAidl != null) {
						value = mIptvOutWardAidl.getParams(name);
					}
					log.d("mIptvOutWardAidlgetParams============name=="+name +",value=="+ value );
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		};
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonIptv:
			mHandler.sendEmptyMessageDelayed(0, 2000);
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private ServiceConnection mIptvServiceConnect = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			log.d("mIptvOutWardAidlgetParams==onServiceDisconnected==============");
			mIptvOutWardAidl = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIptvOutWardAidl = IPTVOutWardAidl.Stub.asInterface(service);
			log.d("mIptvOutWardAidlgetParams=============="+mIptvOutWardAidl);
		}
	};

	

}
