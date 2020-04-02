package utils;

import java.util.List;

import model.TicketByMonth;

public class Reducer {
	
	private Reducer() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void reduce(List<TicketByMonth> tbm) {
		for (int i = 0; i < tbm.size(); i++) {
			int j = i + 1;
			while( j < tbm.size()) 
				if (TicketByMonth.sameDate(tbm.get(i), tbm.get(j))) {
					TicketByMonth temp = new TicketByMonth(tbm.get(i).getDate(),
															tbm.get(i).getNumTickets() + tbm.get(j).getNumTickets());
					tbm.set(i, temp);
					tbm.remove(j);
				} else {
					j++;
				}
		}
	}
}
