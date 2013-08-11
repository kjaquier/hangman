package com.hangman;

import java.io.IOException;
import java.net.MalformedURLException;
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
		
		class SubmitLetterTask extends AsyncTask<URL, Void, Void> {

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
				//((TextView) findViewById(R.id.current_word)).setText(data.getWord());
				//((TextView) findViewById(R.id.nb_tries)).setText(data.getNbTries() + "");
				
				// Goes to the next try
				Intent intent = new Intent(PlayingActivity.this, PlayingActivity.class);
				intent.putExtra("game_data", data);
				startActivity(intent);
			}
		}
		
		// Get the data from the previous try
		Log.d(getClass().getSimpleName(), "Going for a new try");
		data = (GameData) getIntent().getSerializableExtra("game_data");
		Log.d(getClass().getSimpleName(), "Data : " + data);

		// Update the UI with these data
		((TextView) findViewById(R.id.current_word)).setText(data.getWord());
		((TextView) findViewById(R.id.nb_tries)).setText("" + data.getNbTries());
		Log.d(getClass().getSimpleName(), "UI updated!");
		
		// Set the behaviour of the button that let us submit a new letter
		Button playBtn = ((Button) findViewById(R.id.new_try_btn));
		Log.d(getClass().getSimpleName(), "Got the button : " + playBtn);
		playBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Define the dialog that let us choose a letter
				DialogFragment letterChoiceDialog = new DialogFragment() {
					
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					    builder.setTitle("Submit a letter");
					    builder.setItems(data.getLetters(), new DialogInterface.OnClickListener() {
					    	
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String letter = (String) data.getLetters()[which];
								//data.setNbTries(data.getNbTries() - 1);
								data.deleteLetter(letter);
								
								// Submit the letter to the server
								try {
									Log.d(getClass().getSimpleName(), "Submitting : " + letter);
									new SubmitLetterTask().execute(new URL(
											"http://192.168.1.116:4243?letter=" + letter));
								} catch (MalformedURLException e) {
									Log.e(getClass().getSimpleName(), e.getMessage());
								}
							}
							
						});
					    return builder.create();
					}

				};
				letterChoiceDialog.show(PlayingActivity.this.getFragmentManager(), "lettersdialog");
			}
		});
		Log.d(getClass().getSimpleName(), "Button set");

	}
	
	@Override
	public void onBackPressed() {
		
		// Set the confirmation dialog
		AlertDialog.Builder dialog = new AlertDialog.Builder(
                PlayingActivity.this);
        dialog.setTitle("Abort");
        dialog.setMessage("Are you sure you want to go back to the menu?");
        
        // Set the "Yes" option
        dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PlayingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        
        // Set the "No" option
        dialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        
        // Show the dialog
        dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playing, menu);
		return true;
	}

}
