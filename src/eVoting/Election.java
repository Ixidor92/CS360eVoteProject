package eVoting;

import java.util.ArrayList;

public class Election 
{
	private ArrayList<Nominee> nominees;		//a list of available nominees
	private boolean currentlyRunning;			//is the election currently active?
	private int voteTally;						//tally of those who have voted
	private ArrayList<Integer> registeredVoter;	//list of registered voters
	
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
}
