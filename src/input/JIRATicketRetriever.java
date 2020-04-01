package input;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import utils.JSONReader;

import org.json.JSONArray;

public class JIRATicketRetriever {
	
	private static final Logger LOGGER = Logger.getLogger(JIRATicketRetriever.class.getName());
	
	private JIRATicketRetriever() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static String[] readTicketKeys(String projName) {
		Integer j = 0;
		Integer i = 0;
		Integer total = 1;
		ArrayList<String> keys = new ArrayList<>();
		//Get JSON API for closed bugs w/ AV in the project
		do {
			//Only gets a max of 1000 at a time, so must do this multiple times if res >1000
			j = i + 1000;
			String url = "https://issues.apache.org/jira/rest/api/2/search?jql=project=%22"
					+ projName + "%22AND(%22status%22=%22closed%22OR"
					+ "%22status%22=%22resolved%22)AND%22resolution%22=%22fixed%22&fields=key,resolutiondate,versions,created&startAt="
					+ i.toString() + "&maxResults=" + j.toString();
			JSONObject json;
			try {
				json = JSONReader.readJsonFromUrl(url);
				JSONArray issues = json.getJSONArray("issues");
				total = json.getInt("total");
				for (; i < total && i < j; i++) {
					//Iterate through each res
					String key = issues.getJSONObject(i%1000).get("key").toString();
					keys.add(key);
				} 
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} 
		} while (i < total);
		return keys.toArray(new String[0]);
	}
}
