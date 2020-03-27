package utils;

import java.util.ArrayList;

import model.TicketByMonth;

public class Reducer {
	
	public static void reduce(ArrayList<TicketByMonth> tbm) {
		for (int i = 0; i < tbm.size(); i++) 
			for (int j = i; j < tbm.size(); j++) 
				if (TicketByMonth.sameDate(tbm.get(i), tbm.get(j))) {
					tbm.get(i).setNumTickets(tbm.get(i).getNumTickets() + tbm.get(j).getNumTickets());
					tbm.remove(j);
			}
	}
}
