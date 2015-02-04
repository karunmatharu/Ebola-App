package com.example.corgenixrdt;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class DocCreator extends Activity {
	
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doc_creator);
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	}
	
	
	public void makePdfClicked(View view){
		
		//Create pdf form using PdfHandler Class
		PdfHandler pdfHandler = new PdfHandler(getApplicationContext());
		pdfHandler.write();
		
		//Clear all saved Data
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	/*
	 * use an intent to send an email with the attached created form
	 */
	public void sendPdfClicked(View view){
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ksm113@imperial.ac.uk"});
		intent.putExtra(Intent.EXTRA_SUBJECT, "Ebola Data Form");
		intent.putExtra(Intent.EXTRA_TEXT, "Please find attached form.");
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root, "/corgenixrdt/outputFile.pdf" );
		if (!file.exists() || !file.canRead()) {
		    Toast.makeText(this, "Attachment Error, File Cannot be found at " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
		    finish();
		    return;
		}
		Uri uri = Uri.fromFile(file);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(intent, "Send email..."));
		
	}
	
	
	public void restartClicked(View view){
		//Close all activities and return to home activity
		Intent intent = new Intent(getApplicationContext(), DataOne.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
