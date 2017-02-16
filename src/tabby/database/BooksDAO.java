package tabby.database;

import static java.lang.System.out;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tabby.database.DatabaseManager;
import tabby.model.AUser;
import tabby.model.Book;

/**
 * A class to learn about MySql and JDBC
 * Uses prepared statements to access a database
 * 
 * @author Holly Williams
 *
 */

public class BooksDAO {

	public BooksDAO() {
		
	}

	public List<Book> searchBooks(String text) throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<Book> bookList = new ArrayList<Book>();
		String sql = "SELECT * FROM Books where Title LIKE ? ";
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				//myConn = DbUtils.openConnection(); old
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setString(1, "%" + text + "%");
				myRs = myStmt.executeQuery();

				// 4. Process the result set - put it into the ArrayList
				
				while (myRs.next()) {
					bookList.add(new Book(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") ));
				}
				return bookList;
	
			} //end try
			finally {
				//DbUtils.silentCloseConnection(myConn);
				//DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}
			
		} // end searchUsers

	public List<Book> listMyBooks() throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<Book> bookList = new ArrayList<Book>();
		String sql = "SELECT * FROM books";
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
	
				//myConn = DbUtils.openConnection(); old
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				
				//	ResultSet myRs = myStmt.executeQuery("SELECT * FROM student");
				myRs = myStmt.executeQuery();
				//myRs = myStmt.executeQuery("SELECT * FROM student ORDER BY UserName");
				
			// 4. Process the result set - put it into the ArrayList
			
				while (myRs.next()) {
				//	bookList.add(new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") ));
					bookList.add(new Book(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") ));
				}
				return bookList;
			} //end try
			finally {
			//	DbUtils.silentCloseConnection(myConn);
			//	DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}

	} // end listMyBooks

	
	public void save(Book book) {
		// save a user if one like this does not exist 
		// otherwise update it
		
	//	insert(newU);   // for testing 
	//	update(newU);   // for testing
		
		out.println("in save book.getId() =  " + book.getId());
		if(book.getId() == 0){
			insert(book);
		}else {
			update(book);
		}
	
	} // end save()
	
	private void update (Book book) {
		// this is just going to update the book title
		out.println("UPDATING... ");
		
		String sql = "UPDATE Books SET Title=?, Author=?, Edition=?,Isbn=? WHERE id=?";
	
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
			
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
			
				myStmt.setString(1,book.getTitle());
				myStmt.setString(2,book.getAuthor());
				myStmt.setString(3,book.getEdition());
				myStmt.setString(4,book.getIsbn());
				myStmt.setInt(5,book.getId());
				
				myStmt.executeUpdate();
			} //end try
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
			//	DbUtils.silentCloseConnection(myConn);
			//	DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}
			
		//	DbUtils.close(myConn, myStmt, myRs);
		
	} // end update()
	
	
	private void insert (Book book) {
		
		out.println("INSERTING... ");
		
		String sql = "INSERT INTO Books "
				+ "(Title, Author, Edition, Isbn)"   //CourseDept and CourseNumber later
				+ "VALUES (?, ?, ?, ?)";
		
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
			//	myConn = DbUtils.openConnection(); 
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				myStmt.setString(1,book.getTitle()); //pulls email from object
				myStmt.setString(2,book.getAuthor());
				myStmt.setString(3,book.getEdition());
				myStmt.setString(4,book.getIsbn());
				
				myStmt.executeUpdate();
				//myRs = myStmt.executeQuery("SELECT * FROM student ORDER BY UserName");
				try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						book.setId(generatedKeys.getInt(1));
					} else {
						throw new SQLException("Insertion failed, no new id created.");
					}
						
				} // end inner try
			
			} //end try
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
			//	DbUtils.silentCloseConnection(myConn);
			//	DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}

	} // end insert()

	
	public Book get(Integer id) throws SQLException {
		
	String sql = "SELECT * FROM books where id=?";
	
	DatabaseManager mgr = new DatabaseManager();
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
	Connection myConn = null;
	
	try {
		// 1. Get a connection to the database
		//	myConn = DbUtils.openConnection(); 
			myConn = mgr.getConnection();
		// 2. Create a statement object
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1,id);
			myRs = myStmt.executeQuery();
			//myRs = myStmt.executeQuery("SELECT * FROM student ORDER BY UserName");
			if (myRs.next()) {
			//	AUser u = new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") );
				Book b = new Book(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") );
				return b;
				
			} else {
				return null;
			}

		} //end try
	/*
		catch (Exception exc) {
			exc.printStackTrace();
		}
		*/
		finally {
		//	DbUtils.silentCloseConnection(myConn);
		//	DbUtils.silentCloseStatement(myStmt);
			mgr.silentClose(myConn, myStmt, myRs);
		//	DbUtils.close(myConn, myStmt, myRs);
		}
		
	} // end get()
	

	public void delete(Integer id) throws SQLException {
		
	String sql = "DELETE FROM books WHERE id=?";
	
	DatabaseManager mgr = new DatabaseManager();
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
	Connection myConn = null;
	
	try {
		// 1. Get a connection to the database
		//	myConn = DbUtils.openConnection(); 
			myConn = mgr.getConnection();
		// 2. Create a statement object
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
			myStmt.executeUpdate();

		} //end try
		catch (Exception exc) {
			exc.printStackTrace();
			
		}
		finally {
		//	DbUtils.silentCloseConnection(myConn);
		//	DbUtils.silentCloseStatement(myStmt);
			mgr.silentClose(myConn, myStmt, myRs);
		//	DbUtils.close(myConn, myStmt, myRs);
		}
	
	} // end delete
	
} // end class UsersDAO
