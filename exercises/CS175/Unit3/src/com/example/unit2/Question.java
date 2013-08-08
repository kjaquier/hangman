package com.example.unit2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Question implements Serializable {
	private static final long serialVersionUID = -3276091907050158477L;
	
	private String text;
    private List<String> choices = new ArrayList<String>();
    private String answer;

    public void read(Scanner in) {
        text = in.nextLine();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.startsWith("*")) { line = line.substring(1); answer = line; }
            choices.add(line);
        }
    }

    public List<String> getChoices() { return Collections.unmodifiableList(choices); }
    public String getText() { return text; }
    public String getAnswer() { return answer; }
 
    public String toString() {
        String result = text + "\n";
        for (String c : choices) {
            if (c == answer) result += "*";
            result += c;
        }
        return result;
    }
}
