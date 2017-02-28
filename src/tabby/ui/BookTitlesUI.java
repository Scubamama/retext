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


/**
 * console version of Books located, add a book, add listing, and edit listing
 * hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class BookTitlesUI {
	//Logger log = LogManager.getLogger(ReTextUI.class);
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BookTitlesDAO bookDAO = new BookTitlesDAO();
	
	public void mainMenu() throws SQLException {
		
		Logger log = LogManager.getLogger(BookTitlesUI.class.getName());
		log.debug("Test message!!! ");
		log.info("Test message!!!  mainMenu of BookUI");
		//log.error("Test message!!!", new NullPointerException("foo"));
		log.debug("Program starting mainMenu() in BookUI");
		
		out.println("Welcome to Book Management.");
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
		out.println("0) Quit and return to Main Menu\n");
		out.print("? ");
	}
	

	public boolean callMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // add
			addBook();
			break;
		case 2: // search
			searchBooks();
			break;
		case 3: // list all
			listAllBooks();
			break;
		case 4: // modify
			modifyBook();
			break;
		case 5: // delete
			deleteBook();
			break;
		}
		return true;
	}

	public void addBook()  throws SQLException {
	
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
		BookTitles book = new BookTitles(title,author,edition,isbn);
	
		bookDAO.save(book);
		out.println(" ");
		listAllBooks();
	} // end addUser
	
	public void searchBooks() throws SQLException {
		out.println("Displays books with a particular title. ");
		out.print("Search for books (enter as much of the name as you know): ");
		String query = keyboard.nextLine();
		List<BookTitles> results = bookDAO.searchBooks(query);
		if(results.isEmpty()){
			out.println("No matches.");
		}else {
			displayBooksPaged(results);
		}
	}
	
	public void listAllBooks() throws SQLException {
	
		// pulls users out of the db and puts them in an ArrayList
		List<BookTitles> results = bookDAO.listMyBooks(); 
		displayBooksPaged(results); // prints above ArrayList
		
		out.println(" ");
	}
	
	private void displayBooksPaged(List<BookTitles> results) throws SQLException {	
		
		out.println("Current ReText books: ");
		out.println("db-id  \t title    \t\t author \t edition \t isbn ");
		int count = 0;
		for (BookTitles b : results) {
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
	
	private void modifyBook()  throws SQLException {
		
		out.println("Please type the database id for the book that you want to modify. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		BookTitles b = bookDAO.get(id);    
		if (b == null) {
			out.println("There is no book with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		
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
	
		out.println("Updated book: "+" "+title+" "+author+" "+edition+" "+dept+" "+cNum+" "+isbn);
		
		b.setTitle(title);
		b.setAuthor(author);
		b.setEdition(edition);
		b.setDept(dept);
		b.setCourseNum(cNum);
		b.setIsbn(isbn);
		bookDAO.save(b);
		out.println(" ");
		listAllBooks();
		
	}
	
	private void deleteBook() throws SQLException {
	
		out.println("Please type the database id for the book that you want to delete. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer id = readUserChoice();
		
		BookTitles b = bookDAO.get(id);      
		
		if (b == null) {
			out.println("There is no book with the Id of  " + id + ". Returning to main menu.");
			return;
		}
		out.print("Here is the book that you asked to delete:  ");
		out.println(b.getTitle()+" "+b.getAuthor()+" "+b.getEdition()+" "+b.getIsbn());

		out.print("Delete? (y/n) ");
		String text = keyboard.nextLine();
		if (text.equals("y")) {
			bookDAO.delete(id);
			out.println(b.getTitle() + " Deleted. ");
		}
	}// end delete
	
} // end class BookUI
