package tabby.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Loads the database connection information from a separate properties file, 
 * so it's not in the source code.  You can then add db.properties to your .gitignore
 * so it's not uploaded with your code (although you'll have to recreate it in the future
 * if you lose your copy). The db.properties file should be in your src/ folder in Eclipse and
 * not in a sub-package.
 * 
 * 
 * @author jenny
 *
 */
public class DatabaseManager {
	
	ResourceBundle file = null;
	String url = null;
	String driver = null;
	String username = null;
	String password = null;
	String schema = null;
	
	public DatabaseManager() {
		file = PropertyResourceBundle.getBundle("db"); // implies: db.properties in the classpath

		url = file.getString("url");
		driver = file.getString("driver");
		username = file.getString("username");
		password = file.getString("password");
		schema = file.getString("schema");

		try {
			// loads the driver automatically
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url + schema, username, password);	
	}

	public void silentClose(Connection conn) {
		silentClose(conn, null);
	}

	public void silentClose(Connection conn, Statement stmt) {
		if (stmt != null) { 
			try { 
				stmt.close();
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	

	public void silentClose(Connection conn, Statement stmt, ResultSet res) {
		if (stmt != null) { 
			try { 
				stmt.close();
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		if (res != null) {
			try {
				res.close();
			} catch (Exception e) {
			}
		}
	}
	
 /*	
	public static void main(String[] args) {
		DatabaseManager mgr = new DatabaseManager();
		Connection conn = null;
		
		try {
			conn = mgr.getConnection();
			System.out.println("Succeeded.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mgr.silentClose(conn);
		}
		
	}
  */
}
