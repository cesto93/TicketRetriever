package output;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import model.TicketByMonth;

public class CSVExporter {

	static public void printCSV(TicketByMonth[] tbm, String file) {
		
		try {
			FileWriter fw = new FileWriter(file);
			CSVPrinter printer = new CSVPrinter(fw, CSVFormat.DEFAULT);
		    printer.printRecord("mese","anno", "numeroTicket");
		    for (int i = 0; i < tbm.length; i++) {
		    	printer.printRecord(tbm[i].getMonth(), tbm[i].getYear(), tbm[i].getNumTickets());
		    }
		    printer.close();
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
	}
}
