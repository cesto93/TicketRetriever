package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.MyLoggerFactory;
import utils.MyLoggerFactory.LoggerType;

public class GitLogRetriever {
	private static final Logger LOGGER = MyLoggerFactory.getLogger(GitLogRetriever.class.getName(), LoggerType.DEBUG);
	private File repo;
	private File parent;
	
	public GitLogRetriever(String repoURL, String repoPath) {
		this.repo = new File(repoPath);
		parent = repo.getParentFile();
		if (!repoExist()) 
			createRepo(repoURL);
	}
	
	private boolean repoExist() {
		if (!repo.exists() || !repo.isDirectory())
			return false;
		
		File git = new File(this.repo.getAbsolutePath() + File.separator + ".git");
		return (git.exists());
	}
	
	//no checkout
	private void createRepo(String repoURL) {
		LOGGER.log(Level.INFO, "creating repo");
		ProcessBuilder pb = new ProcessBuilder( "git", "clone", repoURL, "-n");
		pb.directory(parent);
		try {
			Process p = pb.start(); 
			if (p.waitFor() != 0) 
				LOGGER.log(Level.SEVERE, readErrors(p));
		} catch (IOException | InterruptedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}
	
	public String[] getDates(String[] keys) {
		ProcessBuilder[] pb = new ProcessBuilder[keys.length]; 
		ArrayList<String> res = new ArrayList<>();
		
		for (int i = 0; i < keys.length; i++) {
			pb[i] = new ProcessBuilder( "git", "log", "--date=short", "--pretty=format:\"%cd\"",
					"--max-count=1", "--grep=" + keys[i]);
			pb[i].directory(repo);
			
			try {
			Process p = pb[i].start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while ((line = stdInput.readLine()) != null)
				res.add(line);
			if (p.waitFor() != 0)
				LOGGER.log(Level.SEVERE, readErrors(p));
			} catch (IOException | InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
		return res.toArray(new String[0]);
	}
	
	private static String readErrors(Process p) throws IOException  {
		String s = "";
		String line = "";
		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		while ((line = stdError.readLine()) != null)
			s = s + line;
		return s;
	}
}
