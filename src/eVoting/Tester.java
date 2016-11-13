package eVoting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tester 
{
	public static void main(String args[])
	{
		/*
		String name = "Sanz";
		String voterID = "W4rr10r";
		
		Voter testVoter = new Voter(name,voterID);
		System.out.println(testVoter.getName());
		System.out.println(testVoter.getID());
		*/
		
		String userName = "root";
		String password = "Scampers9/2";
		int port = 3306;
		String server = "localhost";
		String DB = "evote";
		
		SqlConnector test = new SqlConnector(userName, password, server, port, DB);
		try
		{
			ResultSet voters = test.getData("SELECT * FROM registeredvoters;");
			while(voters.next())
			{
				System.out.println("Voter: " + voters.getString(2) + " " + voters.getString(3));
			}
		}
		catch(SQLException e)
		{
			System.out.println("faulty SQL Command");
		}
	}
}
