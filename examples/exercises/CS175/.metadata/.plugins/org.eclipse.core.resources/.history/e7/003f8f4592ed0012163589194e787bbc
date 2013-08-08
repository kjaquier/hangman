package com.example.unit2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String urlString = "http://horstmann.com/sjsu/summer2013/unit2/question.txt";
	private TextView view = (TextView) findViewById(R.id.textView3);
	
	private class DownloadTextTask extends AsyncTask<URL, Void, String> {

		protected String doInBackground(URL... urls) {
			Scanner in;
			try {
				in = new Scanner(urls[0].openStream());
				StringBuilder result = new StringBuilder();
				while (in.hasNext()) {
					result.append(in.next());
					result.append("\n");
				}
				return result.toString();
			} catch (IOException e) {
				Log.e(getClass().toString(), e.getMessage());
				return "ERROR";
			}
		}

		protected void onPostExecute(String result) {
			view.setText(result);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			new DownloadTextTask().execute(new URL(urlString));
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
