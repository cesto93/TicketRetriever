package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GitLogRetriever {
	Runtime rt;
	public GitLogRetriever() {
		rt = Runtime.getRuntime();
	}
	
	public String[] getDates(String[] keys) {
		ProcessBuilder pb[] = new ProcessBuilder[keys.length]; 
		ArrayList<String> res = new ArrayList<String>();
		File file = new File("/home/pier/Desktop/uni/isw2/progetto/stdcxx/");
		
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
					System.out.println(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res.toArray(new String[0]);
	}
}
