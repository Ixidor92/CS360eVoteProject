package eVoting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Voter 
{
	private String firstName;	//name of the voter
	private String lastName;
	private int voterID;	//voterID of the voter
	private SqlConnector connection;	//the connection to the sql server
	
	//constructor
	//takes a given name, ID, and ssn, then turns it into a voter
	//object
	//parameters:
	//inName: the given name for the voter
	//inID: the voterID for the given voter
	//registered: the SQLConnector that connects to the table of voters
	public Voter(String first, String last, int inID, SqlConnector registered)
	{
		this.firstName = first;
		this.lastName = last;
		this.voterID = inID;
		this.connection = registered;
	}
	
	//gives the name of the voter
	public String getName()
	{
		return this.firstName + " " + this.lastName;
	}
	
	//gives the voterID of the voter
	public int getID()
	{
		return this.voterID;
	}
	
	//asks the user for their voter ID, and then proceeds with the voting process
	//if there is no user with the ID, an error message appears
	//if the user of that ID has already voted, then the system notes as such
	//otherwise, the system gives a list of candidates, and 
	//returns "true" if the voter cast a ballot, "false" otherwise
	public boolean vote(Scanner input) throws SQLException
	{
		boolean successful = false;		//is the 
		
		//print the available Nominees
		String candQuery = "SELECT * FROM nominees";
		ResultSet candidates = connection.getData(candQuery);
				
		System.out.println("List of candidates:");
		while(candidates.next())
		{//print out the name and id of every candidate
			System.out.print(Integer.toString(candidates.getInt(1)));	//print out the candidate's numerical ID
			System.out.println(" " + candidates.getString(2));
		}
				
		//print out a blank line for clarity, followed by a prompt.
		//current version: asks for the number ID of the candidate
		boolean done = false;		//is the user finished?
				
		while(!done)
		{//continue to ask the user to enter their value until they cancel or have a valid vote
			System.out.println("\n" + "Please enter the numerical ID of the candidate you would like to vote for, enter 0 to cancel:");
			String choice = input.next();
					
			if(choice.equals("0"))
			{//if the user entered 0, then cancel the voting process
				System.out.println("Cancelling ballot...");
				done = true;
			}	
			else
			{
				candQuery = "SELECT * FROM nominees WHERE nominees.ID = " + choice;
				//check if there is a candidate with the given ID, if not, say as such and ask again
				candidates = connection.getData(candQuery);
				
				if(!candidates.first())
				{//if the result list is empty, then there is no candidate with that ID
					System.out.println("The value " + choice + " does not match any candidate.");
				}
				else
				{
					//show the user what they have selected, and ask if they wish to continue
					System.out.println("You have selected " + candidates.getString(2) + ", representing party " + 
						candidates.getString(3) + ".\n" + "Is this your desired choice? 1 for yes, any other entry for no:");
					String confirmation = input.next();
					//if confirmation is Y or y, then move on.  Any other entry will be taken as no.
					if(confirmation.equals("1"))
					{
						System.out.println("Vote tallied");
						//update the table so the voter is marked as having voted
						String voterUpdate = "UPDATE registeredvoters SET hasVoted = 1 WHERE VoterID = " + Integer.toString(this.voterID);
						connection.editData(voterUpdate);
								
						String tallyUpdate = "UPDATE nominees SET votes = (votes + 1) WHERE ID = " + choice;
						connection.editData(tallyUpdate);
								
						done = true;		//the user is finished
						successful = true;	//the vote was successfully tallied
					}
					else
					{
								System.out.println("Returning to selection");
					}
				}
			}
		}
		return successful;
	}
}
