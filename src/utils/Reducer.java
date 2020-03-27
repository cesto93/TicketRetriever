package utils;

import java.util.List;

import model.TicketByMonth;

public class Reducer {
	
	private Reducer() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void reduce(List<TicketByMonth> tbm) {
		for (int i = 0; i < tbm.size(); i++) 
			for (int j = i; j < tbm.size(); j++) 
				if (TicketByMonth.sameDate(tbm.get(i), tbm.get(j))) {
					tbm.get(i).setNumTickets(tbm.get(i).getNumTickets() + tbm.get(j).getNumTickets());
					tbm.remove(j);
			}
	}
}
