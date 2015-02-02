package com.example.corgenixrdt;

import com.example.cortegixrdt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ReaderOne extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader_one);
	}
	
	public void resultButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
        TextView positivityScoreLabel = (TextView) findViewById(R.id.Positivity_Score_Label);
        RadioGroup positivityScores = (RadioGroup) findViewById(R.id.Positivity_Scores);

	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.Result_Positive:
	            if (checked){
	            	//Make positivity score visible
	                positivityScoreLabel.setVisibility(View.VISIBLE);
	                positivityScores.setVisibility(View.VISIBLE);
	            }
	            break;
	        case R.id.Result_Negative:
	            if (checked)
	                // Ninjas rule
	            break;
	        case R.id.Result_Invalid:
	        	if(checked)
	        		//Do something
	        	break;
	    }
	}
}
