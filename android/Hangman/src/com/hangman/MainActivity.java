package com.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Button) findViewById(R.id.playbtn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
				GameData data = new GameData();
				data.setWord("_ _ _ _ _ _ _ _ _ _");
				data.setNbTries(11);
				int nbLetters = 'Z' - 'A' + 1;
				String[] letters = new String[nbLetters];
				for (int i = 0; i < nbLetters; i++) {
					letters[i] = String.format("%c", 'A' + i);
				}
				data.setLetters(letters);
				intent.putExtra("game_data", data);
				startActivity(intent);
				
			}
		});
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
