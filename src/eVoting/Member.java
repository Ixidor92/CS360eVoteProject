package eVoting;

public class Member 
{
	private String name;
	private int IDNumber;
	private String login;
	
	public boolean checkPassword(String password)
	{
		return (login.equals(password));
	}
}
