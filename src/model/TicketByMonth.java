package model;

public class TicketByMonth {
	private int month;
	private int year;
	private int numTickets;
	
	@Override
	public String toString() {
		return "TicketByMonth [month=" + month + ", year=" + year + ", numTickets=" + 
				numTickets + "]";
	}
	
	public TicketByMonth(int month, int year, int numTickets) {
		this.setMonth(month);
		this.setYear(year);
		this.numTickets =  numTickets;
	}

	public int getNumTickets() {
		return numTickets;
	}
	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int compareDate(TicketByMonth tbm) {
		return (this.getYear() - tbm.getYear()) * 12 + (this.getMonth() - tbm.getMonth());
	}
	
	public static boolean sameDate(TicketByMonth a, TicketByMonth b) {
		return (a.getMonth() == b.getMonth()) && (a.getYear() == b.getYear());
	}
}
