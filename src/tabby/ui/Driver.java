package tabby.ui;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class to learn about MySql and JDBC
 * Opens and closes a MySql connection, and uses prepared statements to access a database
 * 
 * @author Holly Williams
 *
 */

public class Driver {

	static Logger log = LogManager.getLogger(Driver.class);
	
	
	public static void main(String[] args) throws Exception {
		log.error("Starting main");
		log.warn("Warn");

		ReTextUI ui = new ReTextUI();
		ui.mainMenu();

	//  run as java application

	} // end main
	
} // end Driver class
