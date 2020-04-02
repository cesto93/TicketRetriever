package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLoggerFactory {
	
	private MyLoggerFactory() {
		
	}
	
	public static Logger getLogger(String name, LoggerType loggerType) {
		Logger log = Logger.getLogger(name);
		switch (loggerType) {
		case DEBUG:
			log.setLevel(Level.ALL);
			break;
		default:
			log.setLevel(Level.WARNING);
		}
		return log;
	}
	
	public enum LoggerType {
	    DEBUG, NORMAL;
	  }
}
