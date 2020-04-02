package input;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import model.CommitMessageByData;
import utils.JSONReader;

public class GithubLogRetriever {
	private static final Logger LOGGER = Logger.getLogger(GithubLogRetriever.class.getName());
	
	private GithubLogRetriever() {
		throw new IllegalStateException("Utility class");
	}
	
	public static LocalDateTime[] getDates(String repo, String[] keys) {
		ArrayList<CommitMessageByData> commits = getDates(repo);
		ArrayList<LocalDateTime> dates = new ArrayList<>();
		for (int i = 0 ; i < keys.length; i++) {
			for (CommitMessageByData commit : commits) {
				if (commit.getMsg().contains(keys[i]) && 
						(dates.get(i) == null  || dates.get(i).isBefore(commit.getData())))
						dates.add(i, commit.getData());
			}
		}
		
		return dates.toArray(new LocalDateTime[0]);
	}
	
	private static ArrayList<CommitMessageByData> getDates(String repo) {
		ArrayList<CommitMessageByData> res = new ArrayList<>();
		String url = "https://api.github.com/repos/" + repo + "commits";
		JSONArray jsons;
		int page = 1;
		try {
			while ( (jsons = JSONReader.readJsonArrayFromUrl(url + "?pages=" + page)) != null) {
				for (int i = 0; i < jsons.length(); i++) {
					JSONObject commit = jsons.getJSONObject(i).getJSONObject("commit");
					String msg = commit.get("message").toString();
					String data = commit.get("data").toString();
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
