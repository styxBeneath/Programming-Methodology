
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class HangmanLexicon {
	private ArrayList<String> words = new ArrayList<String>();

	public HangmanLexicon() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ShorterLexicon.txt"));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				words.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	}

}
