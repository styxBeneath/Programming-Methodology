import java.io.BufferedReader;

import acmx.export.java.io.FileReader;
import sun.util.locale.StringTokenIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	private Map<String, String> base;

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		base = new HashMap<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				int index = line.indexOf(" ");
				String key = line.substring(0, index);
				base.put(key, line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		if (base.containsKey(name)) {
			NameSurferEntry entry = new NameSurferEntry(base.get(name));
			return entry;
		}
		return null;
	}
}
