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
		while (currentlyRunning == true)
		{
			System.out.println("Enter a ID Number:\t");
			string username = scanner.nextLine();
		
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
						else
						{
							float truth = 1;
							while (truth % 2 != 0)
							{
								System.out.println("Enter Name:\t");
								string name = scanner.nextLine();
								System.out.println("Enter Social Security Number:\t");
								string ssn = scanner.nextLine();
						
								if (name.equals(registered[i].getName()) && ssn.equals(registered[i].getSsn()))
								{
									truth += 1;
								}	
							}
							float truth_2 = 1;
							while (truth_2 % 2 != 0)
							{
								System.out.print("Please enter the name of the candidate you would like to vote for:\t");
								string nominee = scanner.nextLine();
							
								for (int i = 0; i < nominees.length; i++)
								{
									if (nominee.euqals(nominees[i].getName()))
									{
										System.out.print("Would you like to submit your vote? y or n:\t");
										string answer = scanner.nextLine();
									
										if (answer.equals("y"))
										{
											active.castVote(nominee);
											voteTally.add(active);
											truth_2 += 1;
										}
										else
										{
											System.out.println("Cast ballot system restarting");
										}
									}
								}
							}
						
						}
					
					}

				
				}
			}
			
		}
			

		
	}
	
	
}
