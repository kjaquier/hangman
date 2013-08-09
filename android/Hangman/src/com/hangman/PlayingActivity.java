package com.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
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
		
		/*class SendRequestTask extends AsyncTask<URL, Void, Void> {

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
		}*/
		
		// Get data from the previous try
		Log.d(getClass().getSimpleName(), "Going for a new try");
		data = (GameData) getIntent().getSerializableExtra("game_data");
		Log.d(getClass().getSimpleName(), "Data : " + data);

		// Update the UI with these data
		((TextView) findViewById(R.id.current_word)).setText(data.getWord());
		((TextView) findViewById(R.id.nb_tries)).setText("" + data.getNbTries());
		Log.d(getClass().getSimpleName(), "UI updated!");
		
		// Set the behaviour of the button that let us submit a new letter
		Button playBtn = ((Button) findViewById(R.id.playbtn));
		Log.d(getClass().getSimpleName(), "Got the button");
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
								Log.d(getClass().getSimpleName(), "Submitted : " + letter);
								data.setNbTries(data.getNbTries() - 1);
								data.deleteLetter(letter);
								Intent intent = new Intent(PlayingActivity.this, PlayingActivity.class);
								intent.putExtra("game_data", data);
								startActivity(intent);
							}
							
						});
					    return builder.create();
					}

				};
				letterChoiceDialog.show(PlayingActivity.this.getFragmentManager(), "lettersdialog");
			}
		});
		Log.d(getClass().getSimpleName(), "Button set");
		
		/*try {
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
