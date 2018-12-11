package application;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class DBConntection {

	public static Connection conn = null;
	
	public static Connection DBconnect() throws SQLException, FileNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String USERNAME = "root";
			String PASSWORD = "WIRuwgb297762!";
			String CONN_STRING = "jdbc:mysql://localhost:3306/budget";
			conn = DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
			return conn;

		} catch (ClassNotFoundException e) {
		    	   PrintStream pst = new PrintStream("C:\\Users\\wirth\\git\\repository\\BudgetApp\\ErrorLog.txt");  
		    	   System.setOut(pst);
		    	   System.setErr(pst);
		    	   System.out.println("Error Occured.");
		        
		}
				return null;		
	}
	
}
