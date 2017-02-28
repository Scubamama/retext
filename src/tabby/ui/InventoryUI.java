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
import tabby.ui.BookTitlesUI;
import tabby.database.UserInventoryDAO;
import tabby.model.UserInventory;
import tabby.model.DisplayUserInventory;

/**
 * console version of Inventory
 * hopeful precursor to the retext app
 * @author Holly Williams
 *
 */

public class InventoryUI {
	//Logger log = LogManager.getLogger(ReTextUI.class);
	
	Scanner keyboard = new Scanner(System.in);
	UsersDAO aUserDAO = new UsersDAO();
	BookTitlesDAO bookDAO = new BookTitlesDAO();
	BookTitlesUI bookTitleUi = new BookTitlesUI();
	UserInventoryDAO inventoryDAO = new UserInventoryDAO();
	//UserInventory inv = new UserInventory();
	BookTitles bookTitle = new BookTitles();
//	DisplayUserInventory display = new DisplayUserInventory();
	
	public void mainMenu() throws SQLException {
		
		Logger log = LogManager.getLogger(InventoryUI.class.getName());
		log.debug("Test message!!! ");
		log.info("Test message!!!  mainMenu of InventoryUI");
		//log.error("Test message!!!", new NullPointerException("foo"));
		log.debug("Program starting mainMenu() in Inventory"
				+ "UI");
		
		out.println("\nWelcome to Book Inventory Management.");
		out.println("Please sign in. Or create a user account.");
		boolean keepRunning = true;
		while(keepRunning)
		{
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thank you for managing your book inventory. ");
	}
	
	public int readUserChoice() {
		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public void printMainMenu() {
		out.println("1) Add a book to my book inventory");
		out.println("2) Search my books");
		out.println("3) List my books");
	//	out.println("4) Modify a book");
		out.println("4) Delete a book");
		out.println("0) Quit and return to Main Menu\n");
		out.print("? ");
	}
	

	public boolean callMenuItem(int choice) throws SQLException {
		int currUser = 1;      // take out when login is working
		switch (choice) {
		case 0: // quit
			return false;
		case 1: // add
			//addBook();
			addBookToInventory(currUser);
			break;
		case 2: // search
			searchMyBooks(currUser);
			break;
		case 3: // list 
			listMyBooks(currUser);
			break;
	//	case 4: // modify not needed just add or delete in inventory
	//		modifyBook();
	//		break;
		case 4: // delete
			deleteBook();
			break;
		}
		return true;
	}

	public void addBookToInventory(int currUserId)  throws SQLException {
	
		double price = 0.0;
		int sold = 0;
		
		int currBookId = 0;  

		// have the current user login if necessary
	//	UserUI userUi = new UserUI();  // ? might be able to use the one created in ReTextUI
	//	userUi.login(); 
		
			// create a new user id if need be
		// ask for book info
		out.println("Please enter the title: ");
		String title = keyboard.nextLine();
		out.println("Please enter the author name: ");
		String author = keyboard.nextLine();
		out.println("Please enter the edition: ");
		String edition = keyboard.nextLine();
		//keyboard.skip("\n"); //use this after each number before a string
		//out.println("New book: "+" "+title+" "+author+" "+edition);
		 // dept and course num later
	//	out.println("Please enter the department name: ");     
	//	String dept = keyboard.nextLine();
	//	out.println("Please enter the course number: ");
	//	String cNum = keyboard.nextLine();
				
		// look for book title in db and get its id to put in inv table
	//	BookTitles b = bookDAO.get(title);
		bookTitle = bookDAO.get(title);
		
		// if book title not in book db table create a new book title row
		
		if (bookTitle == null) {
			out.println("Book not found. Adding to book titles. " );
			out.println("Please enter the Isbn: ");
			String isbn = keyboard.nextLine();
			BookTitles bookTitle = new BookTitles(title,author,edition,isbn);
			
			bookDAO.save(bookTitle);   // puts a new title in db 
			currBookId = bookTitle.getId();
			out.println("added new book to titles table after get title = " + bookTitle.getTitle());
		}
		else {
			currBookId = bookTitle.getId();
			out.println("after get title = " + bookTitle.getTitle());
			out.println("Book found. " );	
		}
		
		// ask user for price
		out.println("Please enter the price: ");
		String input = keyboard.nextLine().trim();
		price = Double.parseDouble(input);
		
		// construct new user inventory object with user id, book id, & price
		UserInventory inv = new UserInventory(currUserId, currBookId, price, sold);
		
		// add new row to user inventory with current user's id and current title's id
		inventoryDAO.save(inv);
		//out.println(bookTitle.getTitle()  + " Added. ");

	//	listAllBooks();
	//	bookTitleUi.listAllBooks(); // needs to be listMyBooks later
		
		listMyBooks(currUserId); // needs to be listMyBooks later
		
	} // end addbook
	
	public void searchMyBooks(int currUserId) throws SQLException {
		out.println("Displays books with a particular title. ");
		out.print("Search for books (enter as much of the name as you know): ");
		String query = keyboard.nextLine();
		//List<BookTitles> results = bookDAO.searchBooks(query);
		List<DisplayUserInventory> results = inventoryDAO.searchMyBooks(query); 
		if(results.isEmpty()){
			out.println("No matches.");
		}else {
			displayInvPaged(results);
		}
	}
	
	private void listMyBooks(int currUserId) throws SQLException {
	
		// pulls users out of the db and puts them in an ArrayList
		List<DisplayUserInventory> results = inventoryDAO.listMyBooks(); 
	//	int listSize = results.size();
	//	out.println("result set size in listMyBooks " + listSize);
		displayInvPaged(results); // prints above ArrayList
		
		out.println(" ");
	}
	
	private void displayInvPaged(List<DisplayUserInventory> results) throws SQLException {	
		
		out.println("Your Current books: ");
		out.println("id \t title    \t\t author \t edition \t isbn \t price");
		int count = 0;
		for (DisplayUserInventory i : results) {
			 out.println(i.getId()  + "\t " + i.getTitle()  + "\t " + i.getAuthor()  + "\t " + i.getEdition() + "\t " + i.getIsbn()+ "\t " + i.getPrice());
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
		
		//Book book = new Book(title,author,edition,isbn);
		b.setTitle(title);
		b.setAuthor(author);
		b.setEdition(edition);
		b.setDept(dept);
		b.setCourseNum(cNum);
		b.setIsbn(isbn);
		bookDAO.save(b);
		out.println(" ");
		//listMyBooks();
		
	} // end modifyBook
	
	private void deleteBook() throws SQLException {
	
		Integer currUserId = 1; // take out when login is working
		// ask for id num of book in curr user's inv that they want to delete
		out.println("Please type the database id for the book that you want to delete. ");
		out.println("You can find this from the list or search options. ");
		out.println("Id?  ");
		Integer bookId = readUserChoice();
		
		//get the inv info in an obj
		UserInventory inv = new UserInventory();
		//inv = userInvDAO.get(bookId);
		inv = inventoryDAO.get(bookId);   // gets the UserInventory obj (& info) for this id
		
		if (inv == null) {
		out.println("You have no book with the Id of  " + bookId + ". Returning to Book Inventory Management menu.");
			return;
		}
		// get the title id and name for this copy
		int bookTitleID = inv.getBookId();	//looking at inv row, get that book title id

		BookTitles bT = new BookTitles();
		
		// pull info for this title from title table
	//	bT = bookDAO.get(bookId);    // returns a bookTitles obj  
		bT = bookDAO.get(bookTitleID);    // returns a bookTitles obj  
		if (bT == null) {
		out.println("There is no book with the Id of  " + bookId + ". Returning to Book Inventory Management menu.");
			return;
		}
	
		//make sure that this is the title that they want to delete
		// if yes delete the inv row
		
		out.print("Here is the book in your inventory that you asked to delete:  ");
		out.println( inv.getId()+" "+bT.getTitle()+" "+bT.getAuthor()+" "+bT.getEdition()+" "+bT.getIsbn());
		out.println("currUserId = "+currUserId+"bookId = "+bookId+"bookTitleId = "+bookTitleID);
		out.print("Delete? (y/n) ");
		String text = keyboard.nextLine();
		if (text.equals("y")) {
		//	bookDAO.delete(id);
			inventoryDAO.delete(currUserId,bookTitleID);
			out.println("Your copy of " +bT.getTitle() + " Deleted. ");
		}
	}
	
} // end class UserInventoryUI
