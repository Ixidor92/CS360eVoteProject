package eVoting;

public class Voter 
{
	private String name;	//name of the voter
	private int voterID;	//voterID of the voter
	private int ssn;		//social security number of the voter
	private String affiliation;	//political affiliation of the voter
	
	//constructor
	//takes a given name, ID, and ssn, then turns it into a voter
	//object
	//parameters:
	//inName: the given name for the voter
	//inID: the voterID for the given voter
	//inSocial: the social security number for the given voter
	public Voter(String inName, int inID, int inSocial, String politics)
	{
		this.name = inName;
		this.voterID = inID;
		this.ssn = inSocial;
		this.affiliation = politics;
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
	public int getID()
	{
		return this.voterID;
	}
	
	//gives the social security number of the voter
	public int getSsn()
	{
		return this.ssn;
	}
	
	public String getAffiliation()
	{
		return this.affiliation;
	}
}
