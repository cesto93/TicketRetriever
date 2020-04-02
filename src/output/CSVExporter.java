package output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import model.TicketByMonth;
import utils.MyLoggerFactory;
import utils.MyLoggerFactory.LoggerType;

public class CSVExporter {
	
	private static final Logger LOGGER = MyLoggerFactory.getLogger(CSVExporter.class.getName(), LoggerType.DEBUG);
	
	private CSVExporter() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void printCSV(TicketByMonth[] tbm, String file) {
		
	try (
		FileWriter fw = new FileWriter(file);
		CSVPrinter printer = new CSVPrinter(fw, CSVFormat.DEFAULT);	
		) {
		    printer.printRecord("data", "numeroTicket");
		    for (int i = 0; i < tbm.length; i++) {
		    	printer.printRecord(tbm[i].getDate(), tbm[i].getNumTickets());
		    }
		 } catch (IOException e) {
		     LOGGER.log(Level.SEVERE, e.getMessage(), e);
		 }
	}
}
