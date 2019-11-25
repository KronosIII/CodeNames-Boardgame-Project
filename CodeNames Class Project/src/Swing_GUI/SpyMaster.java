package Swing_GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.NumberFormatter;

import Swing_Functionality.ClueButton;
import code.Board;

public class SpyMaster {
	private Object NP = null;
	final private Boolean truthValue = true;
	private Board gameBoard;
	private CodeNames codeNamesPanel;
	private JPanel MainGamePanel;
	private JPanel GetTurns;
	private JPanel textEntryPanelOne;
	private JPanel textEntryPanelTwo;
	private JPanel clueConfirmerPanel;
	private JPanel countConfirmerPanel;
	public static JLabel verificationPanel;
	public static JTextField textEntryOne;
	public static JTextField textEntryTwo;
	public static JPanel getSpyMasterWords;
	public static JLabel spyMasterStatus;

	/**
	 * Responsible for The SpyMasterPanel's Look and Feel
	 * 
	 * @param Board
	 *            : Takes in a Board parameter to add Functionality to The
	 *            SpyMasterPanel
	 * @param codeNames
	 *            : The CodeNames class is connected to the SpyMaster b
	 * @param masterPanel
	 *            :
	 */
	public SpyMaster(Board board, CodeNames codeNames, JPanel MasterPanel) {
		gameBoard = board;
		codeNamesPanel = codeNames;
		MainGamePanel = MasterPanel;
		MainGamePanel.setLayout(new BoxLayout(MainGamePanel, BoxLayout.Y_AXIS));
		GetTurns = new JPanel();
		MainGamePanel.add(GetTurns);
		spyMasterStatus = new JLabel("RedAgent SpyMaster");
		GetTurns.add(spyMasterStatus);
		clueConfirmerPanel = new JPanel();
		MainGamePanel.add(clueConfirmerPanel);
		countConfirmerPanel = new JPanel();
		MainGamePanel.add(countConfirmerPanel);
		getSpyMasterWords = new JPanel();
		MainGamePanel.add(getSpyMasterWords);
		getSpyMasterWords.setLayout(new GridLayout(5, 5));
		textEntryPanelOne = new JPanel();
		MainGamePanel.add(textEntryPanelOne);
		textEntryPanelOne.setLayout(new BoxLayout(textEntryPanelOne, BoxLayout.X_AXIS));
		textEntryPanelTwo = new JPanel();
		MainGamePanel.add(textEntryPanelTwo);
		textEntryPanelTwo.setLayout(new BoxLayout(textEntryPanelTwo, BoxLayout.X_AXIS));
		textEntryOne = new JTextField(20);
		textEntryPanelOne.add(textEntryOne);
		
		
		JButton clueSubmit = new JButton("Enter Clue");
		clueSubmit.addActionListener(new ClueButton(gameBoard));
		textEntryPanelOne.add(clueSubmit);
		/**
		 * Automatically formats the number with commas and more
		 */
		NumberFormat IlleagalCounts = NumberFormat.getIntegerInstance();
		NumberFormatter setLegalNumber = new NumberFormatter(IlleagalCounts);
		setLegalNumber.setAllowsInvalid(false);
		setLegalNumber.setMinimum(00);
		JFormattedTextField field = new JFormattedTextField(setLegalNumber);
		textEntryTwo = field;
		textEntryTwo.setEditable(true);
		textEntryPanelTwo.add(textEntryTwo);
		JButton countSubmit = new JButton("Enter Count");

		countSubmit.addActionListener(new ActionListener() {
			String RedSquad = "Red Agents It's Time to Guess";
			String BlueSquad = "BlueAgents It's Time to Guess";
			String GreenSquad = "GreenAgents It's Time to Guess";
			@Override
			public void actionPerformed(ActionEvent e) {

				if (Integer.decode(SpyMaster.textEntryTwo.getText()) <= 0) {
				}

				else {
					CodeNames.count = Integer.decode(SpyMaster.textEntryTwo.getText());
					CodeNames.clueLabel.setText("Clue:  " + CodeNames.EntryClue);
					CodeNames.countLabel.setText("   Count:  " + CodeNames.count);
				}

				if (CodeNames.EntryClue != NP && CodeNames.count != NP && !CodeNames.EntryClue.equals(Board.emptyString)) {

					MainGUI.codeNames.setVisible(truthValue);
					MainGUI.spyMaster.setVisible(truthValue);
					if(!Board.threePlayerGame){
						if ((Board.TrackingNumber & 1) == 0) {
							CodeNames.alertPanel.setText(RedSquad);
						}

						if (Board.BlueAgentCount % 2 == 1) {
							CodeNames.alertPanel.setText(BlueSquad);

						}
					}else if(Board.threePlayerGame){
						if (Board.redSquadTurn) {
							CodeNames.alertPanel.setText(RedSquad);
						}

						if (Board.blueSquadTurn) {
							CodeNames.alertPanel.setText(BlueSquad);

						}
						if (Board.greenSquadTurn) {
							CodeNames.alertPanel.setText(GreenSquad);

						}
					}
					

				}
			}
		});

		textEntryPanelTwo.add(countSubmit);

		Integer Increment = 0;
		while (Increment < 25) {
			JLabel JavaLabels = SpyMasterLabels().get(Increment);
			spyMasterLabelProperties(JavaLabels);
			allocateSpyMasterColor(Increment, JavaLabels);
			getSpyMasterWords.add(JavaLabels);
			Increment++;
		}

		/**
		 * @info Creates The MenuBar in the SpyMaster
		 */
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem ItemOne = new JMenuItem("Don't Click");
		JMenuItem ItemTwo = new JMenuItem("Exit Game");
		JMenuItem ItemThree = new JMenuItem("Tips & Tricks");
		ItemOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			codeNamesPanel.restartMethod();

			}

		});
		/**
		 * @Info Allows for Game to be exited once the "Exit" Icon is clicked
		 */
		ItemTwo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});
		
		menu.add(ItemOne);
		menu.addSeparator();
		menu.add(ItemTwo);
		menu.addSeparator();
		menu.add(ItemThree);
		menuBar.add(menu);
		MainGUI.spyMaster.setJMenuBar(menuBar);
	}

	/**
	 * @Info Responsible for how the Labels on the SpyMasterPanel look
	 * @param  Jlabel 
	 */
	public static void spyMasterLabelProperties(JLabel labelProperties) {
		labelProperties.setFont(new Font("Times New Roman", Font.BOLD, 12));
		labelProperties.setPreferredSize(new Dimension(200, 50));
		labelProperties.setBackground(Color.WHITE);
		labelProperties.setForeground(Color.BLACK);
		labelProperties.setOpaque(true);
		labelProperties.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
	}

	/**
	 * @Info Sets the Color of The SpyMaster Labels
	 * @param label
	 * @param Increment
	 */
	public static void allocateSpyMasterColor( int Increment, JLabel label) {
		String RA= "RedAgent";
		String BA = "BlueAgent";
		String GA = "GreenAgent";
		String A = "Assassin";
		String IB = "Innocent Bystander";
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(RA)) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		}

		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(BA)) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(GA)) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(A)) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		}

		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(IB)) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		}

	}

	/**
	 * @Info Changes the Text after the a CodeName has been revealed
	 * @param JavaLabels
	 * @param Increment
	 */
	public static void changeTextAfterReveal(JLabel JavaLabels, Integer Increment) {
		String RA= "RedAgent";
		String BA = "BlueAgent";
		String GA = "GreenAgent";
		String A = "Assassin";
		String IB = "Innocent Bystander";
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(RA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {

			JavaLabels.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(BA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {

			JavaLabels.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());

		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(GA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {

			JavaLabels.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());

		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(A)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {

			JavaLabels.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(IB)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {

			JavaLabels.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());

		}

	}

	/**
	 * @Info Creates The SpyMaster Labels
	 */
	public static ArrayList<JLabel> SpyMasterLabels() {
		ArrayList<JLabel> SpyMasterLabels = new ArrayList<JLabel>();
		Integer Increment = 0;
		while (Increment < 25) {
			SpyMasterLabels.add(Increment,new JLabel(CodeNames.LocationsOnTheBoard.get(Increment).getCodename() +
					"   " + CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor(),
									SwingConstants.CENTER));
			Increment++;
		}
		return SpyMasterLabels;
	}

}
