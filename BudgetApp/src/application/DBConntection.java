package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class DBConntection {

	public static Connection conn = null;
	
	public static Connection DBconnect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String USERNAME = "root";
			String PASSWORD = "WIRuwgb297762!";
			String CONN_STRING = "jdbc:mysql://localhost:3306/budget";
			conn = DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
			return conn;

		} catch (ClassNotFoundException e) {
			
		}
				return null;		
	}
	
}
