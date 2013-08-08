package com.hangman;

import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LetterChoiceDialog extends DialogFragment {
	
	private String[] letters;
	private DialogInterface.OnClickListener clickHandler;
	
	public LetterChoiceDialog() {
		super();
	}
	
	public LetterChoiceDialog(String[] letters, DialogInterface.OnClickListener clickHandler) {
		this.letters = letters;
		this.clickHandler = clickHandler;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Submit a letter");
	    builder.setItems(letters, clickHandler);
	    return builder.create();
	}

	public String getSelectedChar() {
		return selection;
	}
}
