package eVoting;

package eVoting;

import java.util.ArrayList;
import java.util.Scanner;

public class Election 
{	
	public static void main(String [] args)
	{
		//create sql connection
		String user = "root";
		String password = "Scampers9/2";
		String server = "localhost";
		int port = 3306;
		String DB = "evote";
		boolean currentlyRunning;		//is the election currently active?
		
		SqlConnector connection = new SqlConnector(user, password, server, port, DB);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Voter or Member:\t");
		String username = scanner.nextLine();
		
		//create the committee object
		ElectorateCommittee committee = new ElectorateCommittee(connection);
		
		//if the user is a member, have them login
		if (username.equals("Member"))
		{	
			while(!committee.login());
		}
		else if (username.equals("Voter"))
		{
			if (currentlyRunning == false)
			{
				System.out.println("You cannot vote at this time!");
			}
			else
			{
				System.out.println("enter registration number:\t");
				String reg_number = scanner.nextLine();
				
				
				for (int i = 0; i < registered.length; i++)
				{
					if (reg_number.equals(registered[i].getID()))
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
								truth -= 1;
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
								truth_2 -= 1;
							
							}
						
						}

					
					}
				}
			}
		}
		else
		{
			System.out.println("Did not enter voter or member");
		}
		}

	}
		