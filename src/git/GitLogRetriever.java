package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GitLogRetriever {
	private static final Logger LOGGER = Logger.getLogger(GitLogRetriever.class.getName());
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
			if (p.waitFor() != 0 && LOGGER.isLoggable(Level.SEVERE) ) 
				LOGGER.log(Level.SEVERE, readErrors(p));
		} catch (IOException | InterruptedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}
	
	public LocalDate[] getDates(String[] keys) {
		ArrayList<LocalDate> res = new ArrayList<>();
		for (String key : keys) {
			ProcessBuilder pb = new ProcessBuilder( "git", "log", "--date=short", "--pretty=format:\"%cd\"",
									"--max-count=1", "--grep=" + key);
			pb.directory(repo);
			
			try {
				Process p = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
			
				while ((line = stdInput.readLine()) != null) {
					line = line.substring(1, line.length() - 1);
					res.add(LocalDate.parse(line));
				}
				if (p.waitFor() != 0 && LOGGER.isLoggable(Level.SEVERE))
					LOGGER.log(Level.SEVERE, readErrors(p));
			} catch (IOException | InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
		return res.toArray(new LocalDate[0]);
	}
	
	private static String readErrors(Process p) throws IOException  {
		StringBuilder bld = new StringBuilder();
		String line = "";
		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		while ((line = stdError.readLine()) != null)
			bld.append(line);
		return bld.toString();
	}
}
