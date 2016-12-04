package eVoting;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Election extends JFrame {

	private JPanel contentPane;
	private JPanel optionPane;
	private JButton vote;
	private JTextField txtUsername;
	private JPasswordField electoratePassword;
	private static boolean currentlyRunning;
	private static Election startFrame;
	private static Election committeeOptions;
	
	private final String NOTVOTING = "Voting is currently inactive";
	private final String NOWVOTING = "Voting is currently enabled";
	
	private int type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try 
				{
					//create sql connection
					String user = "root";
					String password = "Scampers9/2";
					String server = "localhost";
					int port = 3306;
					String DB = "evote";
					currentlyRunning = false;		//currentlyRunning defaults to false
					
					SqlConnector connection = new SqlConnector(user, password, server, port, DB);	//controls all sql work
					ElectorateCommittee committee = new ElectorateCommittee(connection);			//committee that controls committee options
					
					startFrame = new Election(committee, connection);
					committeeOptions = new Election(committee, 0);
					startFrame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	//Initial startupPane
	/**
	 * @wbp.parser.constructor
	 */
	public Election(ElectorateCommittee council, SqlConnector db) {
		this.type = 1;	//note that this is the starterFrame
		//auto generated by WindowBuilder
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//-----------------------------------
		
		JLabel lblClickvoteTo = new JLabel("Click \"Vote\" to start the voting Process");
		
		JLabel lblIfYouAre = new JLabel("If you are a member of the electorate committee, please enter your username and password for options");
		
		JLabel lblIfvoteIs = new JLabel("If \"Vote\" is not availalable, the election has not yet started or is halted");
		
		this.vote = new JButton("Vote");
		//if vote is clicked, the user will be prompted for their voterID
		vote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String id = JOptionPane.showInputDialog("Please enter your voter ID:");	//ask the user for their ID
				String query = "SELECT * FROM evote.registeredvoters where registeredVoters.VoterID = " + id;
				try
				{
					ResultSet currentUser = db.getData(query);	//pull the user from the table of registered voters, assuming he is there
					if(!currentUser.first())
					{//if there is not entry in the result set, then there was no voter with that ID
						JOptionPane.showMessageDialog(startFrame, "There is no registered user with that ID in the system");
					}
					else if(currentUser.getInt(4) == 1)
					{//make sure that the current user has not already voted
						JOptionPane.showMessageDialog(startFrame, "We are sorry, but it appears you have already voted in this election. Thank you for your participation!");
					}
					else
					{//if there is a result, then there is a voter.
						//create a voter object, and call the vote method
						int userID = currentUser.getInt(1);
						Voter registeredUser = new Voter(db, userID);
						registeredUser.vote();
					}
				}
				catch(SQLException e1)
				{
					JOptionPane.showMessageDialog(startFrame, "The entry: " + id + ", is not a valid ID.  Please ensure you enter only numbers when typing the ID.");
				}
			}
		});
		if(currentlyRunning)
		{//the button is enabled if voting has been enabled by a user
			this.vote.setEnabled(true);
		}
		else
		{
			this.vote.setEnabled(false);
		}
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		
		electoratePassword = new JPasswordField();
		
		JLabel lblUsername = new JLabel("Username:");
		
		JLabel lblPassword = new JLabel("Password:");
		
		//controls login of a committee member
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//optain the username and passwords from the given fields
				String username = txtUsername.getText();
				char[] entryPass = new char[30];
				entryPass = electoratePassword.getPassword();
				String password = new String(entryPass);
				
				try 
				{
					int result = council.login(username, password);
					if(result == 0)
					{
						committeeOptions.setVisible(true);
						startFrame.setVisible(false);
					}
					else if(result == 1)
					{
						JOptionPane.showMessageDialog(startFrame, "The password you entered was incorrect");
					}
					else
					{
						JOptionPane.showMessageDialog(startFrame, "No member found with the given username");
					}
				}
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(contentPane, "There was a problem with the database, please ask for assistance."); 
				}
			}
		});

		
		//Code below controls layout of the window
		//All code below has been auto-generated by the WindowBuilder plugin
		//----------------------------------------------------------------------------------------------------------------------
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(vote)
									.addGap(284)
									.addComponent(lblUsername))
								.addComponent(lblPassword))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(58)
									.addComponent(btnLogin))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(electoratePassword)
										.addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))))
							.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
						.addComponent(lblClickvoteTo)
						.addComponent(lblIfYouAre)
						.addComponent(lblIfvoteIs))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addComponent(lblClickvoteTo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblIfYouAre)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblIfvoteIs)
							.addGap(24)
							.addComponent(vote)
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUsername)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(btnLogin))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPassword)
							.addComponent(electoratePassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		//above code is auto-generated
		//-----------------------------------------------------------------------------------------------------------------------------------
		
		
	}
	
	//Frame 2:
	//this frame shows the committee options
	public Election(ElectorateCommittee electorate, int choice) {
		this.type = choice;		//note that this is the optionFrame
		this.vote = null;		//vote is not needed for this window
		//initialize the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		optionPane = new JPanel();
		optionPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(optionPane);
		
		//label that denotes whether voting is currently enabled or not
		JLabel votingActive = new JLabel();
		if(currentlyRunning)
		{
			votingActive.setText(NOWVOTING);
		}
		else
		{
			votingActive.setText(NOTVOTING);
		}
		
		//this button enables the system to accept votes
		JButton btnEnableVoting = new JButton("Enable Voting");
		if(currentlyRunning)
		{//if voting is already enabled, this button does not need to be active
			btnEnableVoting.setEnabled(false);
		}
		else
		{
			btnEnableVoting.setEnabled(true);
		}
		
		//disables voting
		JButton btnHaltElection = new JButton("Halt Election");
		if(!currentlyRunning)
		{//if voting is not enabled, then this button is not needed
			btnHaltElection.setEnabled(false);
		}
		else
		{
			btnHaltElection.setEnabled(true); 
		}
		
		//displays the current standings of the election
		//standings may not be viewed unless voting is currently disabled
		JButton btnViewStandings = new JButton("View Standings");
		if(currentlyRunning)
		{
			btnViewStandings.setEnabled(false);
		}
		else
		{
			btnViewStandings.setEnabled(true);
		}
		
		//button that allows the user to return to the main menu
		JButton btnExit = new JButton("Exit");
		
		//ends the election permanently.  Results will be shown before exiting the system
		JButton btnEndElection = new JButton("End Election");
		if(currentlyRunning)
		{
			btnEndElection.setEnabled(false);
		}
		else
		{
			btnEndElection.setEnabled(true);
		}
				
		//click event to enable voting
		btnEnableVoting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				currentlyRunning = true;
				JOptionPane.showMessageDialog(optionPane, "Voting has been enabled");
				votingActive.setText(NOWVOTING);
				//alter the available buttons as a result of voting being enabled
				btnEnableVoting.setEnabled(false); 
				btnHaltElection.setEnabled(true);
				btnViewStandings.setEnabled(false);
				btnEndElection.setEnabled(false);
				startFrame.setVote(true);
			}
		});
		
		//click event for halting the voting process
		btnHaltElection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				currentlyRunning = false;
				JOptionPane.showMessageDialog(optionPane, "Voting has been halted");
				votingActive.setText(NOTVOTING);
				//alter the available buttons as a result of voting being disabled
				btnEnableVoting.setEnabled(true);
				btnHaltElection.setEnabled(false); 
				btnViewStandings.setEnabled(true);
				btnEndElection.setEnabled(true);
				startFrame.setVote(false);
			}
		});
		
		//click event for viewing the current results
		btnViewStandings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String results = electorate.viewResults();		//NOTE: this will return a boolean value and alter a given string value, prevents needing to make a new JFrame in electorateCommittee
					JOptionPane.showMessageDialog(optionPane, results);
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(optionPane, "There was an error connecting to the database");
				}
			}
		});
		
		//click button for closing this window
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!currentlyRunning)
				{//if voting has not been enabled, ensure the user wishes to return to the previous screen
					String message = "Voting has not been enabled.  Are you sure you wish to exit?";
					int choice = JOptionPane.showConfirmDialog(optionPane, message, "HALT", JOptionPane.OK_CANCEL_OPTION);
					
					if(choice == JOptionPane.OK_OPTION)
					{//if the user selects "OK", then the frame will be hidden
						committeeOptions.setVisible(false);
						startFrame.setVisible(true);
					}
					
				}
				else
				{//if voting is enabled, then the system will not ask for confirmation
					committeeOptions.setVisible(false);	//hides this window
					startFrame.setVisible(true);
				}
			}
		});
		
		//click event for ending the election
		btnEndElection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String message = "This will end the election PERMANENTLY.\nOnce it has been finished, no further votes may be tallied." +
							"\nDo you wish to end the election?";
				int choice = JOptionPane.showConfirmDialog(optionPane, message, "HALT", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.YES_OPTION)
				{//if the user selects yes, this will close the entire system
					System.exit(0);
				}
			}
		});
		
		//Auto generated by windowBuilder
		//------------------------------------------------------------------------------------------
		GroupLayout gl_optionPane = new GroupLayout(optionPane);
		gl_optionPane.setHorizontalGroup(
			gl_optionPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_optionPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnEndElection, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnHaltElection, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnEnableVoting, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnViewStandings, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnExit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
					.addComponent(votingActive)
					.addGap(21))
		);
		gl_optionPane.setVerticalGroup(
			gl_optionPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_optionPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEnableVoting)
						.addComponent(votingActive))
					.addGap(18)
					.addComponent(btnHaltElection)
					.addGap(18)
					.addComponent(btnViewStandings)
					.addGap(18)
					.addComponent(btnExit)
					.addGap(18)
					.addComponent(btnEndElection)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		optionPane.setLayout(gl_optionPane);
		//------------------------------------------------------------------------------------------------------------
		//auto generated by window Builder
	}
	
	public void setVote(boolean choice)
	{
		if(this.type == 1)
		{
			this.vote.setEnabled(choice);
		}
	}

}


