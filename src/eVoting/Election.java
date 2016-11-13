package eVoting;

public class Election 
{
	private boolean currentlyRunning;			//is the election currently active?
	private int voteTally;						//tally of those who have voted
	
	//starts the election
	//this will cue the GUI to change and allow voters to send in their ballots
	public void startElection()
	{
		currentlyRunning = true;
	}
	
	//stops the election
	//this will cue the GUI to change an only allow options to a logged in committee member
	public void haltElection()
	{
		currentlyRunning = false;
	}
	
	
	//this returns the current number of votes for each Nominee
	//results should not be available if the election is currenlty running
	public String viewResults()
	{
		//only return results if the election is not running
		if(!currentlyRunning)
		{
			return Integer.toString(voteTally);		//placeholder that gives how many have voted.  Not final
		}
		else
		{
			return "Cannot give results, election currenlty active";
		}
	}
	
	//shows all available nominees that can be voted on
	public void viewNominees()
	{
		System.out.println("not finished");		//filler text
	}
	
	//vote method, allows a user to cast a ballot
	//this method will ask for user info, create a voter object,
	//then check that voter object against the list of registered voters
	//if a match is found, the voter may then cast a vote for the candidate of their choice
	//otherwise, they are asked to re-enter their information
	public void vote()
	{
		System.out.println("cast a ballot");	//placeholder
	}
	
	
}
