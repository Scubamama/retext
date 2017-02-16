package tabby.database;

import static java.lang.System.out;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tabby.database.DatabaseManager;
import tabby.model.AUser;

/**
 * A class to learn about MySql and JDBC
 * Uses prepared statements to access a database
 * 
 * @author Holly Williams
 *
 */

public class UsersDAO {

	public UsersDAO() {
		
	}

	public List<AUser> searchUsers(String text) throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<AUser> userList = new ArrayList<AUser>();
		String sql = "SELECT * FROM Users where UserName LIKE ? ";
		
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
					userList.add(new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") ));
				}
				return userList;
		
			
			// 4. Process the result set
			//listUsers();
		/*	
			while (myRs.next()) {
				System.out.println(myRs.getString("Id") + ", " +myRs.getString("Email") + ", " + myRs.getString("UserName") + ", " + myRs.getString("School"));
			}
		*/	
	
			} //end try
			finally {
				//DbUtils.silentCloseConnection(myConn);
				//DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}
			
		//	DbUtils.close(myConn, myStmt, myRs);
	
		
		} // end searchUsers

	public List<AUser> listMyUsers() throws SQLException {
		DatabaseManager mgr = new DatabaseManager();
		List<AUser> userList = new ArrayList<AUser>();
		String sql = "SELECT * FROM users";
		
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
					userList.add(new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") ));
				}
				return userList;
			} //end try
		//	catch (Exception exc) {
		//		exc.printStackTrace();
				
		//	}
			finally {
			//	DbUtils.silentCloseConnection(myConn);
			//	DbUtils.silentCloseStatement(myStmt);
				mgr.silentClose(myConn, myStmt, myRs);
			//	DbUtils.close(myConn, myStmt, myRs);
			}
			
		//	DbUtils.close(myConn, myStmt, myRs);
	
		
	} // end listMyUsers

	
	public void save(AUser newU) {
		// save a user if one like this does not exist 
		// otherwise update it
		
	//	insert(newU);   // for testing 
	//	update(newU);   // for testing
		
	//	take out of comments after testing
		out.println("in save newU.getId() =  " + newU.getId());
		if(newU.getId() == 0){
			insert(newU);
		}else {
			update(newU);
		}
	
		
	} // end save()
	
	private void update (AUser newU) {
		// this is just going to update the user name
		out.println("UPDATING... ");
		
		String sql = "UPDATE Users SET UserName=? WHERE id=?";
		DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
			
			//	myConn = DbUtils.openConnection(); old
				myConn = mgr.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
			//	myStmt.setString(1,newU.getUserEmail()); //pulls email from object
				myStmt.setString(1,newU.getUserName());
				myStmt.setInt(2,newU.getId());
				
				//	ResultSet myRs = myStmt.executeQuery("SELECT * FROM student");
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
	
	
	private void insert (AUser newU) {
		
		out.println("INSERTING... ");
		
		String sql = "INSERT INTO Users "
				+ "(Email, UserName, UserPassword, TakeCards, school)"
				+ "VALUES (?, ?, ?, ?, ?)";
		
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
				myStmt.setString(1,newU.getUserEmail()); //pulls email from object
				myStmt.setString(2,newU.getUserName());
				myStmt.setString(3,newU.getUserPassword());
				myStmt.setInt(4,newU.getTakeCards());
				myStmt.setString(5,newU.getUserSchool());
				
				//	ResultSet myRs = myStmt.executeQuery("SELECT * FROM student");
				myStmt.executeUpdate();
				//myRs = myStmt.executeQuery("SELECT * FROM student ORDER BY UserName");
				try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						newU.setId(generatedKeys.getInt(1));
					} else {
						throw new SQLException("Insertion failed, no new id created.");
					}
						
				} // end inner try
			// 4. Process the result set
			/*
				while (myRs.next()) {
					System.out.println(myRs.getString("Id") + ", " +myRs.getString("Email") + ", " + myRs.getString("UserName") + ", " + myRs.getString("School"));
				}
			*/
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

	} // end insert()

	
	public AUser get(Integer id) throws SQLException {
		
	String sql = "SELECT * FROM users where id=?";
	
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
				AUser u = new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") );
				return u;
				
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
		
	//	DbUtils.close(myConn, myStmt, myRs);
	//	return null;

	} // end get()
	

	public void delete(Integer id) throws SQLException {
		
	String sql = "DELETE FROM users WHERE id=?";
	
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
			//	ResultSet myRs = myStmt.executeQuery("SELECT * FROM student");
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

	
	} // end delete
	
} // end class UsersDAO
