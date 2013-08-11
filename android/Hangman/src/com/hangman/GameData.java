package com.hangman;

import java.io.Serializable;
import java.util.Scanner;


public class GameData implements Serializable {

	private static final long serialVersionUID = -2936824930949689617L;
	
	private String word;
	private int nbTries;
	private String[] letters;
	
    public void read(Scanner in) {
        String[] line = in.nextLine().split(";");
        word = line[0];
        nbTries = Integer.parseInt(line[1]);
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
		return letters;
	}

	public void setLetters(String[] letters) {
		this.letters = letters;
	}

	public void deleteLetter(String letter) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		return String.format("[%s,%s]", word, nbTries);
	}
	
}
