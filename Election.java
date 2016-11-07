package eVoting;

import java.util.ArrayList;
import java.util.Scanner;

public class Election 
{
	private ArrayList<Nominee> nominees;	//a list of available nominees
	private boolean currentlyRunning;		//is the election currently active?
	private ArrayList<Voter> voteTally			//tally of those who have voted
	private ArrayList<Member> members;				//a list of committee
	private ArrayList<Voter> registered;					//a list of registered voters
	
	public static void main(String [] args)
	{
		System.out.println("Enter a ID Number:\t");
		String username = scanner.nextLine();
		
		for (int i = 0; i < members.length; i++)
		{
			if (username.equals(members[i].getIDNumber()))
			{
				System.out.println("Enter a Password:\t");
				String password = scanner.nextLine();
				
				if (members[i].checkPassword(password))
				{
					System.out.println("You are a Member!");
				}
				else
				{
					System.out.println("You are not a Member!");
				}
			}
		}
		for (int i = 0; i < registered.length; i++)
		{
			if (username.equals(registered[i].getID()))
			{
				Voter active = new Voter();
				active = registered[i];
				for (int i = 0; i < voteTally.length; i++)
				{
					if (active.getID().equals(voteTally[i].getID()))
					{
						System.out.println("You have already voted!");
					}
				}
			}
		}
			
		
		
		
	}
	
	
}
