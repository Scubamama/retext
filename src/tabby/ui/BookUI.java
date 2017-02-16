package tabby.ui;

import static java.lang.System.out;

//import java.awt.List;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tabby.model.AUser;
import tabby.database.BooksDAO;
import tabby.database.UsersDAO;
import tabby.model.Book;


/**
 hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class BookUI {
	//Logger log = LogManager.getLogger(ReTextUI.class);
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BooksDAO bookDAO = new BooksDAO();
	
	public void mainMenu() throws SQLException {
		
		Logger log = LogManager.getLogger(BookUI.class.getName());
		log.debug("Test message!!!");
		log.info("Test message!!!");
		log.error("Test message!!!", new NullPointerException("foo"));
		log.debug("Program starting mainMenu()");
		
		//out.println("Welcome to Retext.");
		boolean keepRunning = true;
		while(keepRunning)
		{
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thank you for managing books. ");
	}
	
	public int readUserChoice() {
		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public void printMainMenu() {
		out.println("1) Add a book");
		out.println("2) Search books");
		out.println("3) List all books");
		out.println("4) Modify a book");
		out.println("5) Delete a book");
		out.println("0) Quit\n");
		out.print("? ");
	}
	

	public boolean callMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // add
			//addUser();
			addBook();
			break;
		case 2: // search
			searchUsers();
			break;
		case 3: // list all
			listAllBooks();
			break;
		case 4: // modify
		//	UsersDAO.updateUser();
			modifyUser();
			break;
		case 5: // delete
			deleteTheUser();
			break;

		}
		return true;
	}

	public void addBook()  throws SQLException {
	//	int card = 0;  // default user does not take cards
		out.println("Please enter the title: ");
		String title = keyboard.nextLine();
		out.println("Please enter the author name: ");
		String author = keyboard.nextLine();
		out.println("Please enter the edition: ");
		String edition = keyboard.nextLine();
		out.println("Please enter the department name: ");
		String dept = keyboard.nextLine();
		out.println("Please enter the course number: ");
		String cNum = keyboard.nextLine();
		out.println("Please enter the Isbn: ");
		String isbn = keyboard.nextLine();
	
		out.println("New book: "+" "+title+" "+author+" "+edition+" "+dept+" "+cNum+" "+isbn);
		Book book = new Book(title,author,edition,isbn);
	
		bookDAO.save(book);
		out.println(" ");
		listAllBooks();
	} // end addUser
	
	public void searchUsers() throws SQLException {
		out.println("Displays users with a particular username. ");
		out.print("Search for users (enter as much of the name as you know): ");
		String query = keyboard.nextLine();
		List<Book> results = bookDAO.searchBooks(query);
		if(results.isEmpty()){
			out.println("No matches.");
		}else {
			displayBooksPaged(results);
		}
	}
	
	private void listAllBooks() throws SQLException {
	
		// pulls users out of the db and puts them in an ArrayList
		List<Book> results = bookDAO.listMyBooks(); 
		displayBooksPaged(results); // prints above ArrayList
		
		out.println(" ");
	}
	
	private void displayBooksPaged(List<Book> results) throws SQLException {	
		
		out.println("Current ReText books: ");
		out.println("db-id  \t title    \t\t author \t edition \t isbn ");
		int count = 0;
		for (Book b : results) {
			out.println(b.getId() + "\t " + b.getTitle()  + "\t " + b.getAuthor()  + "\t " + b.getEdition() + "\t " + b.getIsbn());
			count++;
			if (count % 20 == 0) { //pause every 20 lines
				out.println("Press enter for more or q to quit  > ");
				String key = keyboard.nextLine();
				if (key.equals("q")) {
					break;
				}
			}
		} //end for
		
	} // end displayBooksPaged()
	
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
	}
	
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
	}
	
} // end class ReTextUI
