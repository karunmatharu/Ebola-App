package com.example.corgenixrdt;

import com.example.cortegixrdt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DataOne extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_one);
        

    }
    
    
    public void button(View view){
    	Log.i("DataOne", "executing button method");
    	
    	Intent intent = new Intent(this, PickerTest.class);
    	startActivity(intent);
    	/*
    	NumberPicker np = (NumberPicker) findViewById(R.id.np);
    	np.setMaxValue(100);
    	np.setMinValue(0);  
    	*/
    }
    
    public void onGenderButtonClicked(View view){
    	
    	
    }
    
    public void continueToDeviceData(View view){
    	//add values to intent
    	Intent intent = new Intent(this, DeviceData.class);
    	startActivity(intent);
    }

}
