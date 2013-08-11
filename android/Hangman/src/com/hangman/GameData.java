package com.hangman;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

import android.util.Log;

/**
 * Simple class to store all the relevant data for the game's current state
 */
public class GameData implements Serializable {

	private static final long serialVersionUID = -2936824930949689617L;

	/**
	 * Parts of the word that have been discovered. Non-discovered letters are replaced by '_'.
	 */
	private String word;
	
	/**
	 * Number of remaining tries. Decreased only when a try fails.
	 */
	private int nbTries;
	
	/**
	 * Remaining set of letters to be submitted.
	 */
	private LinkedList<String> letters;
	
	/**
	 * Current state of the game, see {@link GameState}
	 */
	private GameState state;

	/**
	 * Parse a string to get the data. Format is : "<word>;<state>;<nbTries>"
	 */
	public void read(Scanner in) {
		String[] fields = in.nextLine().split(";");
		Log.d(getClass().getSimpleName(),
				String.format("Parsed data : [%s,%s,%s]", (Object[]) fields));
		word = fields[0];
		if (fields[1].equals("you-win"))
			state = GameState.WIN;
		else if (fields[1].equals("game-over"))
			state = GameState.LOSE;
		else
			state = GameState.PLAYING;
		nbTries = Integer.parseInt(fields[2]);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getNbTries() {
		return nbTries;
	}

	public void setNbTries(int nbTries) {
		this.nbTries = nbTries;
	}

	public String[] getLetters() {
		String[] array = new String[letters.size()];
		int i = 0;
		for (String letter : letters) {
			array[i++] = letter;
		}
		return array;
	}

	public void initLetters() {
		int nbLetters = 'Z' - 'A' + 1;
		letters = new LinkedList<String>();
		for (int i = 0; i < nbLetters; i++) {
			letters.add(String.format("%c", 'A' + i));
		}
	}

	public void deleteLetter(String letter) {
		letters.remove(letter);
	}

	@Override
	public String toString() {
		return String.format("[%s,%s,%s]", word, state, nbTries);
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

}
