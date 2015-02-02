package com.example.corgenixrdt;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * A {@link android.preference.Preference} that displays a number picker as a dialog.
 */
public class NumberPickerPreference extends DialogPreference {

    public static final int MAX_VALUE = 1000;
    public static final int MIN_VALUE = 0;
    public static final String TAG = "NumberPickerPreferene";

    private NumberPicker picker;
    private int value;
    
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private Context mContext;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
     
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    
    
    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);
        
        //sharedPreferences = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(MIN_VALUE);
        picker.setMaxValue(MAX_VALUE);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
    	Log.i(TAG, "onDialogClosed method");
        if (positiveResult) {
        	Log.i(TAG, "onDialogClosed positiveResult");
            setValue(picker.getValue());
        }
        
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, MIN_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
    	Log.i(TAG, "onSetInitialValue");
    	//set initial value to value currently in credit setting
        sharedPreferences = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    	int cr = sharedPreferences.getInt("credit", -1);
    	//setValue(cr);
        //setValue(restorePersistedValue ? getPersistedInt(MIN_VALUE) : (Integer) defaultValue);
    }

    public void setValue(int value) {
    	Log.i(TAG, "setValue method with value: " + String.valueOf(value));
    	//set credit as entered by user
        sharedPreferences = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    	editor = sharedPreferences.edit();
    	editor.putInt("credit", value);
    	editor.commit();
        this.value = value;
        
        persistInt(this.value);
    }

    public int getValue() {
    	//return the current credit value
    	int currentCredit = sharedPreferences.getInt("credit", -1);
    	return currentCredit;
    	
        //return this.value;
    }
}