package com.hangman;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

import android.util.Log;

public class GameData implements Serializable {

	private static final long serialVersionUID = -2936824930949689617L;

	private String word;
	private int nbTries;
	private LinkedList<String> letters;
	private GameState state;

	public void read(Scanner in) {
		String[] fields = in.nextLine().split(";");
		Log.d(getClass().getSimpleName(), String.format("Parsed data : [%s,%s,%s]", (Object[])fields));
		word = fields[0];
		state = fields[1].equals("you-win") ? 
				GameState.WIN : 
				(fields[1].equals("game-over") ? 
						GameState.LOSE : 
						GameState.PLAYING);
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
