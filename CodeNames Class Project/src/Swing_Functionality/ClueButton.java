package Swing_Functionality;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Swing_GUI.CodeNames;
import Swing_GUI.MainGUI;
import Swing_GUI.SpyMaster;
import code.Board;


	public class ClueButton implements ActionListener{
		/***
		 * 
		 * 
		 * 
		 */
		private Board gameBoard;
		Object NP = null;
		Boolean truthValue = true;
		public ClueButton(Board board) {
			gameBoard = board;
		}
		
			@Override
			public void actionPerformed(ActionEvent e){		
					String RS = "RedSquad Enter a Guess | Clue";
					String BS = "BlueSquad Enter a Guess | Clue";
				if(gameBoard.checkClue(CodeNames.LocationsOnTheBoard,SpyMaster.textEntryOne.getText()) == truthValue){
					CodeNames.EntryClue = SpyMaster.textEntryOne.getText();
					CodeNames.clueLabel.setText("Clue: " + CodeNames.EntryClue );
					CodeNames.countLabel.setText("   Count: " + CodeNames.count);
				} else {
					JOptionPane.showMessageDialog((Component) NP, "Illegal Clue! please enter again.", "Congratulations You Played Yourself",JOptionPane.ERROR_MESSAGE);
				}
				
				if(CodeNames.EntryClue != NP && CodeNames.count != NP) {
					 MainGUI.codeNames.setVisible(truthValue);
					 MainGUI.spyMaster.setVisible(truthValue);
					 if((Board.TrackingNumber & 1) == 0) {
						CodeNames.alertPanel.setText(RS);
					}else{
						CodeNames.alertPanel.setText(BS);
						
					}
				
				}
			}
	}	
	
	
	
	

