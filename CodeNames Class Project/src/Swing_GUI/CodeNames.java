package Swing_GUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import Swing_Functionality.SubmitButton;
import code.Board;
import code.Location;

public class CodeNames {

	private JPanel CodeNamesGamePanel;
	private JPanel TurnCompleted;
	private JPanel scoreBoard;
	private JPanel mergeCountAndCluePanel;
	public static String EntryClue;
	public static Integer count;
	public static JPanel text;
	private static Board gameBoard;
	public static JLabel clueLabel;
	public static JLabel countLabel;
	public static JLabel alertPanel;
	public static JLabel VerificationStatus;
	public static ArrayList<Location> LocationsOnTheBoard;

	/**
	 * @Info When a CodeNames Button is This class handles those actions
	 */
	public CodeNames(Board board, JPanel masterPanel) {
		Integer Increment = 0;
		setMyBoard(board);
		EntryClue = "______";
		count = null;
		getMyBoard();
		/**
		 * @Info Do Not Edit The Static/Non-Static
		 */
		LocationsOnTheBoard = Board.randomGeneratedAssignments(getMyBoard().createLocations(),getMyBoard().listOf25CodeNames(getMyBoard().randomlySelectedCodeNames()));
		getMyBoard().redMovesFirst();
		CodeNamesGamePanel = masterPanel;
		CodeNamesGamePanel.setOpaque(true);
		CodeNamesGamePanel.setLayout(new BoxLayout(CodeNamesGamePanel, BoxLayout.Y_AXIS));
		scoreBoard = new JPanel();
		scoreBoard.setLayout((new BoxLayout(scoreBoard, BoxLayout.X_AXIS)));
		CodeNamesGamePanel.add(scoreBoard);
		TurnCompleted = new JPanel();
		CodeNamesGamePanel.add(TurnCompleted);
		alertPanel = new JLabel("Red Team's Turn");
		VerificationStatus = new JLabel("________");
		TurnCompleted.add(alertPanel);
		TurnCompleted.add(VerificationStatus);
		mergeCountAndCluePanel = new JPanel();
		CodeNamesGamePanel.add(mergeCountAndCluePanel);
		clueLabel = new JLabel("Clue: " + EntryClue);
		countLabel = new JLabel("       Count: " + count);
		mergeCountAndCluePanel.add(clueLabel);
		mergeCountAndCluePanel.add(countLabel);
		text = new JPanel();
		text.setLayout(new GridLayout(5, 5));
		CodeNamesGamePanel.add(text);

		while (Increment < 25) {
			JButton JavaButtons = codeNameButtons().get(Increment);
			JLabel JavaLabels = SpyMaster.SpyMasterLabels().get(Increment);
			text.add(JavaButtons);
			setButtonProperties(JavaButtons);
//			JavaButtons.addActionListener(new SubmitButton(JavaButtons.getText(), getMyBoard(), JavaButtons,
//					LocationsOnTheBoard, Increment, JavaLabels, this));
			Increment++;
		}
		/**
		 * @Info Responsible to making the Menu in the CodeNames Panel
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.LIGHT_GRAY);
		JMenu menu = new JMenu("File");
		menu.setOpaque(true);
		menu.setBackground(Color.LIGHT_GRAY);
		JMenuItem itemOne = new JMenuItem("New Game");
		JMenuItem itemTwo = new JMenuItem("Exit Game");
		JMenuItem itemThree = new JMenuItem("Real CodeNames");
		itemOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				restartMethod();

			}

		});
		/**
		 * @Info Allows the Buttons to exit the game when clicked
		 */
		itemTwo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});
		/**
		 * @Info Allows the Buttons to use EasterEgg
		 */
		itemThree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Desktop internetLink = Desktop.getDesktop();
				try {
					internetLink.browse(new URI("https://www.youtube.com/watch?v=vJPTbMxKcvA"));
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				}
			}
		});

		menu.add(itemOne);
		menu.addSeparator();
		menu.add(itemTwo);
		menu.addSeparator();
		menu.add(itemThree);
		menuBar.add(menu);
		MainGUI.codeNames.setJMenuBar(menuBar);

	}

	/**
	 * @Info This will run when the user resets/restarts the game
	 */
	public void restartMethod() {
		if(!Board.threePlayerGame){
			Board.RedAgentCount = 9;
			Board.BlueAgentCount = 8;
			Board.InnocentBystanderCount = 7;
			Board.AssassinCount = 1;
			
		}else if(Board.threePlayerGame){
			Board.RedAgentCount = 6;
			Board.BlueAgentCount = 5;
			Board.GreenAgentCount = 5;
			Board.InnocentBystanderCount = 7;
			Board.AssassinCount = 2;
		}
		Board.TrackingNumber = 0;
		Integer Increment = 0;
		
		text.removeAll();
		SpyMaster.getSpyMasterWords.removeAll();
		getMyBoard();
		/**
		 * @Info Do Not Edit The Static/Non-Static
		 */
		LocationsOnTheBoard = Board.randomGeneratedAssignments(getMyBoard().createLocations(),getMyBoard().listOf25CodeNames(getMyBoard().randomlySelectedCodeNames()));
		getMyBoard().redMovesFirst();

			while (Increment < 25) {
			String emptyString = "";
			String wideString = "    ";
			JButton JavaButtons = codeNameButtons().get(Increment);
			codeNameButtons().get(Increment).setText(LocationsOnTheBoard.get(Increment).getCodename());
			text.add(JavaButtons);
			setButtonProperties(JavaButtons);

			JLabel JavaLabels = SpyMaster.SpyMasterLabels().get(Increment);
			SpyMaster.SpyMasterLabels().get(Increment).setText(
					LocationsOnTheBoard.get(Increment).getCodename() + wideString + LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
			SpyMaster.getSpyMasterWords.add(JavaLabels);
			SpyMaster.spyMasterLabelProperties(JavaLabels);
			SpyMaster.allocateSpyMasterColor(Increment,JavaLabels);
			SpyMaster.textEntryOne.setText(emptyString);
			SpyMaster.textEntryTwo.setText(emptyString);
			JavaButtons.addActionListener(new SubmitButton(JavaButtons.getText(), getMyBoard(), JavaButtons,
					LocationsOnTheBoard, Increment, JavaLabels, this));
			Increment++;
		}	
		String RedSquad = "RedSquad Make A Good Guess";
		String RedSpyMaster = "Begin : RedSquad SpyMaster ";
		String emptyString = "______";
		String EnterCount = "Please Enter A Count";
		alertPanel.setText(RedSquad);
		VerificationStatus.setText(EnterCount);
		VerificationStatus.setForeground(Color.black);
		SpyMaster.spyMasterStatus.setText(RedSpyMaster);
		clueLabel.setText("Clue: " + emptyString);
		countLabel.setText("   Count: " + emptyString);
		MainGUI.codeNames.pack();

	}

	/*
	 * @Info Responsible for Color and Font of The Buttons On the CodeName Board
	 * @param button
	 */
	public void setButtonProperties(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setPreferredSize(new Dimension(150, 150));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.BLACK));
	}

	/*
	 * After a Button Has been revealed changes color and Text on the SpyMasterPanel
	 * @param JavaButtons
	 * @param Increment
	 */
	public static void ChangeColorAndText(Integer Increment,JButton JavaButtons) {
		String RA = "RedAgent";
		String BA = "BlueAgent";
		String A = "Assassin";
		String GA = "GreenAgent";
		String IB = "Innocent Bystander";
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(RA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {
			JavaButtons.setBackground(Color.RED);
			JavaButtons.setForeground(Color.BLACK);
			JavaButtons.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}

		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(GA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {
			JavaButtons.setBackground(Color.GREEN);
			JavaButtons.setForeground(Color.BLACK);
			JavaButtons.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}
		
		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(BA)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {
			JavaButtons.setBackground(Color.CYAN);
			JavaButtons.setForeground(Color.BLACK);
			JavaButtons.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}

		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(A)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupant().getReveal() == true) {
			JavaButtons.setBackground(Color.DARK_GRAY);
			JavaButtons.setForeground(Color.BLACK);
			JavaButtons.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());
		}

		if (CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor().equals(IB)
				&& CodeNames.LocationsOnTheBoard.get(Increment).getOccupantStatus() == true) {
			JavaButtons.setBackground(Color.MAGENTA);
			JavaButtons.setForeground(Color.BLACK);
			JavaButtons.setText(CodeNames.LocationsOnTheBoard.get(Increment).getAgentsSquadColor());

		}

	}

	/**
	 * @Info Method Which creates the buttons on the codeName Panels
	 */
	public static ArrayList<JButton> codeNameButtons() {
		ArrayList<JButton> codeNamesButtons = new ArrayList<JButton>();
		Integer Increment = 0;

		while (Increment < 25) {
			codeNamesButtons.add(new JButton(LocationsOnTheBoard.get(Increment).getCodename()));
			Increment++;
		}
		return codeNamesButtons;
	}

	public  Board getMyBoard() {
		return gameBoard;
	}

	public static void setMyBoard(Board myBoard) {
		CodeNames.gameBoard = myBoard;
	}

	
	public static Integer getCount() {
		return count;
	}

	public static void setCount(Integer count) {
		CodeNames.count = count;
	}

}