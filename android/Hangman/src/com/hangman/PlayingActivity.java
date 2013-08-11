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

/**
 * Activity that handles a game. Running from the moment when the server sends
 * the initial data to when the user finds the whole word or submit his last
 * letter.
 */
public class PlayingActivity extends Activity {

	/**
	 * Submit a letter to the server, and when it replies, update the game's
	 * data and updates the UI.
	 */
	private class SubmitLetterTask extends AsyncTask<URL, Void, Void> {

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

			updateUI();

			// End the game if we won or if we lost
			if (data.getState() != GameState.PLAYING) {
				onGameEnd();
			}
		}
	}

	/**
	 * Shows a dialog to the user to make him select a letter, then runs
	 * {@link SubmitLetterTask} to submit it.
	 */
	private class LetterSubmitter implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			// Define the dialog that let us choose a letter
			DialogFragment letterChoiceDialog = new DialogFragment() {

				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Submit a letter");
					builder.setItems(data.getLetters(),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String letter = (String) data.getLetters()[which];
									data.deleteLetter(letter);

									// Submit the letter to the server
									try {
										Log.d(getClass().getSimpleName(),
												"Submitting : " + letter);
										new SubmitLetterTask().execute(new URL(
												"http://cs13.cs.sjsu.edu:8080/team1?letter="
														+ letter));
									} catch (MalformedURLException e) {
										Log.e(getClass().getSimpleName(),
												e.getMessage());
									}
								}

							});
					return builder.create();
				}

			};
			letterChoiceDialog.show(PlayingActivity.this.getFragmentManager(),
					"lettersdialog");
		}
	}

	private GameData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing);

		// Get the data from the previous try
		data = (GameData) getIntent().getSerializableExtra("game_data");
		Log.d(getClass().getSimpleName(), "Data : " + data);

		// Update the UI with these data
		updateUI();

		// Set the behaviour of the button that let us submit a new letter
		((Button) findViewById(R.id.new_try_btn))
				.setOnClickListener(new LetterSubmitter());

	}

	@Override
	public void onBackPressed() {

		// Set the confirmation dialog
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				PlayingActivity.this);
		dialog.setTitle("Abort");
		dialog.setMessage("Are you sure you want to go back to the menu?");

		// Set the "Yes" option
		dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(PlayingActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		// Set the "No" option
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Show the dialog
		dialog.show();
	}

	/**
	 * Update the informations showed on the UI
	 */
	private void updateUI() {
		((TextView) findViewById(R.id.current_word)).setText(separate(data
				.getWord()));
		((TextView) findViewById(R.id.nb_tries))
				.setText(data.getNbTries() + "");
	}

	/**
	 * @param word
	 *            A string
	 * @return The same string with the letters separated by empty spaces. For
	 *         example : "foobar" => "f o o b a r "
	 */
	private String separate(String word) {
		StringBuilder sb = new StringBuilder();
		for (char c : word.toCharArray()) {
			sb.append(c);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Shows a dialog and goes back to the menu, when the game ends
	 */
	private void onGameEnd() {
		((TextView) findViewById(R.id.new_try_btn))
				.setVisibility(View.INVISIBLE);

		// Set the message dialog
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				PlayingActivity.this);
		dialog.setTitle(data.getState() == GameState.WIN ? "Congratulations!"
				: "You failed!");
		dialog.setMessage("The word was " + data.getWord());
		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Get back to the menu
				Intent intent = new Intent(PlayingActivity.this,
						MainActivity.class);
				startActivity(intent);
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
