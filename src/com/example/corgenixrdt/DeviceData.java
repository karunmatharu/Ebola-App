package com.example.corgenixrdt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corgenixrdt.R;

public class DeviceData extends Activity {

	TextView timerTextView;
	int waitTime = 3000; //Time to wait before Reader One in milliseconds
	AlertDialog alertDialog;
	int temperature = 20;
	//String temperatureString;
	int humidity = 60;
	//String humidityString;
	String dailyPos = "POS";
	String dailyNeg = "NEG";
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_data);
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		
		//Set temperature and humitidy to stored values
		//or default values if none have been stored
		Button temperatureButton = (Button) findViewById(R.id.Temperature_Button);
		temperatureButton.setText(temperature + "\u2103");
		
		Button humidityButton = (Button) findViewById(R.id.Humidity_Button);
		humidityButton.setText(humidity + "%");
	}

	public void temperatureButtonClicked(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.temperature_picker, null);
		final NumberPicker tempPicker = (NumberPicker) v1.findViewById(R.id.TemperaturePicker);

		tempPicker.setMaxValue(110);
		tempPicker.setMinValue(0);
		tempPicker.setValue(20);
		tempPicker.setWrapSelectorWheel(true);

		AlertDialog.Builder builder = new AlertDialog.Builder(DeviceData.this);

		builder.setView(v1);
		builder.setTitle("Enter Temperature (\u2103)");
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				temperature = tempPicker.getValue();
				//temperatureString = temperature + "\u2103";
				
				//Set button text to update temperature
				Button temperatureButton = (Button) findViewById(R.id.Temperature_Button);
				temperatureButton.setText(temperature + "\u2103");
				
				//Toast.makeText(getBaseContext(), temperatureString, Toast.LENGTH_SHORT).show();
			}
		});

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
	

	public void humidityButtonClicked(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.humidity_picker, null);
		final NumberPicker humidPicker = (NumberPicker) v1.findViewById(R.id.HumidityPicker);

		humidPicker.setMaxValue(100);
		humidPicker.setMinValue(0);
		humidPicker.setValue(65);
		humidPicker.setWrapSelectorWheel(true);

		AlertDialog.Builder builder = new AlertDialog.Builder(DeviceData.this);

		builder.setView(v1);
		builder.setTitle("Enter Humidity (%)");
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				humidity = humidPicker.getValue();
				//humidityString = humidity + "%";
				
				//Set humidity button text to updated value
				Button humidityButton = (Button) findViewById(R.id.Humidity_Button);
				humidityButton.setText(humidity + "%");
				
				Toast.makeText(getBaseContext(), "" + humidity + " percent", Toast.LENGTH_SHORT).show();
			}
		});

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}

	public void dailyPosClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.DailyPosPositiveButton:
	            if (checked){
	            	dailyPos = "POS";
	            }
	            break;
	        case R.id.DailyPosNegativeButton:
	            if (checked){
	            	dailyPos = "NEG";
	            }
	            break;
	    }
	}
	
	public void dailyNegClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.DailyNegPositiveButton:
	            if (checked){
	            	dailyNeg = "POS";
	            }
	            break;
	        case R.id.DailyNegNegativeButton:
	            if (checked){
	            	dailyNeg = "NEG";
	            }
	            break;
	    }
	}
	
	public void startTimer(View view){
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		
		/*
		//Check correct input and store
		if (temperatureString == null){
			Toast.makeText(getBaseContext(), "Please Enter Temperature", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//Check correct input and store
		if (humidityString == null){
			Toast.makeText(getBaseContext(), "Please Enter Humidity", Toast.LENGTH_SHORT).show();
			return;
		}
		*/
		
		Editor editor = pref.edit();
		editor.putString("temperature", "" + temperature);
		editor.putString("humidity", humidity + "%");
		editor.putString("dailyPosControl", dailyPos);
		editor.putString("dailyNegControl", dailyNeg);
		editor.commit();
		
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
	    	Intent intent = new Intent(getApplicationContext(), Reader.class);
	    	startActivity(intent);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timerTextView.setText("Wait " + millisUntilFinished / 1000 + " seconds");
		}
	}

}


