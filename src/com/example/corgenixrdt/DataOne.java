package com.example.corgenixrdt;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.corgenixrdt.R;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class DataOne extends Activity {

	private static final String TAG = "DataOne";
	AlertDialog alertDialog;
	Calendar cal = Calendar.getInstance();
	SharedPreferences pref;
	int ageYears = 30;
	int ageMonths = 0;
	int dateDay = 0;
	int dateMonth = 0;
	int dateYear = 0;
	String age;
	String gender;
	String dateString;
	String testKitLot;
	String exp;
	String studyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_one);

		Log.i(TAG, "Activity onCreate Method");
		
		
		//Clear any previous preferences
		pref = getApplicationContext().getSharedPreferences("MyPref", 0); 
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
		
		
		Log.i(TAG, "Got Shared preferences");

		
		//If age has already been set, set the text to the age
		//else set the age to a default value
		if (pref.getString(age, null) != null){
			age = pref.getString(age, null);
		} else{
			age = "30y";
		}
		
		//Set age buttons text
		Button ageButton = (Button) findViewById(R.id.Age_Button);
		ageButton.setText(age);
		
		Log.i(TAG, "Button Text Set");
		
		//IF date has not already been set, change to today's date
		if (dateDay == 0){
			dateDay = cal.get(Calendar.DAY_OF_MONTH);
			dateMonth = cal.get(Calendar.MONTH) + 1;  //add one to month as months are zero indexed
			dateYear = cal.get(Calendar.YEAR);
			
			dateString = dateDay + "/" + dateMonth + "/" + dateYear;
		}
		//Update the date button text
		Button dateButton = (Button) findViewById(R.id.Date_Button);
		dateButton.setText(dateString);
		
		Log.i(TAG, "Exiting on create");	
		
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(TAG, "Activity onDestroy method called");
	}

	@Override
	public void onPause() {
		super.onPause();  // Always call the superclass method first
		Log.i(TAG, "OnPause called");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop called");
	}


	/*
	 * Display Dialog to pick Age when age Button clicked
	 */
	public void ageButton(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.age_picker, null);
		final NumberPicker YearPicker = (NumberPicker) v1.findViewById(R.id.AgePickerYears);
		final NumberPicker MonthsPicker = (NumberPicker) v1.findViewById(R.id.AgePickerMonths);

		YearPicker.setMaxValue(110);
		YearPicker.setMinValue(0);
		YearPicker.setValue(ageYears);
		YearPicker.setWrapSelectorWheel(true);

		MonthsPicker.setMaxValue(11);
		MonthsPicker.setMinValue(0);
		MonthsPicker.setValue(ageMonths);
		MonthsPicker.setWrapSelectorWheel(true);

		AlertDialog.Builder builder = new AlertDialog.Builder(DataOne.this);

		builder.setView(v1);
		builder.setTitle("Enter Age");
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ageYears = YearPicker.getValue();
				ageMonths = MonthsPicker.getValue();
				
				age = ageYears + "y";
				
				//Include months if value has been changed
				if (ageMonths != 0){
					age = age + ageMonths + "m";
				}
				
				//update the button text to new age
				Button ageButton = (Button) findViewById(R.id.Age_Button);
				ageButton.setText(age);
				
				Toast.makeText(getBaseContext(), age, Toast.LENGTH_SHORT).show();
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
	
	public void genderClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.Male_Button:
	            if (checked){
	            	gender = "M";
	            }
	            break;
	        case R.id.Female_Button:
	            if (checked){
	            	gender = "F";
	            }
	            break;
	    }
	}
	
	/*
	 * Display Dialog to pick Age when age Button clicked
	 */
	public void dateButton(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.date_picker, null);
		final NumberPicker DayPicker = (NumberPicker) v1.findViewById(R.id.DatePickerDay); 
		final NumberPicker MonthPicker = (NumberPicker) v1.findViewById(R.id.DatePickerMonth);
		final NumberPicker YearPicker = (NumberPicker) v1.findViewById(R.id.DatePickerYear);
		
		DayPicker.setMaxValue(31);
		DayPicker.setMinValue(1);
		DayPicker.setValue(dateDay);
		DayPicker.setWrapSelectorWheel(true);
		
		MonthPicker.setMaxValue(12);
		MonthPicker.setMinValue(1);
		MonthPicker.setValue(dateMonth);
		MonthPicker.setWrapSelectorWheel(true);
		
		YearPicker.setMaxValue(2050);
		YearPicker.setMinValue(2010);
		YearPicker.setValue(dateYear);
		YearPicker.setWrapSelectorWheel(true);


		AlertDialog.Builder builder = new AlertDialog.Builder(DataOne.this);

		builder.setView(v1);
		builder.setTitle("Enter Date");
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dateDay = DayPicker.getValue();
				dateMonth = MonthPicker.getValue();
				dateYear = YearPicker.getValue();

				dateString = dateDay + "/" + dateMonth + "/" + dateYear;
				
				//Set button text to date
				Button dateButton = (Button) findViewById(R.id.Date_Button);
				dateButton.setText(dateString);
				
				Toast.makeText(getBaseContext(), dateString, Toast.LENGTH_SHORT).show();
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
	};


	public void continueToDeviceData(View view){

		//Save input data to preference
		EditText edtx = (EditText)findViewById(R.id.StudyIdField);
		studyId = edtx.getText().toString();
		// check if input exists for studyId
		if (studyId.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Study ID Number", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// check if input exists for age
		if (age == null || age.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Age", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// check if input exists for gender
		if (gender == null || gender.length() == 0){
			Toast.makeText(getBaseContext(), "Please Select Gender", Toast.LENGTH_SHORT).show();
			return;
		}
		// check if input exists for date
		if (dateString == null || dateString.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Date", Toast.LENGTH_SHORT).show();
			return;
		}
		edtx = (EditText)findViewById(R.id.Test_Kit_Field);
		testKitLot = edtx.getText().toString();
		// check if input exists for studyId
		if (testKitLot.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Test Kit Lot", Toast.LENGTH_SHORT).show();
			return;
		}
		edtx = (EditText)findViewById(R.id.Exp_Field);
		exp = edtx.getText().toString();
		// check if input exists for studyId
		if (exp.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Exp", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Editor editor = pref.edit();
		editor.putString("studyId", studyId); // Storing Study Id
		editor.putString("age", age); // Storing age
		editor.putString("gender", gender); // Storing gender
		editor.putString("date", dateString); // Storing date
		editor.putString("testKitLot", testKitLot); //Storing testKitLot
		editor.putString("exp", exp); //storing exp
		editor.commit();
		
		Log.i(TAG, "the value for exp is " + pref.getString("exp", "no value"));
		
		//add values to intent
		Intent intent = new Intent(this, DeviceData.class);
		startActivity(intent);
	}

}
