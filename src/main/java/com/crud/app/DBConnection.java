package com.crud.app;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private Connection connection;
	private static DBConnection dbConnection;
	
	private DBConnection() {
		
	}

	public static void main(String[] args) throws Exception{
        DBConnection obj_DB_connection=new DBConnection();
        System.out.println(obj_DB_connection.getH2Connection());
    }
	
	public static synchronized DBConnection getInstance() {
		if (dbConnection == null)
            dbConnection = new DBConnection();
  
        return dbConnection;
	}
	
	public Connection getConnection() {
		if(connection == null) {
			connection = getH2Connection();
		}
		return connection;
		
	}
	
    private Connection getMSConnection() throws Exception{
        Connection connection=null;
        try {
        	Class.forName("com.mysql.jdbc.Driver");        	
        	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","root");

        } catch (Exception e) {
            System.err.println(e);
        }
        return connection;
    }
    
    private Connection getH2Connection() {
    	Connection conn = null;
    	try {
    		String jdbcURL = "jdbc:h2:file:~/sampleDB;INIT=RUNSCRIPT FROM '~/create.sql'\\;RUNSCRIPT FROM '~/populate.sql'";
            String username = "sa";
            String password = "1234";
     
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to H2 embedded database.");
    		
		} catch (Exception e) {
			System.err.println(e);
		}
    	return conn;
    }
}
