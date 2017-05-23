package tabby.ui;

import static java.lang.System.out;

//import java.awt.List;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tabby.model.AUser;
import tabby.database.BookTitlesDAO;
import tabby.database.UsersDAO;
import tabby.model.BookTitles;
import tabby.database.SchoolDAO;
import tabby.model.School;

/**
 * console version of school located, add a school
 * hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class SchoolUI {
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BookTitlesDAO bookDAO = new BookTitlesDAO();
	SchoolDAO schoolDAO = new SchoolDAO();
	
	public void mainMenu() throws SQLException {
		
		Logger log = LogManager.getLogger(SchoolUI.class.getName());

		//log.error("Test message!!!", new NullPointerException("foo"));
		log.debug("Program starting mainMenu() in SchoolUI");
		
		out.println("Welcome to School Management.");
		boolean keepRunning = true;
		while(keepRunning)
		{
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thank you for managing schools. ");
	}
	
	public int readUserChoice() {
		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public void printMainMenu() {
		out.println("1) Add a school");
		out.println("2) Search schools");
		out.println("3) List all schools");
		out.println("4) Modify a school");
		out.println("5) Delete a school");
		out.println("0) Quit and return to Main Menu\n");
		out.print("? ");
	}
	
	public boolean callMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // add
			addSchool();
			break;
		case 2: // search
			searchSchools();
			break;
		case 3: // list all
			listSchools();
			break;
		case 4: // modify
			modifySchool();
			break;
		case 5: // delete
			deleteSchool();
			break;
		}
		return true;
	}

	public void addSchool()  throws SQLException {
	
		out.println("Please enter the school name: ");
		String name = keyboard.nextLine();
		out.println("Please enter the school nick name: ");
		String nickName = keyboard.nextLine();
		out.println("Please enter the school city: ");
		String city = keyboard.nextLine();
		out.println("Please enter the campus name: ");
		String campus = keyboard.nextLine();
	
		out.println("New school: "+" "+name+" "+nickName+" "+city+" "+campus);
		School school = new School(name,nickName,city,campus);
	
		schoolDAO.save(school);
		out.println(" ");
		listSchools();
	} // end addSchool
	
	public void searchSchools() throws SQLException {
		out.println("Displays schools with a particular name. ");
		out.print("Search for schools (enter as much of the name as you know): ");
		String query = keyboard.nextLine();
		List<School> results = schoolDAO.searchSchool(query);
		if(results.isEmpty()){
			out.println("No matches.");
		}else {
			displaySchoolsPaged(results);
		}
	}// end searchSchools
	
	private void listSchools() throws SQLException {
	
		// pulls schools out of the db and puts them in an ArrayList
		List<School> results = schoolDAO.listSchools(); 
		displaySchoolsPaged(results); // prints above ArrayList
		
		out.println(" ");
	} // end listSchools
	
	private void displaySchoolsPaged(List<School> results) throws SQLException {	
		
		out.println("Current ReText schools: ");
		out.println("db-id  \t name    \t\t nick name \t city \t campus ");
		int count = 0;
		for (School s : results) {
			out.println(s.getId() + "\t " + s.getName()  + "\t " + s.getNickName()  + "\t " + s.getCity() + "\t " + s.getCampus());
			count++;
			if (count % 20 == 0) { //pause every 20 lines
				out.println("Press enter for more or q to quit  > ");
				String key = keyboard.nextLine();
				if (key.equals("q")) {
					break;
				}
			}
		} //end for
		
	} // end displaySchoolsPaged()
	
	private void modifySchool()  throws SQLException {
		
		out.println("Please type the database id for the school that you want to modify. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		School s = schoolDAO.get(id);    
		if (s == null) {
			out.println("There is no school with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		
		out.println("Please enter the school name: ");
		String name = keyboard.nextLine();
		out.println("Please enter the school nick name: ");
		String nickName = keyboard.nextLine();
		out.println("Please enter the school city: ");
		String city = keyboard.nextLine();
		out.println("Please enter the campus name: ");
		String campus = keyboard.nextLine();
	
		out.println("Updated school: "+" "+name+" "+nickName+" "+city+" "+campus);

		s.setName(name);
		s.setNickName(nickName);
		s.setCity(city);
		s.setCampus(campus);

		schoolDAO.save(s);
		out.println(" ");
		listSchools();
		
	} // end modifySchool()
	
	private void deleteSchool() throws SQLException {
	
		out.println("Please type the database id for the school that you want to delete. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		School s = schoolDAO.get(id);      
		
		if (s == null) {
			out.println("There is no user with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		out.print("Here is the school that you asked to delete:  ");
		out.println(s.getName()+" "+ s.getNickName()+" "+ s.getCity()+" "+ s.getCampus());
		
		out.print("Delete? (y/n) ");
		String text = keyboard.nextLine();
		if (text.equals("y")) {
			schoolDAO.delete(id);
			out.println(s.getName() + " Deleted. ");
		}
	} // end deleteSchool()
	
} // end class SchoolUI
