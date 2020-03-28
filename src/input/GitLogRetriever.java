package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GitLogRetriever {
	private static final Logger LOGGER = Logger.getLogger(GitLogRetriever.class.getName());
	
	private GitLogRetriever() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static String[] getDates(String repoPath, String[] keys) {
		ProcessBuilder[] pb = new ProcessBuilder[keys.length]; 
		ArrayList<String> res = new ArrayList<>();
		File file = new File(repoPath);
		
		for (int i = 0; i < keys.length; i++) {
			pb[i] = new ProcessBuilder( "git", "log", "--date=short", "--pretty=format:\"%cd\"",
					"--max-count=1", "--grep=" + keys[i]);
			pb[i].directory(file);
			
			try {
				Process p = pb[i].start();
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// Read the output from the command
			String line = "";
			while ((line = stdInput.readLine()) != null)
				res.add(line);
	
			while ((line = stdError.readLine()) != null)
					LOGGER.log(Level.WARNING, line);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return res.toArray(new String[0]);
	}
}
