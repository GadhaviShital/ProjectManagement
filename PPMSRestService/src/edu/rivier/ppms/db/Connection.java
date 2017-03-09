package edu.rivier.ppms.db;

import java.sql.DriverManager;
import java.util.Properties;

public class Connection {
	private static java.sql.Connection conn;
	
	private Connection()  {
		try{
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/postgres";
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "postgres");
			
			conn = DriverManager.getConnection(url, props);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static java.sql.Connection getConnection(){
		if(conn!=null) {
			return conn;
		}
		else {
			new Connection();
			return conn;
		}  
		
	}
	
} 

