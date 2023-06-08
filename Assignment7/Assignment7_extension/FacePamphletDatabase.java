
/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import acm.graphics.GImage;
import acmx.export.java.io.FileReader;

public class FacePamphletDatabase implements FacePamphletConstants {

	private Map<String, FacePamphletProfile> base;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */

	public FacePamphletDatabase() {
		base = new HashMap<>();
		readFile();

	}

	/**
	 * This method adds the given profile to the database. If the name associated
	 * with the profile is the same as an existing name in the database, the
	 * existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String name = profile.getName();
		base.put(name, profile);

	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {

		return base.get(name);
	}

	/**
	 * This method removes the profile associated with the given name from the
	 * database. It also updates the list of friends of all other profiles in the
	 * database to make sure that this name is removed from the list of friends of
	 * any other profile.
	 * 
	 * If there is no profile in the database with the given name, then the database
	 * is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		base.remove(name);

	}

	/**
	 * This method returns true if there is a profile in the database that has the
	 * given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (base.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}

	// this method removes a name from each profile's friendlists
	public void removeFromFriendlists(String name) {
		for (String key : base.keySet()) {
			FacePamphletProfile prof = base.get(key);
			prof.removeFriend(name);
		}
	}

	private void readFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("profiles.txt"));
			while (true) {
				String line = br.readLine();
				if (line == null || line.equals("")) {
					break;
				} else {
					// we use '_' as a tokenizer
					StringTokenizer tok = new StringTokenizer(line, "_");
					String name = tok.nextToken();
					String status = tok.nextToken();
					// we save the name of image in the file
					String image = tok.nextToken();
					FacePamphletProfile profile = new FacePamphletProfile(name);
					if (status.equals("emptyStatus")) {
						status = "";
					}
					profile.setStatus(status);
					if (image.equals("emptyImage")) {
						profile.setImage(null);
					} else {
						profile.setImage(new GImage(image));
						profile.setImageName(image);
					}
					while (tok.hasMoreTokens()) {
						profile.addFriend(tok.nextToken());
					}
					base.put(name, profile);
				}

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateFile() {
		try {
			PrintWriter wr = new PrintWriter(new FileWriter("profiles.txt"));

			for (String name : base.keySet()) {
				String line = "";

				FacePamphletProfile profile = base.get(name);
				String status = profile.getStatus();
				if (status.equals("")) {
					status = "emptyStatus";
				}
				line += profile.getName() + "_" + status + "_" + profile.getImageName();

				Iterator<String> friends = profile.getFriends();
				while (friends.hasNext()) {
					line += "_" + friends.next();
				}

				wr.println(line);
			}
			wr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
