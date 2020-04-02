package main;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import input.GitLogRetriever;
import input.JIRATicketRetriever;
import model.TicketByMonth;
import output.CSVExporter;
import utils.Reducer;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {	
		String[] keys = JIRATicketRetriever.readTicketKeys("STDCXX");
		GitLogRetriever retriever = new GitLogRetriever("https://github.com/apache/stdcxx", 
															"/home/pier/Desktop/uni/isw2/progetto/stdcxx/");
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
		CSVExporter.printCSV(tbm.toArray(new TicketByMonth[0]), "./ticketByMonth.csv");
	}
	
}
