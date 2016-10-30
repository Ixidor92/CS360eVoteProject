package eVoting;

public class Member 
{
	private String name;
	private int IDNumber;
	private String login;
	
	//creates a new member with the given name, member ID, and password
	public Member(String introName, int ID, String password)
	{
		this.name = introName;
		this.IDNumber = ID;
		this.login = password;
	}
	
	//returns true if the password given matches the member's password
	public boolean checkPassword(String password)
	{
		return (login.equals(password));
	}
	
	//takes the given string and uses it to change the member's password
	public void changePassword(String password)
	{
		this.login = password;
	}
	
	//gives the name of this committee member 
	public String getName()
	{
		return name;
	}
	
	//gives the member ID of the committee member
	public int getIDNumber()
	{
		return IDNumber;
	}
}
