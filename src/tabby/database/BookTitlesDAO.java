package tabby.database;

import static java.lang.System.out;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tabby.database.DatabaseManager;
import tabby.model.BookTitles;

/**
 * A class to learn about MySql and JDBC
 * Uses prepared statements to access a database
 * Adds, lists, searches for, and deletes book titles
 * 
 * @author Holly Williams
 *
 */

public class BookTitlesDAO {

	public BookTitlesDAO() {
		
	}

	public List<BookTitles> searchBooks(String text) throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<BookTitles> bookList = new ArrayList<BookTitles>();
		String sql = "SELECT * FROM Book_Titles where Title LIKE ? ";
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setString(1, "%" + text + "%");
				myRs = myStmt.executeQuery();

				// 3. Process the result set - put it into the ArrayList
				
				while (myRs.next()) {
					bookList.add(new BookTitles(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") ));
				}
				return bookList;
	
			} //end try
			finally {
				mgr.silentClose(myConn, myStmt, myRs);
			}
			
		} // end searchBooks

	public List<BookTitles> listMyBooks() throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<BookTitles> bookList = new ArrayList<BookTitles>();
		String sql = "SELECT * FROM Book_Titles";
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database

				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myRs = myStmt.executeQuery();
				
			// 3. Process the result set - put it into the ArrayList
			
				while (myRs.next()) {
					bookList.add(new BookTitles(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") ));
				}
				return bookList;
			} //end try
			finally {
				mgr.silentClose(myConn, myStmt, myRs);
			}

	} // end listMyBooks

	
	public void save(BookTitles book) {
		// save a user if one like this does not exist 
		// otherwise update it
		
		if(book.getId() == 0){
			out.println("INSERTING... ");
			insert(book);
		}else {
			out.println("UPDATING... ");
			update(book);
		}
	
	} // end save()
	
	private void update (BookTitles book) {
		out.println("UPDATING New Book Title... ");
		
		String sql = "UPDATE Book_titles SET Title=?, Author=?, Edition=?,Isbn=? WHERE id=?";
	
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
				mgr.silentClose(myConn, myStmt, myRs);
			}
		
	} // end update()
	
	
	private void insert (BookTitles book) {
		
		out.println("INSERTING New Book Title... ");
		
		String sql = "INSERT INTO Book_Titles "
				+ "(Title, Author, Edition, Isbn)"   //CourseDept and CourseNumber later
				+ "VALUES (?, ?, ?, ?)";
		
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				myStmt.setString(1,book.getTitle()); //pulls email from object
				myStmt.setString(2,book.getAuthor());
				myStmt.setString(3,book.getEdition());
				myStmt.setString(4,book.getIsbn());
				
				myStmt.executeUpdate();
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
				mgr.silentClose(myConn, myStmt, myRs);
			}

	} // end insert()

	
	public BookTitles get(Integer id) throws SQLException {
			
		String sql = "SELECT * FROM Book_Titles where id=?";
		
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1,id);
				myRs = myStmt.executeQuery();
				
				if (myRs.next()) {
					BookTitles b = new BookTitles(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") );
					return b;
					
				} else {
					return null;
				}
	
			} //end try

			finally {
				mgr.silentClose(myConn, myStmt, myRs);
			}
			
	} // end get(id)
	
	
	public BookTitles get(String title) throws SQLException {
			
		String sql = "SELECT * FROM Book_Titles where Title=?";
		
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setString(1,title);
				myRs = myStmt.executeQuery();
				
				if (myRs.next()) {
					BookTitles b = new BookTitles(myRs.getInt("Id"), myRs.getString("Title"), myRs.getString("Author"), myRs.getString("Edition"), myRs.getString("Isbn") );
					return b;
					
				} else {
					return null;
				}
	
			} //end try

			finally {
				mgr.silentClose(myConn, myStmt, myRs);
			}
			
	} // end get(title)

	public void delete(Integer id) throws SQLException {
		
		String sql = "DELETE FROM Book_Titles WHERE id=?";
		
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
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
				mgr.silentClose(myConn, myStmt, myRs);
			}
		
	} // end delete
	
} // end class BooksDAO
