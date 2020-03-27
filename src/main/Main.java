package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import input.GitLogRetriever;
import input.JIRATicketRetriever;
import model.TicketByMonth;
import output.CSVExporter;
import utils.Reducer;

public class Main {
	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {	
		String[] keys = JIRATicketRetriever.readTicketKeys("STDCXX");
		String[] dates = GitLogRetriever.getDates("/home/pier/Desktop/uni/isw2/progetto/stdcxx/", keys);
		ArrayList<TicketByMonth> tbm = new ArrayList<>();
		
		for (int i = 0; i < dates.length; i++) {
			String s = dates[i].substring(1, dates[i].length() - 1);
			LocalDate ld = LocalDate.parse(s);
			tbm.add(new TicketByMonth(ld.getMonthValue(), ld.getYear(), 1));
			LOGGER.log(Level.INFO, tbm.get(i).toString());
		}
		Collections.sort(tbm);
		System.out.println("Reduced:");
		Reducer.reduce(tbm);
		for (int i = 0; i < tbm.size(); i++) {
			LOGGER.log(Level.INFO, tbm.get(i).toString());
		}
		CSVExporter.printCSV(tbm.toArray(new TicketByMonth[0]), "./ticketByMonth.csv");
	}
	
}
