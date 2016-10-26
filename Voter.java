package eVoting;

public class Voter 
{
	private String name;	//name of the voter
	private int voterID;	//voterID of the voter
	private int ssn;		//social security number of the voter
	
	//constructor
	//takes a given name, ID, and ssn, then turns it into a voter
	//object
	//parameters:
	//inName: the given name for the voter
	//inID: the voterID for the given voter
	//inSocial: the social security number for the given voter
	public Voter(String inName, int inID, int inSocial)
	{
		this.name = inName;
		this.voterID = inID;
		this.ssn = inSocial;
	}
	
	//method castVote
	//gives a vote for the given candidate
	public boolean castVote(String chosenNominee)
	{
		return true;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getID()
	{
		return this.voterID;
	}
	
	public int getSsn()
	{
		return this.ssn;
	}
}
