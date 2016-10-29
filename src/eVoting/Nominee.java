package eVoting;

public class Nominee 
{
	private String name;	//name of the candidate
	private String party;	//political party the candidate is running for
	
	public Nominee(String inName, String politicalAffiliation)
	{
		this.name = inName;
		this.party = politicalAffiliation;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getParty()
	{
		return this.party;
	}
}
