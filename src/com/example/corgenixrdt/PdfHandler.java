package com.example.corgenixrdt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/*
 * Class used to create and write to PDF file 
 * in the format of the Device Data Collection Form
 */
public class PdfHandler {
	final static String TAG = "PdfTest";
	Font standardBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	Context context;
	SharedPreferences pref;
	
	public PdfHandler(Context context){
		this.context = context;
	}

	public Boolean write(){
		try {

			//Check if folder exists
			String folderName = "/corgenixrdt/";
			String filename = "outputFile";
			File folder = new File(Environment.getExternalStorageDirectory() + folderName);

			if(folder.exists() && folder.isDirectory()){
				Log.i(TAG, "folder exists");
			} else {
				Log.i(TAG, "folder does not exists. Creating folder");
				folder.mkdir();
			}

			String folderPath = folder.getAbsolutePath();
			File pdffile = new File(folderPath + "/" + filename + ".pdf");
			Log.i(TAG, "Output file to have path " + pdffile.getAbsolutePath());


			// If file does not exists, then create it
			if (!pdffile.exists()) {
				pdffile.createNewFile();
			}

			//Get access to preferences data
			pref = context.getSharedPreferences("MyPref", 0); 

			
			//Create document and outputstream
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(pdffile.getAbsoluteFile()));
			document.open();

			//Document Title
			Paragraph title = new Paragraph("Corgenix RDT Device Data Collection",
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13));
			title.setAlignment(Element.ALIGN_CENTER);

			addEmptyLine(title, 1);
			document.add(title);

			//Retrieve saved data values
			String studyId = pref.getString("studyId", null);
			String age = pref.getString("age", null);
			String gender = pref.getString("gender", null);
			String date = pref.getString("date", null);
			String testKitLot = pref.getString("testKitLot", null);
			String exp = pref.getString("exp", null);
			
			//Retrieve Device Data values
			String temperature = pref.getString("temperature", null);
			String humidity = pref.getString("humidity", null);
			String dailyPosControl = pref.getString("dailyPosControl", null);
			String dailyNegControl = pref.getString("dailyNegControl", null);


			//table of initial data

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			// t.setBorderColor(BaseColor.GRAY);
			// t.setPadding(4);
			// t.setSpacing(4);

			PdfPCell c1 = new PdfPCell(new Phrase("Study ID Number: " + studyId));
			//c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBorderWidth(0);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Age: " + age));
			c1.setBorderWidth(0);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Gender(M/F): " + gender));
			c1.setBorderWidth(0);
			table.addCell(c1);

			// add 3 empty cells as
			c1 = new PdfPCell(new Phrase(" "));
			c1.setBorderWidth(0);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase(" "));
			c1.setBorderWidth(0);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase(" "));
			c1.setBorderWidth(0);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Date: " + date));
			c1.setBorderWidth(0);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Test Kit Lot: " + testKitLot));
			c1.setBorderWidth(0);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Exp: " + exp));
			c1.setBorderWidth(0);
			table.addCell(c1);

			document.add(table);

			//Device Data Title
			Paragraph deviceDataTitle = new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
			addEmptyLine(deviceDataTitle, 1);
			deviceDataTitle.add("Device Data");
			addEmptyLine(deviceDataTitle, 1);
			document.add(deviceDataTitle);

			// Device data values
			//Temperature and Humidity
			Paragraph text = new Paragraph("", standardBold);
			text.add(new Phrase("Temperature: " + temperature, standardBold));
			text.add(new Phrase("                   ", standardBold));
			text.add(new Phrase("Humidity: " + humidity, standardBold));
			addEmptyLine(text, 1);
			document.add(text);

			//Daily Control Results
			text = new Paragraph("", standardBold);
			text.add(new Phrase("Daily POS control result: " + dailyPosControl));
			text.add(new Phrase("                        "));
			text.add(new Phrase("Daily NEG control result: " + dailyNegControl));
			document.add(text);

			//Result 1 
			text = results(1);
			document.add(text);

			//Results 2
			text = new Paragraph("", standardBold);
			text.add(new Phrase("---------------------------READER 1 TO FOLD HERE AFTER READING----------------------"));
			addEmptyLine(text, 1);
			document.add(text);

			text = results(2);
			document.add(text);

			//Results 3 if needed
			text = new Paragraph("");
			text.add(new Phrase("If Reader 1 does not match Reader 2 result, test must be reade by Reader 3 (unblinded)"));
			addEmptyLine(text, 1);
			document.add(text);

			text = results(3);
			document.add(text);

			
			//Check the requirements for this
			text = new Paragraph("RCT-PCR results (record AFTER reading Corgenix test):  pos / neg         Ct value:", standardBold);
			document.add(text);

			// step 5
			document.close();

			Log.d("Suceess", "Sucess");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Function to generate text for reader results
	 */
	private Paragraph results(int readerNo){
		
		
		String defaultValue = "NODATA";
		//there may not be a third reading
		// in this case, return a blank form
		if (readerNo > 2){
			defaultValue = "";
		}
		
		String reader = "reader" + readerNo;
		String readTime = pref.getString(reader + "MinsBefore", defaultValue);
		String result = pref.getString(reader + "Result", defaultValue);
		String initials = pref.getString(reader + "ReaderInitials", defaultValue);
		
		String score = "";
		if(result.equals("Positive")){
			score = pref.getString(reader + "PositivityScore", defaultValue);
		} else if(result.equals("Negative")){
			score = pref.getString(reader + "InvalidReason", defaultValue);
		}
		
		Paragraph text = new Paragraph();
		text.add(new Phrase("Reader " + readerNo + ": ", standardBold));
		text.add(new Phrase("Results read after "));
		text.add(new Phrase(readTime, standardBold));
		text.add(new Phrase("min (range 5-15')"));

		text.add(new Phrase("               "));
		text.add(new Phrase("Reader's initials: "));
		text.add(new Phrase(initials, standardBold));
		addEmptyLine(text, 1);

		text.add(new Phrase("Reader " + readerNo + " result interpretation: " + result, standardBold));
		addEmptyLine(text, 1);

		String positivityScore = "";
		String invalidResult = "";

		if (result.equals("Positive")){
			positivityScore = score;
		} else if (result.equals("Invalid")){
			invalidResult = score;
		}

		text.add(new Phrase("    "));
		text.add(new Phrase("If Positive, ", standardBold));
		text.add(new Phrase("positivity score (5=high, 1=low): "));
		text.add(new Phrase(positivityScore, standardBold));

		addEmptyLine(text, 1);
		text.add(new Phrase("    "));
		text.add(new Phrase("If Invalid, ", standardBold));
		text.add(new Phrase("reason for invalid: "));
		text.add(new Phrase(invalidResult, standardBold));
		addEmptyLine(text, 1);
		return text;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}


