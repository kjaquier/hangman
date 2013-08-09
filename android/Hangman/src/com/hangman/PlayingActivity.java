package com.hangman;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayingActivity extends Activity {

	private GameData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing);
		
		// Get data from the previous try
		Log.d(getClass().getSimpleName(), "Going for a new try");
		data = (GameData) getIntent().getSerializableExtra("game_data");
		Log.d(getClass().getSimpleName(), "Got the data");
		int nbLetters = 'Z' - 'A' + 1;
		final String[] letters = new String[nbLetters];
		for (int i = 0; i < nbLetters; i++) {
			letters[i] = String.format("%c", 'A' + i);
		}
		
		// Update the UI with these data
		((TextView) findViewById(R.id.current_word)).setText(data.getWord());
		((TextView) findViewById(R.id.nb_tries)).setText(data.getNbTries());
		Log.d(getClass().getSimpleName(), "UI updated");
		
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
				((TextView) findViewById(R.id.current_word)).setText(data.getWord());
			}
			
		}
		
		// Set the behaviour of the button that let us submit a new letter
		((Button) findViewById(R.id.playbtn)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Define the dialog that let us choose a letter
				DialogFragment letterChoiceDialog = new DialogFragment() {
					
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					    builder.setTitle("Submit a letter");
					    final CharSequence[] letters = getArguments().getCharSequenceArray("letters");
					    builder.setItems(letters, new DialogInterface.OnClickListener() {
					    	
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Log.d(getClass().getSimpleName(), "Submitted : " + letters[which]);
								data.setNbTries(data.getNbTries() - 1);
								Intent intent = new Intent(PlayingActivity.this, PlayingActivity.class);
								intent.putExtra("game_data", data);
								startActivity(intent);
							}
							
						});
					    return builder.create();
					}

				};
				Bundle bundle = new Bundle();
				bundle.putCharSequenceArray("letters", letters);
				letterChoiceDialog.setArguments(bundle);
				letterChoiceDialog.show(PlayingActivity.this.getFragmentManager(), "lettersdialog");
			}
		});
		Log.d(getClass().getSimpleName(), "Button set");
		
		/*try {
			Log.d(getClass().getSimpleName(), "Selected : " + letter);
			new SendRequestTask().execute(new URL(
					"http://192.168.1.4:8080/hangman?letter=" + letter));
		} catch (MalformedURLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playing, menu);
		return true;
	}

}
