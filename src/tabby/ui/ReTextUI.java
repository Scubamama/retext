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
import tabby.model.Book;
import tabby.database.BooksDAO;
//import tabby.ui.UserUi;

/**
 hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class ReTextUI {
	//Logger log = LogManager.getLogger(ReTextUI.class);
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BooksDAO bookDAO = new BooksDAO();
	
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
		out.println("1) Manage users");
		out.println("2) Manage books");
		out.println("3) Manage inventory");
		out.println("4) Manage schools");
		out.println("0) Quit\n");
		out.print("? ");
	}
	

	public boolean callMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // Manage users
		//	manageUsers();
			UserUI userUi = new UserUI();
			userUi.mainMenu();
			break;
		case 2: // Manage books
		//	manageBooks();
			UserUI bookUi = new UserUI();
			bookUi.mainMenu();
			break;
		case 3: // Manage inventory
		//	manageInventory();
			break;
		case 4: // Manage schools
		//	manageSchools();
			break;

		}
		return true;
	}

	
} // end class ReTextUI
