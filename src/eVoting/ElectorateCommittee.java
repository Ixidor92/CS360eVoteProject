package eVoting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ElectorateCommittee 
{
	private SqlConnector connection;
	
	public ElectorateCommittee (SqlConnector connector)
	{
		this.connection = connector;
	}
	
	public void viewMember (int memberID) throws SQLException
	{
		ResultSet member = connection.getData("SELECT * FROM members WHERE id = " + Integer.toString(memberID));
		if (!member.first())
		{
			System.out.println("The is no such user identification");
		}
		else
		{
			System.out.println(member.getString("username"));
			System.out.println(member.getString("password"));
		}
	}
	
	public void viewNomineeVotes (int nomineeID) throws SQLException
	{
		ResultSet nominee = connection.getData("SELECT * FROM nominees WHERE ID = " + Integer.toString(nomineeID));
		if (!nominee.first())
		{
			System.out.println("There is no such nominee identification");
		}
		else
		{
			System.out.println(nominee.getString("Name"));
			System.out.println(nominee.getString("Party"));
			System.out.println(nominee.getString("votes"));
		}
	}
	
	public boolean login() throws SQLException
	{
		boolean successful = false;
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your username: ");	//query the user for their username
		String username = input.nextLine();
		
		
		String query = "SELECT * FROM members WHERE username = " + username;	//look for members with the given username 
		ResultSet member = connection.getData(query);
		if(!member.first())
		{
			System.out.println("No member found with the given username");
		}
		else
		{
			//check for the user AND password
			boolean found = false;
			int count = 0;
			while(!found && count < 3)
			{
				System.out.println("Please enter your password: ");	//query the user for their password
				String pass = input.nextLine();
				
				query = "SELECT * FROM members WHERE username = " + username + " AND password = " + pass;
				member = connection.getData(query);
				if(!member.first())
				{
					System.out.println("The password that you entered was incorrect.");
					count++;
				}
				else
				{
					System.out.println("Logging in...");
					found = true;
					successful = true;
				}
			}
			if(count == 3)
			{
				System.out.println("You have failed to enter your login information correctly.  Please reattempt.");
			}
			
		}
		input.close();
		return successful;
	}
	
	
}