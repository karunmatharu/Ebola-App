package com.example.corgenixrdt;

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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corgenixrdt.R;

public class Reader extends Activity {
	
	AlertDialog alertDialog;
	SharedPreferences pref;
	String minsBefore;
	String readerInitials;
	String result;
	String positivityScore;
	String invalidReason;
	int reader = -1;
	String readerString;
	final String TAG = "Reader";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader_one);
		
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		
		//if no readings have been taking, set reader number to 1
		//else increase the stored reader number
		if (reader == -1){
			int completedReadings = pref.getInt("readerNumber", 0);
			if (completedReadings == 0){
				Editor editor = pref.edit();
				editor.putInt("readerNumber", 1);
				editor.commit();
			}
			else if (completedReadings <= 3){
				Editor editor = pref.edit();
				editor.putInt("readerNumber", completedReadings + 1);
				editor.commit();
			}
				
			//Set reader string as the current reader number
			reader = completedReadings +1;
			readerString = "reader" + reader;
			Log.i(TAG, "reader is: " + readerString);
		}
		
		//Set the activity title according to reading
		String activityTitle = "Reading " + reader;
		setTitle(activityTitle);
			
	}
	
	public void minsReadButtonClicked(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.minsread_picker, null);
		final NumberPicker minsReadPicker = (NumberPicker) v1.findViewById(R.id.MinsReadPicker);

		minsReadPicker.setMaxValue(15);
		minsReadPicker.setMinValue(5);
		minsReadPicker.setValue(5);
		minsReadPicker.setWrapSelectorWheel(true);

		AlertDialog.Builder builder = new AlertDialog.Builder(Reader.this);

		builder.setView(v1);
		builder.setTitle("Enter Duaration Before Results Read (mins)");
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int minsRead = minsReadPicker.getValue();
				minsBefore = minsRead + "min";

				Toast.makeText(getBaseContext(), "" + minsRead + " minsread", Toast.LENGTH_SHORT).show();
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

	
	public void resultButtonClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    //Positivity Scores
        TextView positivityScoreLabel = (TextView) findViewById(R.id.Positivity_Score_Label);
        RadioGroup positivityScores = (RadioGroup) findViewById(R.id.Positivity_Scores);

        //Invalid Reasons
        TextView invalidReasonsLabel = (TextView) findViewById(R.id.Invalid_Reason_Label);
        RadioGroup invalidScores = (RadioGroup) findViewById(R.id.Invalid_Reasons);
      
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.Result_Positive:
	            if (checked){
	            	//record result as positive
	            	result = "Positive";
	            	
	            	//Make Invalid Reasons Invisible
	            	invalidReasonsLabel.setVisibility(View.INVISIBLE);
	            	invalidScores.setVisibility(View.INVISIBLE);
	            	
	            	//Make Positivity score visible
	                positivityScoreLabel.setVisibility(View.VISIBLE);
	                positivityScores.setVisibility(View.VISIBLE);
	            }
	            break;
	        case R.id.Result_Negative:
	            if (checked){
	            	result = "Negative";
	            	//Make Positivity score invisible
	            	positivityScoreLabel.setVisibility(View.INVISIBLE);
	            	positivityScores.setVisibility(View.INVISIBLE);
	            	
	            	//Make Invalid Reasons Visible
	            	invalidReasonsLabel.setVisibility(View.INVISIBLE);
	            	invalidScores.setVisibility(View.INVISIBLE);
	            }
	            break;
	        case R.id.Result_Invalid:
	        	if(checked){
	            	result = "Invalid";
	        		//Make Positivity score invisible
	        		positivityScoreLabel.setVisibility(View.INVISIBLE);
	        		positivityScores.setVisibility(View.INVISIBLE);
	        		//Make invalid Score Invisible
	            	invalidReasonsLabel.setVisibility(View.VISIBLE);
	            	invalidScores.setVisibility(View.VISIBLE);
	        	}
	        	break;
	    }
	}
	
	public void positivityScoreClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.positivity5:
	            if (checked){
	            	positivityScore = "5";
	            }
	            break;
	        case R.id.positivity4:
	            if (checked){
	            	positivityScore = "4";
	            }
	            break;
	        case R.id.positivity3:
	            if (checked){
	            	positivityScore = "3";
	            }
	            break;
	        case R.id.positivity2:
	            if (checked){
	            	positivityScore = "2";
	            }
	            break;
	        case R.id.positivity1:
	            if (checked){
	            	positivityScore = "1";
	            }
	            break;
	    }
	    //only for testing
		Toast.makeText(getBaseContext(), "positivity score: " + positivityScore, Toast.LENGTH_SHORT).show();
	}
	
	public void invalidReasonClicked(View view) {
	    // Is the button now checked
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.invalid1:
	            if (checked){
	            	invalidReason = "1";
	            }
	            break;
	        case R.id.invalid2:
	            if (checked){
	            	invalidReason = "2";
	            }
	            break;
	        case R.id.invalid3:
	            if (checked){
	            	invalidReason = "3";
	            }
	            break;
	    }
	    //only for testing
		Toast.makeText(getBaseContext(), "invalid reason " + invalidReason, Toast.LENGTH_SHORT).show();
	}
	
	
	
	public void nextReader(View view){
		
		Editor editor = pref.edit();
		
		//Validate input data and store
		
		if (minsBefore == null){
			Toast.makeText(getBaseContext(), "Please Enter Mins Before Results Read", Toast.LENGTH_SHORT).show();
			return;
		}
		
		EditText edtx = (EditText)findViewById(R.id.Readers_Initials_Field);
		readerInitials = edtx.getText().toString();
		if (readerInitials.length() == 0){
			Toast.makeText(getBaseContext(), "Please Enter Reader's Initials", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// check result
		if (result == null){
			Toast.makeText(getBaseContext(), "Please Enter Result Interpretation", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (result.equals("Positive")){
			if (positivityScore == null){
				Toast.makeText(getBaseContext(), "Please Enter Positivity Score", Toast.LENGTH_SHORT).show();
				return;
			}
			editor.putString(readerString + "PositivityScore", positivityScore);
		}
		else if (result.equals("Invalid")){
			if (invalidReason == null){
				Toast.makeText(getBaseContext(), "Please Enter Invalidity Reason", Toast.LENGTH_SHORT).show();
				return;
			}
			editor.putString(readerString + "InvalidReason", invalidReason);
		}
		
		editor.putString(readerString + "Result", result);
		Log.i(TAG, "saving result: " + readerString + "Result" + " : " + result);
		editor.putString(readerString + "MinsBefore", minsBefore);
		editor.putString(readerString + "ReaderInitials", readerInitials);
		editor.commit();
		
		//If only one reading is taken, open another reader activity
		//If two readings have been taken, check whether they are the same
		//If not, take another reading
		if (reader <= 1){
		   	Intent intent = new Intent(this, Reader.class);
	    	startActivity(intent);
	    	return;
		}
		else if(reader == 2){
			
			Log.i(TAG, "Getting first and second reading results");
			String reader1result = pref.getString("reader1Result", null);
			String reader2result = pref.getString("reader2Result", null);
			
			Log.i(TAG, "r1: " + reader1result);
			Log.i(TAG, "r2: " + reader2result);
			
			if (!(reader1result.equals(reader2result))){
				//open another reading if the first two have different results
		    	Intent intent = new Intent(this, Reader.class);
		    	startActivity(intent);
		    	return;
			}
		}
		
		Toast.makeText(getBaseContext(), "Form Complete", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(getApplicationContext(), DocCreator.class);
		startActivity(intent);
		

	}
	
}
