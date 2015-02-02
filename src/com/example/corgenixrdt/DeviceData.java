package com.example.corgenixrdt;

import com.example.cortegixrdt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class DeviceData extends Activity {

	TextView timerTextView;
	int waitTime = 3000; //Time to wait before Reader One in milliseconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_data);
	}


	
	public void startTimer(View view){
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		
		// 30 second timer before Reader One activity
		MyCountDownTimer countdown = new MyCountDownTimer(waitTime, 1000);
		countdown.start();
	}


	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			// Open Reader One activity when countdown finishes
	    	Intent intent = new Intent(getApplicationContext(), ReaderOne.class);
	    	startActivity(intent);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timerTextView.setText("Wait " + millisUntilFinished / 1000 + " seconds");
		}
	}

}


