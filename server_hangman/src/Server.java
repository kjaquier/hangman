import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// The word to find
	private String word;
	// The current state of the word to find
	private String state;
	// Number of attempts left
	private int attemptLeft;

	private LinkedList<String> wordList = new LinkedList<String>();

	{
		wordList.add("ANDROID");
		wordList.add("CALIFORNIA");
		wordList.add("DEPARTMENT");
		wordList.add("PLATYPUS");
		wordList.add("CHEESEBURGER");
		wordList.add("UNBEATABlE");
		wordList.add("VOCABULARY");
	}

	// Get method
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		// Read the character entered by the player
		BufferedReader in = request.getReader();
		String l = in.readLine();
		in.close();

		// Format the response
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF-8"));

		// Get the letter parameter and handle it
		String letter = (String) request.getParameter("letter");
		if (letter != null) {
			// Check if a game was created first
			if (word != null) {

				// Check if there is still some attempt left
				if (attemptLeft > 0) {
					if (word.contains(letter)) {

						// Update the state of the guessed word
						for (int i = 0; i < word.length(); i++) {
							if (word.charAt(i) == letter.charAt(0)) {
								state.toCharArray()[i] = letter.charAt(0);
							}
						}
					} else {
						attemptLeft--;
					}
					out.write(attemptLeft);
				} else {
					out.write("game-over");
				}
			}
		}

		// Get the new parameter and handle it
		String newGame = (String) request.getParameter("new");
		if (newGame != null) {
			Random rand = new Random();
			int index = rand.nextInt(wordList.size());
			word = wordList.get(index);
			state = "";
			// Initialisation of the current state (empty word)
			for (int i = 0; i < word.length(); i++)
				state += "_";

			out.write("game-start");
		}

		out.close();
	}
}
