package eVoting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Election 
{	
	private static boolean currentlyRunning;	//is the election running? (allowing for the casting of ballots)
	private static boolean isOver;				//is the election finished? closes the system if true
	
	public static void main(String [] args)
	{
		//create sql connection
		String user = "root";
		String password = "Scampers9/2";
		String server = "localhost";
		int port = 3306;
		String DB = "evote";
		currentlyRunning = false;		//currentlyRunning defaults to false
		isOver = false;					//this will be set to true ONLY when the election is ready to be ended for good
		
		SqlConnector connection = new SqlConnector(user, password, server, port, DB);	//controls all sql work
		ElectorateCommittee committee = new ElectorateCommittee(connection);			//committee that controls committee options
		
		Scanner scanner = new Scanner(System.in);	//scanner used to get input from the user
		String choice;	//used for scanner input
		
		//first, the system will ask for a committee member to login, and start the election properly
		System.out.println("System started, please log in and start the election.");
		
		//this is the main loop.  It will continue UNTIL the boolean isOver is rendered false, at which point the election will be OVER
		while(!isOver)
		{
			if(currentlyRunning)
			{//if the election is running, ask for the user to prompt whether they are a committee member or would like to cast a ballot
				System.out.println("If you are a committee member and would like to login, please enter 0.  If you would like to cast a " +
						"ballot, enter anything else.");
				
				choice = scanner.next();
				
				if(choice.equals("0"))
				{//if the user enters 0, then they are a committee member trying to log in
					try 
					{
						while(!committee.login(scanner));	//have the committee member log in
						while(!committeeOptions(scanner, committee));	//run through the committee options until they are done
					} 
					catch (SQLException e) 
					{
						System.out.println("Faulty SQL command");
						System.out.println(e);
					}
				}
				else
				{//if the user is not a member trying to log in, have them attempt to cast a ballot
					//begin by prompting the user for their voterID
					System.out.println("Please enter your Voter ID");
					
					String id = scanner.next();
					String query = "SELECT * FROM registeredvoters where VoterID = " + id;
					try
					{
						ResultSet currentUser = connection.getData(query);	//pull the user from the table of registered voters, assuming he is there
						if(!currentUser.first())
						{//if there is not entry in the result set, then there was no voter with that ID
							System.out.println("We are sorry, there is no registered voter with that ID in our system." + 
									"Please re-attempt to enter your ID, or ask a nearby official for help.");
						}
						else if(currentUser.getInt(4) == 1)
						{//make sure that the current user has not already voted
							System.out.println("We are sorry, but it appears you have already voted in this election. Thank you for your participation!");
						}
						else
						{//if there is a result, then there is a voter.
							//create a voter object, and call the vote method
							String first = currentUser.getString(2);
							String last = currentUser.getString(3);
							int userID = currentUser.getInt(1);
							Voter registeredUser = new Voter(first, last, userID, connection);
							registeredUser.vote(scanner);
						}
					}
					catch(SQLException e)
					{
						System.out.println("The entry: " + id + ", is not a valid ID.  Please ensure you enter only numbers when typing the ID.");
					}
				}
			}
			else
			{//if the election is not running, then action can only be taken if a committee member logs in
				System.out.println("Please enter your login for Electorate committee commands.");
				try
				{
					while(!committee.login(scanner));						//have the committee member login
					while(!committeeOptions(scanner, committee));	//run through the committee options until they are done
				}
				catch(SQLException e)
				{
					System.out.println("Faulty SQL command");
					System.out.println(e);
				}
			}
		}
		System.out.println("Closing the system");
		scanner.close();	//close the scanner to prevent data leaks
	}
	
	//prints out the menu options available for a committee member after they have logged in and will run the methods needed
	//will be looped in the main method
	//Return: The method will return false unless the committee member is finished, which will indicate the system may move on
	//to its next state
	private static boolean committeeOptions(Scanner input, ElectorateCommittee electorate)
	{
		boolean finished = false;	//notes whether the user is finished or not
		
		//print out the prompts for the committee member
		System.out.println(); 		//for readability
		System.out.println("Please select one of the following options:");
		System.out.println("1: Start election");
		System.out.println("2: Halt election");
		System.out.println("3: view current standings");
		System.out.println("4: leave this menu and let another user interact with the system.");
		System.out.println("5: End election WARNING: this will stop the election for good and finalize results.");
		
		System.out.println("Testing: about to take input...");
		String selection = input.next();	//take the selection from the user
		
		//do the appropriate action based on the user's input
		if(selection.equals("1"))
		{
			currentlyRunning = true;	//election is now allowing ballots to be cast
			System.out.println("Election has now started!");
		}
		else if(selection.equals("2"))
		{
			currentlyRunning = false;	//election is halted, no ballots may be cast
			System.out.println("Election Halted.");
		}
		else if(selection.equals("3"))
		{
			//if the election is not currently running, results can be viewed
			if(!currentlyRunning)
			{
				try 
				{
					electorate.viewResults();
				} 
				catch (SQLException e) 
				{//print out a simple error message if there is a sql error.  There should be no chance of this, assuming proper database integration
					System.out.println("Faulty SQL command");
					System.out.println(e);
				}
			}
			else
			{//if the election is running, then let the user know results cannot be seen until halted
				System.out.println("Election is currently running, results cannot be viewed.");
				System.out.println("If you wish to view the election results, please halt the election and retry.");
			}
		}
		else if(selection.equals("4"))
		{
			//This command leaves the menu.  If the election is currently halted, the user will be given a warning that voters cannot
			//use the system and ask for confirmation.
			if(!currentlyRunning)
			{
				System.out.println("WARNING: The election is currently halted, and voters will not be able to cast ballots.  " +
						"Do you wish to continue? y/n");
				String yesNo = input.next();
				if(yesNo.equals("y"))
				{//if they have confirmed, then continue, noting that the election is currently halted
					System.out.println("Returning to main menu...");
					System.out.println("Election is currently halted.");
					finished = true;
				}
				else
				{
					System.out.println("Returning to command menu...");
				}
			}
			else
			{//if the election is not halted, the user is taken to the previous menu with no issues
				System.out.println("Returning to main menu...");
				System.out.println("Election is running, voters may cast ballots.");
				finished = true;
			}
		}
		else if(selection.equals("5"))
		{
			//This option ends the election for good and gives the results
			//this option can only be selected if the election is already halted
			if(currentlyRunning)
			{
				System.out.println("The election is currently running, and cannot be ended.  Please halt the election first.");
			}
			//the user will be prompted to be sure this is what they want, assuming the election is halted
			else
			{
				System.out.println("You have selected to end the election.  WARNING: the election will be stopped after this, and " +
						"additional ballots may not be cast once it is finished.  Do you wish to continue? y/n");
				String yesNo = input.next();
				
				if(yesNo.equals("y"))
				{
					System.out.println("Ending election...");
					isOver = true;		//election is over now
					finished = true;	//user is done with the system
				}
				else
				{//default to the election NOT ending
					System.out.println("Returning to command menu...");
				}
			}
			
		}
		else
		{
			System.out.println("The entry " + selection + " is not a valid entry.  Please try again.");
		}
		return finished;
	}

}
		