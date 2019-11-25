package Swing_Functionality;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import Swing_GUI.CodeNames;
import Swing_GUI.SpyMaster;
import code.Board;
import code.Location;


public class SubmitButton implements ActionListener{

	private Integer index;
	private String codeNames;
	private Board gameBoardNames;
	private JLabel codeNamesLabel;
	private CodeNames codeNamesGUI;	
	private JButton codeNameButtons;
	private ArrayList<Location> gameBoard;
	

		
	/**
	 * @Info  When a CodeNames Button is This class handles those actions
	 */
	public SubmitButton(String name,Board board, JButton Button,ArrayList<Location> gameBoardLocations, Integer Increment, JLabel JavaLabel, CodeNames CN ){		
			
		codeNameButtons = Button;
		index = Increment;
		gameBoardNames = board;
		codeNames = name;
		gameBoard = gameBoardLocations;
		codeNamesLabel = JavaLabel;
		codeNamesGUI = CN;
					
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameBoardNames.getLocationStatus(gameBoard, codeNames);
		SpyMaster.changeTextAfterReveal(codeNamesLabel, index);
		CodeNames.ChangeColorAndText(index, codeNameButtons);
		gameBoardNames.getTurn(gameBoard , codeNames);
		CodeNames.clueLabel.setText("Clue: " + CodeNames.EntryClue );
		CodeNames.countLabel.setText("   Count: " + CodeNames.count);
		codeNamesGUI.getMyBoard().getVictoryFormation(gameBoard);
		codeNamesGUI.getMyBoard().getAssassinStatus(gameBoard);	
		codeNameButtons.setEnabled(false);
	}
	
	
}
