package com.hangman;

import java.io.Serializable;
import java.util.Scanner;


public class GameData implements Serializable {

	private static final long serialVersionUID = -2936824930949689617L;
	
	private String word;

    public void read(Scanner in) {
        word = in.nextLine();
    }
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}
