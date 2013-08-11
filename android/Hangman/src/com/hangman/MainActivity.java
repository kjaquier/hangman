package com.hangman;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main menu activity
 */
public class MainActivity extends Activity {

	private GameData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Request the server for a new game and launch the playing activity
		 * when it gets the answer
		 */
		class StartNewGameTask extends AsyncTask<URL, Void, Void> {

			@Override
			protected Void doInBackground(URL... urls) {
				Log.d(getClass().getSimpleName(), "Reading data...");
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
				Log.d(getClass().getSimpleName(), "Data : " + data);

				data.initLetters();

				// Goes to the playing activity
				Intent intent = new Intent(MainActivity.this,
						PlayingActivity.class);
				intent.putExtra("game_data", data);
				startActivity(intent);
			}
		}

		// Set the button to request a new game when clicked on
		((Button) findViewById(R.id.playbtn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// Init the data
						data = new GameData();
						data.initLetters();

						// Update the UI
						((TextView) findViewById(R.id.loading_label))
								.setVisibility(View.VISIBLE);
						((TextView) findViewById(R.id.playbtn))
								.setVisibility(View.INVISIBLE);

						// Request the server to start a new game
						try {
							Log.d(getClass().getSimpleName(),
									"Requesting new game...");
							new StartNewGameTask()
									.execute(new URL(
											"http://cs13.cs.sjsu.edu:8080/team1?new=1"));
						} catch (MalformedURLException e) {
							Log.e(getClass().getSimpleName(), e.getMessage());
						}
					}
				});
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
