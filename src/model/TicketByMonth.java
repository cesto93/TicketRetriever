package model;

import java.time.YearMonth;

public class TicketByMonth {
	private YearMonth date;
	private int numTickets;
	
	@Override
	public String toString() {
		return "TicketByMonth [date=" + date + ", numTickets=" + 
				numTickets + "]";
	}
	
	public TicketByMonth(YearMonth date, int numTickets) {
		this.date = date;
		this.numTickets =  numTickets;
	}
	
	public YearMonth getDate() {
		return date;
	}

	public void setDate(YearMonth date) {
		this.date = date;
	}

	public int getNumTickets() {
		return numTickets;
	}
	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public int compareDate(TicketByMonth tbm) {
		return this.date.compareTo(tbm.date);
	}
	
	public static boolean sameDate(TicketByMonth a, TicketByMonth b) {
		return a.date.equals(b.date);
	}
}
