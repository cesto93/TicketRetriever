package input;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;

import model.CommitMessageByData;
import utils.JSONReader;

public class GithubLogRetriever {
	private static final Logger LOGGER = Logger.getLogger(GithubLogRetriever.class.getName());
	
	private GithubLogRetriever() {}
	
	public static String[] getDates(String repo, String[] keys) {
		ArrayList<CommitMessageByData> commits = getDates(repo);
		ArrayList<LocalDateTime> dates = new ArrayList<>();
		for (int i = 0 ; i < keys.length; i++) {
			for (CommitMessageByData commit : commits) {
				if (commit.getMsg().contains(keys[i]) && 
						(dates.get(i) == null  || dates.get(i).isBefore(commit.getData())))
						dates.add(i, commit.getData());
			}
		}
		
		return dates.toArray(new String[0]);
	}
	
	private static ArrayList<CommitMessageByData> getDates(String repo) {
		ArrayList<CommitMessageByData> res = new ArrayList<>();
		String url = "https://api.github.com/repos/" + repo + "commits";
		JSONArray json;
		int page = 1;
		try {
			while ( (json = JSONReader.readJsonArrayFromUrl(url + "?pages=" + page)) != null) {
			
			for (int i = 0; i < json.length(); i++) {
				String msg = json.getJSONObject(i).getJSONObject("commit").get("message").toString();
				String data = json.getJSONObject(i).getJSONObject("commit").get("data").toString();
				res.add(new CommitMessageByData(msg, LocalDateTime.parse(data, DateTimeFormatter.ISO_INSTANT)));
			} 
			page++;
		}
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return res;
	}
}
