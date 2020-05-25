package git;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GitLogRetriever {
	private static final Logger LOGGER = Logger.getLogger(GitLogRetriever.class.getName());
	private static final String SHORTDATE = "--date=short";
	
	private GitDataProvider dataProvider;
	
	
	public GitLogRetriever(GitDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	public LocalDate getDate(String key) {
		LocalDate res = null;
		try {
			BufferedReader stdInput = dataProvider.getResultStream("git", "log", SHORTDATE, "--pretty=format:\"%cd\"",
										"--max-count=1", "--grep=" + key);
			for (String line = stdInput.readLine(); line != null; line = stdInput.readLine()) {
				res = LocalDate.parse(line.substring(1, line.length() - 1));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		return res;
	}
}
