package eVoting;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CommitteeOptions extends JFrame {

	private JPanel optionPane;
	private final String NOTVOTING = "Voting is currently inactive";
	private final String NOWVOTING = "Voting is currently enabled";
	private boolean currentlyRunning;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String user = "root";
					String password = "Scampers9/2";
					String server = "localhost";
					int port = 3306;
					String DB = "evote";
							
					SqlConnector connection = new SqlConnector(user, password, server, port, DB);	//controls all sql work
					ElectorateCommittee committee = new ElectorateCommittee(connection);			//committee that controls committee options
					
					CommitteeOptions frame = new CommitteeOptions(committee, false);
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CommitteeOptions(ElectorateCommittee electorate, boolean running) {
		//initialize the window
		currentlyRunning = running;				//is voting currently enabled?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		optionPane = new JPanel();
		optionPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(optionPane);
		
		//label that denotes whether voting is currently enabled or not
		JLabel votingActive = new JLabel();
		votingActive.setText("Voting is currently inactive");
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
			}
		});
		
		//click event for viewing the current results
		btnViewStandings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					electorate.viewResults();		//NOTE: this will return a boolean value and alter a given string value, prevents needing to make a new JFrame in electorateCommittee
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
						setVisible(false);
					}
					
				}
				else
				{//if voting is enabled, then the system will not ask for confirmation
					setVisible(false);	//hides this window
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
}
