package tabby.ui;

import static java.lang.System.out;

//import java.awt.List;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tabby.model.AUser;
import tabby.database.UsersDAO;
import tabby.model.BookTitles;
import tabby.database.BookTitlesDAO;
//import tabby.ui.UserUi;

/**
 * console version and hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class ReTextUI {
	//Logger log = LogManager.getLogger(ReTextUI.class);
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BookTitlesDAO bookDAO = new BookTitlesDAO();
	AUser u = null;
	
	public void mainMenu() throws SQLException {
		
		
		Logger log = LogManager.getLogger(ReTextUI.class.getName());
		log.debug("Test message!!!");
		log.info("Test message!!!");
		log.error("Test message!!!", new NullPointerException("foo"));
		log.debug("Program starting mainMenu()");
		
		out.println("Welcome to Retext.");
		boolean keepRunning = true;
		while(keepRunning)
		{
			if (u != null) log.debug("user = " + u.getUserName() +" id = " + u.getId());
			else log.debug("no current user");
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thank you for using ReText. Goodbye.");
	}
	
	public int readUserChoice() {
		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public void printMainMenu() {
		out.println("1) Manage Users");
		out.println("2) Manage Books");
		out.println("3) Manage Inventory");
		out.println("4) Manage Schools");
		out.println("5) Login");
		out.println("6) Create User Id");
		out.println("7) Logout");
		out.println("0) Quit\n");
		out.print("? ");
	}

	public boolean callMenuItem(int choice) throws SQLException {
		UserUI userUi = new UserUI();
		// AUser u;
				
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // Manage users
		//	UserUI userUi = new UserUI();
		//	userUi.mainMenu(u);
			userUi.mainMenu();
			break;
		case 2: // Manage books
		//	manageBooks();
			BookTitlesUI bookUi = new BookTitlesUI();
			bookUi.mainMenu();
			break;
		case 3: // Manage inventory
		//	manageInventory();
			InventoryUI invUi = new InventoryUI();
			invUi.mainMenu();
			break;
		case 4: // Manage schools
			SchoolUI schoolUi = new SchoolUI();
			schoolUi.mainMenu();
			break;
		case 5: // Login
		//	UserUI userUi = new UserUI();
		//	UserUI.login();
			u = userUi.login(); 
			break;
		case 6: // Create User Id
		//	UserUI userUi = new UserUI();
			u = userUi.createUserId(); 
			break;
		case 7: // Logout
		//	u = userUi.logout();   // sets u = null and says goodbye
			u = null;
			break;
		} // end switch
		return true;
	}

} // end class ReTextUI
