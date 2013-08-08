package com.example.unit2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Question question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		class DownloadTextTask extends AsyncTask<URL, Void, Void> {
			protected Void doInBackground(URL... urls) {
				question = new Question();
				try {
					Scanner in = new Scanner(urls[0].openStream());
					question.read(in);
					in.close();
				} catch (IOException e) {
					Log.e(getClass().toString(), e.getMessage());
				}
				return null;
			}

			protected void onPostExecute(Void arg) {
				
				
				ArrayAdapter<String> choices = new ArrayAdapter<String>(MainActivity.this,
						R.id.txvAnswer, question.getChoices());
				
//				int[] ids = {
//					R.id.textView1, 
//					R.id.textView2, 
//					R.id.textView3, 
//					R.id.textView4,
//					R.id.textView5 };
//				((TextView) findViewById(ids[0])).setText(question.getText());
//				List<String> choices = question.getChoices();
//				for (int i = 1; i < ids.length; i++) {
//					((TextView) findViewById(ids[i])).setText(choices.get(i-1));
//				}
				
				// Link buttons to the answers
//				int[] buttonIds = { 
//					R.id.button1,
//					R.id.Button01,
//					R.id.Button02,
//					R.id.Button03 };
//				for (int i = 0; i < buttonIds.length; i++) {
//				    final String choice = question.getChoices().get(i);
//				    ((Button) findViewById(buttonIds[i])).setOnClickListener(
//				        new View.OnClickListener() {
//				    	    @Override public void onClick(View arg0) {
//					            Intent intent = new Intent(MainActivity.this, ResponseActivity.class);
//					            intent.putExtra("choice", choice);
//					            intent.putExtra("question", question);
//					            startActivity(intent);
//				    }});
//				}
			}
		}

		try {
			new DownloadTextTask().execute(new URL(
					"http://horstmann.com/sjsu/summer2013/unit3/question.txt"));
		} catch (MalformedURLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
