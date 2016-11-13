package eVoting;

public class Voter 
{
	private String name;	//name of the voter
	private String voterID;	//voterID of the voter
	
	//constructor
	//takes a given name, ID, and ssn, then turns it into a voter
	//object
	//parameters:
	//inName: the given name for the voter
	//inID: the voterID for the given voter
	//inSocial: the social security number for the given voter
	public Voter(String inName, String inID)
	{
		this.name = inName;
		this.voterID = inID;
	}
	
	//method castVote
	//gives a vote for the given candidate
	//may be unneeded
	public boolean castVote(String chosenNominee)
	{
		return true;
	}
	
	//gives the name of the voter
	public String getName()
	{
		return this.name;
	}
	
	//gives the voterID of the voter
	public String getID()
	{
		return this.voterID;
	}
	
}
