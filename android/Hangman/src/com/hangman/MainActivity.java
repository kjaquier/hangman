package com.hangman;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private GameData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		class SendRequestTask extends AsyncTask<URL, Void, Void> {

			@Override
			protected Void doInBackground(URL... urls) {				
				data = new GameData();
				try {
					Scanner in = new Scanner(urls[0].openStream());
					data.read(in);
					in.close();
				} catch (IOException e) {
					Log.e(getClass().toString(), e.getMessage());
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void arg) {
				((TextView) findViewById(R.id.hello)).setText(data.getWord());
			}
			
		}
		
		try {
			LinkedList<String> letters = new LinkedList<String>();
			for (char c = 'A'; c <= 'Z'; c++) {
				letters.add(c + "");
			}
			LetterChoiceDialog dialog = new LetterChoiceDialog();
			Bundle bundle = new Bundle();
			bundle.putCharSequenceArray("letters", (String[]) letters.toArray());
			dialog.setArguments(new Bundle());
			dialog.show(this.getFragmentManager(), "letters");
			Log.d(getClass().getSimpleName(), "Selected : " + letter);
			new SendRequestTask().execute(new URL(
					"http://192.168.1.4:8080/hangman?letter=" + letter));
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
