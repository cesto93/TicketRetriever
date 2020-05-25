package launch;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import git.GitLogRetriever;
import jira.JIRATicketRetriever;
import model.TicketByMonth;
import output.CSVExporter;
import utils.Reducer;

public class TicketControlChart {
	private static final Logger LOGGER = Logger.getLogger(TicketControlChart.class.getName());
	
	
	public static void main(String[] args) {	
		final String resfile = "./ticketByMonth.csv";
		final String repoUrl = "https://github.com/apache/stdcxx";
		final String repoPath = "/home/pier/Desktop/uni/isw2/progetto/stdcxx/";
		final String projName = "STDCXX";
		String[] keys = JIRATicketRetriever.readTicketKeys(projName);
		createControlChart(keys, repoUrl, repoPath, resfile);
	}
	
	public static void createControlChart(String[] keys, String repoUrl, String repoPath, String resfile) {
		GitLogRetriever retriever = new GitLogRetriever(repoUrl, repoPath);
		LocalDate[] dates = retriever.getDates(keys);
		ArrayList<TicketByMonth> tbm = new ArrayList<>();
		
		for (LocalDate date : dates) {
			tbm.add(new TicketByMonth(YearMonth.from(date), 1));
		}
		
		Comparator<TicketByMonth> compareByData = (TicketByMonth t1, TicketByMonth t2) -> t1.compareDate(t2);
		Collections.sort(tbm, compareByData);
		Reducer.reduce(tbm);
		Reducer.addEmptyMonths(tbm);
		LOGGER.log(Level.INFO, tbm.toString());
		CSVExporter.printCSV(tbm.toArray(new TicketByMonth[0]), resfile);
	}
	
}
