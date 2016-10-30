package eVoting;
import java.util.ArrayList;

public class ElectorateCommittee 
{
	private ArrayList<Member> memberList;
	
	//creates an electorate committee, using members from a given file
	//note: this will be updated to use a database later
	public ElectorateCommittee(String fileName)
	{
		System.out.println("not finished");		//filler text
	}
	
	//adds a new member to the committee
	public void addMember(Member newMember)
	{
		memberList.add(newMember);
	}
	
}
