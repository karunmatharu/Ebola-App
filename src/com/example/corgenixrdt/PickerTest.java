package com.example.corgenixrdt;

import com.example.cortegixrdt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
//import android.content.DialogInterface.OnClickListener;

public class PickerTest extends Activity{

NumberPicker MyNumPicker1, MyNumPicker2, MyNumPicker3;
TextView txtMyText;
AlertDialog alertdialog;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_layout);

    txtMyText = (TextView) findViewById(R.id.txtMyText);
    txtMyText.setText("5");

    txtMyText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v1 = inflater.inflate(R.layout.number_picker, null);
                MyNumPicker1 = (NumberPicker) v1.findViewById(R.id.MyNunPicker1);
                MyNumPicker2 = (NumberPicker) v1.findViewById(R.id.MyNunPicker2);
                MyNumPicker3 = (NumberPicker) v1.findViewById(R.id.MyNunPicker3);

                MyNumPicker1.setMaxValue(20);
                MyNumPicker1.setMinValue(1);
                MyNumPicker1.setValue(Integer.parseInt(txtMyText.getText().toString()));
                MyNumPicker1.setWrapSelectorWheel(true);

                MyNumPicker2.setMaxValue(20);
                MyNumPicker2.setMinValue(1);
                MyNumPicker2.setValue(Integer.parseInt(txtMyText.getText().toString()));
                MyNumPicker2.setWrapSelectorWheel(true);

                MyNumPicker3.setMaxValue(20);
                MyNumPicker3.setMinValue(1);
                MyNumPicker3.setValue(Integer.parseInt(txtMyText.getText().toString()));
                MyNumPicker3.setWrapSelectorWheel(true);


                AlertDialog.Builder builder = new AlertDialog.Builder(PickerTest.this);

                builder.setView( v1 );
                builder.setTitle("Select Number");
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int NumVal1 = MyNumPicker1.getValue();
                        int NumVal2 = MyNumPicker2.getValue();
                        int NumVal3 = MyNumPicker3.getValue();

                        txtMyText.setText(""+ ( (NumVal1 * 100) + (NumVal2 * 10) + NumVal3) );
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertdialog.dismiss();

                    }
                });

                alertdialog = builder.create();
                alertdialog.show();

            }
        });
}

}