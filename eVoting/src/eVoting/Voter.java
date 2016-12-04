package eVoting;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Voter extends JFrame {

	private JPanel contentPane;
	private int voterID;
	private SqlConnector db;

	/**
	 * Launch the application.
	 */
	
		public void vote() {
			try 
			{
				this.setVisible(true);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	

	/**
	 * Create the frame.
	 */
	public Voter(SqlConnector connection, int ID) throws SQLException
	{
		db = connection;
		voterID = ID;
		setTitle("Voting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		//pull candidates from the database, and create a radio button for each one
		ResultSet candidates = db.getData("SELECT * FROM evote.nominees");
		int numberCandidates;
		JRadioButton[] selections;
		ButtonGroup group = new ButtonGroup();
		int i = 0;

		if(!candidates.first())
		{//if there is no first selection, then the database is void of nominees
			JOptionPane.showMessageDialog(contentPane, "There are no nominees in the database");
			numberCandidates = 0;
			selections = new JRadioButton[0];
		}
		else
		{//grab the final row, which gives how many rows in total are in the resultSet
			candidates.last();
			numberCandidates = candidates.getRow();
			candidates.beforeFirst();
			selections = new JRadioButton[numberCandidates];
			
			
			//after finding out how many nominees there are, create a radioButton for each one
			//add that radio button to the same group
			//then set an action listener using the Candidate's name
			while(candidates.next())
			{
				selections[i] = new JRadioButton(candidates.getString(2));
				group.add(selections[i]);	//add the button to the group
				selections[i].setActionCommand(candidates.getString(2));
				i++;
			}
		}

		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int choice = JOptionPane.showConfirmDialog(contentPane, "Do you not want to cast a ballot?", "Please vote!", JOptionPane.YES_NO_OPTION );
				if(choice == JOptionPane.YES_OPTION)
				{
					dispose();
				}
			}
		});
		contentPane.add(btnCancel, BorderLayout.EAST);
		
		JButton btnVote = new JButton("vote");
		btnVote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//find the selection from the group, grabbing the candidate's name
				String chosen = group.getSelection().getActionCommand();
				//pull the chosen candidate's information from the database
				try 
				{
					String query = "SELECT * FROM evote.nominees WHERE nominees.name = \"" + chosen + "\"";
				
					ResultSet candidate = db.getData(query);
					candidate.first();		//CODE WILL NOT RUN WITHOUT THIS STATEMENT
					int finalDecision = JOptionPane.showConfirmDialog(contentPane, "You have selected candidate " + candidate.getString(2) + 
								", representing the party: " + candidate.getString(3) + "\nIs this who you wish to vote for?", "Final Choice!", JOptionPane.OK_CANCEL_OPTION);
					if(finalDecision == JOptionPane.OK_OPTION)
					{
						String voterUpdate = "UPDATE registeredvoters SET hasVoted = 1 WHERE VoterID = " + Integer.toString(voterID);
						db.editData(voterUpdate);
								
						String tallyUpdate = "UPDATE nominees SET votes = (votes + 1) WHERE ID = " + candidate.getInt(1);
						db.editData(tallyUpdate);
						
						dispose();
					}
				} 
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					System.out.println("Error: " + e1);
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnVote, BorderLayout.WEST);

		JLabel lblPleaseSelectThe = new JLabel("Please select the candidate you would like to vote for.");
		contentPane.add(lblPleaseSelectThe, BorderLayout.NORTH);
		
		
		JPanel candidateOptions = new JPanel();
		candidateOptions.setLayout(new BoxLayout(candidateOptions, BoxLayout.PAGE_AXIS));
		for(int z = 0; z < numberCandidates; z++)
		{
			candidateOptions.add(selections[z]);
		}
		contentPane.add(candidateOptions, BorderLayout.CENTER);
	}

}
