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


/**
 hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class UserUI {
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	Logger log = LogManager.getLogger(UserUI.class.getName());
	
	

	public void mainMenu() throws SQLException {
		
		log.debug("Program starting mainMenu()");
		
		out.println("Welcome to Manage Users.");
		boolean keepRunning = true;
		while(keepRunning)
		{
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thank you for managing users. ");
	} //end mainMenu
	
	public int readUserChoice() {
		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public void printMainMenu() {
		out.println("1) Add a user");
		out.println("2) Search users");
		out.println("3) List all users");
		out.println("4) Modify a user");
		out.println("5) Delete a user");
		out.println("0) Quit and return to main menu\n");
		out.print("? ");
	}
	

	public boolean callMenuItem(int choice) throws SQLException {
		AUser u; 			// just for now to shut eclipse up
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // add
			u = addUser();				//problem!!!! It can't see u
			break;
		case 2: // search
			searchUsers();
			break;
		case 3: // list all
			listAllUsers();
			break;
		case 4: // modify
			modifyUser();
			break;
		case 5: // delete
			deleteTheUser();
			break;

		}
		return true;
	}

	public AUser addUser()  throws SQLException {
		int card = 0;  // default user does not take cards
		out.println("Please enter your email: ");
		String uEmail = keyboard.nextLine();
		out.println("Please enter a user name: ");
		String uName = keyboard.nextLine();
		out.println("Please enter a password: ");
		String uPass = keyboard.nextLine();
		out.println("Please enter your school name: ");
		String uSchool = keyboard.nextLine();
		out.println("Do you want buyers to pay with a card? (y/n): ");
		String uCard = keyboard.nextLine();
		if(uCard.equals("y")) card = 1;
	
		out.println("Thank you "+" "+uName+" "+uEmail+" "+uPass+" "+uSchool+" "+uCard+" "+card);
		AUser newU = new AUser(uEmail,uName,uPass,card,uSchool);
	
		aUserDAO.save(newU);
		out.println(" ");
		listAllUsers();
		return newU;
	} // end addUser
	
	public AUser addUser(String uName, String uPass)  throws SQLException {
		int card = 0;  // default user does not take cards
		out.println("Please enter your email: ");
		String uEmail = keyboard.nextLine();

		out.println("Please enter your school name: ");
		String uSchool = keyboard.nextLine();
		out.println("Do you want buyers to pay with a card? (y/n): ");
		String uCard = keyboard.nextLine();
		if(uCard.equals("y")) card = 1;
	
		out.println("Thank you "+" "+uName+" "+uEmail+" "+uPass+" "+uSchool+" "+uCard+" "+card);
		AUser newU = new AUser(uEmail,uName,uPass,card,uSchool);

		aUserDAO.save(newU);
		out.println(" ");
		listAllUsers();
		return newU;
	} // end addUser
	
	public void searchUsers() throws SQLException {
		out.println("Displays users with a particular username. ");
		out.print("Search for users (enter as much of the name as you know): ");
		String query = keyboard.nextLine();
		List<AUser> results = aUserDAO.searchUsers(query);
		if(results.isEmpty()){
			out.println("No matches.");
		}else {
			displayUsersPaged(results);
		}
	}
	
	private void listAllUsers() throws SQLException {
	
		// pulls users out of the db and puts them in an ArrayList
		List<AUser> results = aUserDAO.listMyUsers(); 
		displayUsersPaged(results); // prints above ArrayList
		
		out.println(" ");
	}
	
	private void displayUsersPaged(List<AUser> results) throws SQLException {	
		
		out.println("Current ReText users: ");
		out.println("db-id  \t email    \t\t username \t school ");
		int count = 0;
		for (AUser u : results) {
			out.println(u.getId() + "\t " + u.getUserEmail()  + "\t " + u.getUserName()  + "\t " + u.getUserSchool());
			count++;
			if (count % 20 == 0) { //pause every 20 lines
				out.println("Press enter for more or q to quit  > ");
				String key = keyboard.nextLine();
				if (key.equals("q")) {
					break;
				}
			}
		} //end for
		
	} // end displayUsersPaged()
	
	private void modifyUser()  throws SQLException {
		
		out.println("Please type the database id for the user that you want to modify. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		AUser u = aUserDAO.get(id);    
		if (u == null) {
			out.println("There is no user with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		out.println("Here is the user that you asked to modify.  ");
		out.println(u.getId() + "\t username: " + u.getUserName() );
		out.print("What would you like to change the username to?  ");
		String text = keyboard.nextLine();
		u.setUserName(text);
		aUserDAO.save(u);    //put it in the db
	} // end  modifyUser()
	
	private void deleteTheUser() throws SQLException {
	
		out.println("Please type the database id for the user that you want to delete. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		AUser u = aUserDAO.get(id);    
		
		if (u == null) {
			out.println("There is no user with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		out.print("Here is the user that you asked to delete.  ");	
		out.println(u.getUserName());
		out.print("Delete? (y/n) ");
		String text = keyboard.nextLine();
		if (text.equals("y")) {
			aUserDAO.delete(id);
			out.println(u.getUserName() + " Deleted. ");
		}
	}// end deleteTheUser
	
	public AUser login() throws SQLException {
		out.println("User name: ");
		String uName = keyboard.nextLine();
		//out.println("Id?  ");
		//Integer id = readUserChoice();
		
		AUser u = aUserDAO.get(uName);    // gets all fields from db
		
		if (u == null) {
			out.println("There is no user with the user name  " + uName + ". Continuing to create a new user id.");
			u = createUserId(uName);
			//return;
			out.println("Welcome " + u.getUserName() + "!");
		} else {
			out.println("Hello " + u.getUserName() + "!");
		}
		log.info("End of login");
		return u;
		
	}// end login
	
	
	public AUser createUserId(String uName) throws SQLException {
		
		out.print("Please enter a password: ");
		String uPass = keyboard.nextLine();
		AUser u = new AUser(uName,uPass);
		// add a new user to the db here
		addUser(uName, uPass);  //does all the asking and adds user to db
		out.println("User " + u.getUserName() + " Created. ");
		
		return u;
	}// end createUserId
	

	public AUser createUserId() throws SQLException {
		
		out.println("User name: ");
		String uName = keyboard.nextLine();
		out.print("Please enter a password: ");
		String uPass = keyboard.nextLine();
		AUser u = new AUser(uName,uPass);
		// add a new user to the db here
		addUser(uName, uPass);  //does all the asking and adds
		
		out.println("User " + u.getUserName() + " Created. ");
		return u;
		
	}// end createUserId	
		
	
} // end class ReTextUI
