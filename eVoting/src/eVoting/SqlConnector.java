package eVoting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlConnector 
{
	/*
	 The purpose of this class is to connect to the voting database.
	 This database will hold entries for the registered voters, nominees available,
	 as well as members of the Electorate committee.
	 */
	
	private final String username;			//username for the database
	private final String password;	//password for the database
	private final String serverName;	//the name of the server the database is located in
	private final int portNumber;			//port for the mysql server
	private final String database;		//name of the database
	
	//Constructor: initialize the connector with the appropriate facts for the database
	public SqlConnector(String user, String login, String server, int port, String DB)
	{
		this.username = user;
		this.password = login;
		this.serverName = server;
		this.portNumber = port;
		this.database = DB;
	}
	
	//connect to the database, and return a result set 
	public ResultSet getData(String query) throws SQLException
	{
		//create a connection to the database given in the constructor
		Connection conn = null;
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", this.username);
		connectionProperties.put("password", this.password);
		
		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.database,
				connectionProperties);
		
		//create a sql statement from the given query and pull a table of results from it
		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet data = stmt.executeQuery(query);
		//return the data found from the given query 
		return data;
	}
	
	public void editData(String update) throws SQLException
	{
		Connection conn = null;
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", this.username);
		connectionProperties.put("password", this.password);
		
		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.database,
				connectionProperties);
		
		//create a sql statement from the given update query, and run it.
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(update);
	}
}
