package com.example.unit2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ResponseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response);
		Question question = (Question) getIntent().getSerializableExtra("question");
		String choice = getIntent().getStringExtra("choice");
		((TextView) findViewById(R.id.responseText)).setText(
			    question.getAnswer().equals(choice) ? "Good job!" : "Try again!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.response, menu);
		return true;
	}

}
