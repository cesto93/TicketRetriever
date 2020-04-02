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
	
	public static void printCSV(TicketByMonth[] tbms, String file) {
		try (
				FileWriter fw = new FileWriter(file);
				CSVPrinter printer = new CSVPrinter(fw, CSVFormat.DEFAULT);	
			) {
		    	printer.printRecord("data", "numeroTicket");
		    	for (TicketByMonth tbm : tbms) {
		    	printer.printRecord(tbm.getDate(), tbm.getNumTickets());
		    	}
		} catch (IOException e) {
		     LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
