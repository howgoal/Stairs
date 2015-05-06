package com.example.stairs;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Backmusic extends Service{
	
	private MediaPlayer mp;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override 

	public void onStart(Intent intent, int startId) { 
		
		// 開始播放音樂 
		mp.start();
		
		// 音樂播放完畢的事件處理
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
				//循環播放
				try { 

					mp.start(); 

					} catch (IllegalStateException e) { 

					// TODO Auto-generated catch block 

					e.printStackTrace(); 

					} 
				
			}
			
		});
		
		// 播放音樂時發生錯誤的事件處理
		mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				
				// 釋放資源
				try { 

					mp.release(); 

					} catch (Exception e) { 

					e.printStackTrace(); 

					} 
				return false;
			} 
			
		});
		
		super.onStart(intent, startId); 
	}
	
	@Override 

	public void onCreate() { 
		
		try {
			// 初始化音樂資源 
			
			// 創建MediaPlayer對象 
			mp = new MediaPlayer(); 
			
			// 將音樂保存在res/raw/backgroundmusic
			mp = MediaPlayer.create(Backmusic.this,R.raw.backgroundmusic);
			
			// 在MediaPlayer取得播放資源與stop()之後要準備PlayBack的狀態前一定要使用MediaPlayer.prepeare
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.onCreate();
	}
	
	@Override

	public void onDestroy() {
		// 服務停止時停止播放音樂並釋放資源

		mp.stop();

		mp.release();


		super.onDestroy();
	}
}
