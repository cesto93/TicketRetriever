package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import input.GitLogRetriever;
import input.JIRATicketRetriever;
import model.TicketByMonth;
import output.CSVExporter;
import utils.Reducer;

public class Main {
	public static void main(String[] args) {
		String projName ="STDCXX";
		GitLogRetriever log = new GitLogRetriever();
		
		String[] keys = JIRATicketRetriever.readTicketKeys(projName);
		String[] dates = log.getDates(keys);
		ArrayList<TicketByMonth> tbm = new ArrayList<TicketByMonth>();
		
		for (int i = 0; i < dates.length; i++) {
			String s = dates[i].substring(1, dates[i].length() - 1);
			LocalDate ld = LocalDate.parse(s);
			tbm.add(new TicketByMonth(ld.getMonthValue(), ld.getYear(), 1));
			System.out.println(tbm.get(i).toString());
		}
		Collections.sort(tbm);
		System.out.println("Reduced:");
		Reducer.reduce(tbm);
		for (int i = 0; i < tbm.size(); i++) {
			System.out.println(tbm.get(i).toString());
		}
		CSVExporter.printCSV(tbm.toArray(new TicketByMonth[0]), "./ticketByMonth.csv");
	}
	
}
