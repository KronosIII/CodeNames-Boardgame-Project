package code;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import Swing_GUI.CodeNames;
import Swing_GUI.SpyMaster;

public class Board {

	public static int BlueAgentCount;
	public static int RedAgentCount;
	public static int GreenAgentCount;
	public static int InnocentBystanderCount;
	public static int AssassinCount;
	public static int TrackingNumber = 0;
	public static String emptyString = "______";
	private String Assassin = "Assassin";

	public static boolean threePlayerGame = false;
	public static boolean redSquadTurn = true;
	public static boolean blueSquadTurn = false;
	public static boolean greenSquadTurn = false;
	public static String redSquadStatus = "Playing";
	public static String blueSquadStatus = "Playing";
	public static String greenSquadStatus = "Playing";

	/**
	 * @Info Creation of the gameBoard contain 25 locations with a codeName and a
	 *       person
	 */
	public ArrayList<Location> gameBoard;

	public Board(Boolean threePlayerGame) {
		Board.threePlayerGame = threePlayerGame;
		
		int X;
		if(threePlayerGame == true) {
			X = 1;
		} else {
			X = 0;
		}
	

		switch(X) {
		case 1:
			BlueAgentCount = 5;
			RedAgentCount = 6;
			GreenAgentCount = 5;
			InnocentBystanderCount = 7;
			AssassinCount = 2;
			gameBoard = ListCreator.randomGeneratedAssignments(listOf25CodeNames(randomlySelectedCodeNames()),
				createLocations(),threePlayerGame);
			break;
			
		case 0:
			BlueAgentCount = 8;
			RedAgentCount = 9;
			InnocentBystanderCount = 7;
			AssassinCount = 1;
			gameBoard = ListCreator.randomGeneratedAssignments(listOf25CodeNames(randomlySelectedCodeNames()),
					createLocations(),threePlayerGame);
			break;
		}
	}

	/**
	 * @Info creates 25 Location instances
	 * @return a ArrayList<Location> containing 25 Location instances
	 */
	public ArrayList<Location> createLocations() {
		Integer Increment = 0;
		String filePath = "";
		Object nullPointer = null;
		ArrayList<Location> BoardLocations = new ArrayList<Location>(25);

		while(Increment < 25) {
			BoardLocations.add(new Location(filePath, (Person) nullPointer));
			Increment++;
		}

		return BoardLocations;
	}

	/**
	 * @Info 
	 * Red Squad starts off, generates random location with codename and a person,
	 * and then red Squad makes a guess
	 */
	public boolean redMovesFirst() {
		if (TrackingNumber == 0) {
			ListCreator.randomGeneratedAssignments(listOf25CodeNames(randomlySelectedCodeNames()),
					createLocations(),threePlayerGame
					);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param gameBoard
	 * @param codeNames
	 * @return the assignments that will appear on the codeNamesBoard
	 */
	public static ArrayList<Location> randomGeneratedAssignments(ArrayList<Location> gameBoard,
			ArrayList<String> codeNames) {
		Integer Increment = 0;
		Person	Object;
		Integer I1 = 0; Integer I2 = 0; Integer I3 = 0; Integer I4 = 0; Integer I5 = 0;
		ArrayList<Person> GeneratedAssingments = new ArrayList<Person>(25);
		if(!threePlayerGame){
			while(I1 < 9) {
				GeneratedAssingments.add(new RedAgent());
				I1++;
			}

			while (I2 < 8) {
				GeneratedAssingments.add(new BlueAgent());
				I2++;
			}
			while(I3 < 7) {
				GeneratedAssingments.add(new InnocentBystander());
				I3++;
			}
			while(I4 < 1) {
				GeneratedAssingments.add(new Assassin());
				I4++;
			}
		}else if(threePlayerGame){
			while(I1 < 6) {
				GeneratedAssingments.add(new RedAgent());
				I1++;
			}

			while (I2 < 5) {
				GeneratedAssingments.add(new BlueAgent());
				I2++;
			}
			while (I5 < 5) {
				GeneratedAssingments.add(new GreenAgent());
				I5++;
			}
			while(I3 < 7) {
				GeneratedAssingments.add(new InnocentBystander());
				I3++;
			}
			while(I4 < 2) {
				GeneratedAssingments.add(new Assassin());
				I4++;
			}
		}

		Collections.shuffle(GeneratedAssingments);
		Collections.shuffle(codeNames);

		while(Increment < 25) {
			Object = GeneratedAssingments.get(Increment);
			if (!gameBoard.get(Increment).isOccupied() && !gameBoard.get(Increment).hasCodeName()) {
				gameBoard.set(Increment, new Location(codeNames.get(Increment), Object));
			}
			Increment++;
		}

		return gameBoard;
	}

	/**
	 * @Info
	 * Reads all the lines of the GameWords.txt file and then adds each line in a
	 * ArrayList<String>
	 */
	public ArrayList<String> randomlySelectedCodeNames() {
		ArrayList<String> codeNames = new ArrayList<String>(25);

		try {
			for (String filePath : Files.readAllLines(Paths.get("GameWords.txt"))){
				codeNames.add(filePath);
			}			
		} catch (IOException ex) {
			ex.printStackTrace();
		} return codeNames;

	}

	/**
	 * Condense's The List From of Random CodeNames Down to 25 To Be Used on the Board 
	 * @param ArrayList
	 * @return the list of 25 codenames to be used on the board
	 */
	public ArrayList<String> listOf25CodeNames(ArrayList<String> ArrayList) {
		Integer Index;
		Integer OverFlowPrevention = ArrayList.size();
		String codeNames;
		SecureRandom Random;
		Integer Increment = 0;

		ArrayList<String> codeNamesList = new ArrayList<String>(25);

		while (Increment < 100) {
			Random = new SecureRandom();
			Index = Random.nextInt(OverFlowPrevention);
			codeNames = ArrayList.get(Index);

			if (!codeNamesList.contains(codeNames)) {
				codeNamesList.add(codeNames);
			}

			if (codeNamesList.size() == 25) {
				return codeNamesList;
			}
			Increment++;
		}

		return codeNamesList;
	}

	/**
	 * Finds out if the Assassin was revealed and if so then which team revealed the
	 * assassin
	 *
	 * @return a boolean value of whether the assassin was revealed by a team or
	 *         not.
	 */
	public Integer getAssassinStatus(ArrayList<Location> LocationsStatus) {

		Integer Increment = 0;
		String Asssassin = new String("Assassin");
		while (Increment < gameBoard.size()) {

			if (LocationsStatus.get(Increment).getOccupant().getAgentColor().equals(Asssassin) && LocationsStatus.get(Increment).getOccupantStatus() == true) {


				if (getBlueAgentStatus(LocationsStatus,Asssassin) == 1) {
					return 1;
				}

				if (getRedAgentStatus(LocationsStatus,Asssassin) == 1) {
					return 1;
				}
				if(threePlayerGame){
					if(getGreenAgentStatus(LocationsStatus,Asssassin) == 1){
						return 1;
					}
				}
			}
			Increment++;
		}

		return 0; 
	}

	/**
	 * @param codeNameBoard
	 * @param String codeNames
	 * Reveals a location with a given codename and then checks if the location was
	 * revealed by the blue team or the red team
	 */
	public Boolean getLocationStatus(ArrayList<Location> codeNameBoard, String codeNames) {
		Integer Increment = 0;
		String BA = "BlueAgent";
		String RA = "RedAgent";
		String GA = "GreenAgent";
		while (Increment < 25) {

			if (codeNameBoard.get(Increment).getOccupantStatus() == false && codeNameBoard.get(Increment).getCodename().equals(codeNames)) {

				codeNameBoard.get(Increment).getOccupantInfo();


				if (codeNameBoard.get(Increment).getOccupant().getAgentColor().equals(RA)) {
					RedAgentCount--;
				}

				if (codeNameBoard.get(Increment).getOccupant().getAgentColor().equals(BA)) {
					BlueAgentCount--;

				}
				if(threePlayerGame){
					if (codeNameBoard.get(Increment).getOccupant().getAgentColor().equals(GA)) {
						GreenAgentCount--;
					}
				}

				return true;

			} else if (codeNameBoard.get(Increment).getOccupantStatus() == true) {
			}
			Increment++;
		}

		return false;
	}

	/**
	 * @Info
	 * Method to check assassin information it will be called by getAgentStatus methods is correct or not
	 *
	 */
	public void assassinMethod(ArrayList<Location> gameBoardToCheckAssassin, String SquadColor){
		Integer i= 0;
		Integer AssassinCounter = 0;
		while (i < gameBoardToCheckAssassin.size()) {
			if (gameBoardToCheckAssassin.get(i).getOccupant().getAgentColor().equals(Assassin) && gameBoardToCheckAssassin.get(i).getOccupantStatus() == true) {
				AssassinCounter++;
			}
			i++;
		}
		if(AssassinCounter == 1){
			if(SquadColor == "Blue"){
				blueSquadStatus = "Lost";
				//changing turn
				if(greenSquadStatus == "Playing"){
					greenSquadTurn = true;
					redSquadTurn = false;
					blueSquadTurn = false;
					CodeNames.alertPanel.setText("Green Squad Make Your Guess");
				}
				//msg that blue is lost

				CodeNames.VerificationStatus.setText("Blue Squad Revealed An Assassin - Green Squad Go!");
				CodeNames.VerificationStatus.setForeground(Color.RED);
				JOptionPane.showMessageDialog(null, "First Assassin Was Chosen: Blue Squad is Out");

			}else if (SquadColor == "Red"){
				redSquadStatus = "Lost";
				//changing turn
				if(blueSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = false;
					blueSquadTurn = true;
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
				}

				CodeNames.VerificationStatus.setText("Red Squad Revealed An Assassin - Blue Squad Go!");
				CodeNames.VerificationStatus.setForeground(Color.RED);
				JOptionPane.showMessageDialog(null, "First Assassin Was Chosen: Red Squad is Out");
				//msg that red is lost
			}else if(SquadColor == "Green"){
				greenSquadStatus = "Lost";
				//changing turn
				if(redSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = true;
					blueSquadTurn = false;
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
				}

				CodeNames.VerificationStatus.setText("Green Squad Revealed An Assassin - Red Squad Go!");
				CodeNames.VerificationStatus.setForeground(Color.RED);
				JOptionPane.showMessageDialog(null, "First Assassin Was Chosen: Green Squad is Out");
				//msg that green is lost
			}
		}else if(AssassinCounter == 2){
			if(SquadColor == "Blue"){
				blueSquadStatus = "Lost";
				if(redSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Red Squad Victory");
					CodeNames.VerificationStatus.setText("Blue and Green Revealed Assassins : Red Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Blue Squad is Out. Start A New Game");
					//redSquad Wins
				}else if(greenSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Green Squad Victory");
					CodeNames.VerificationStatus.setText("Red and Blue Revealed Assassins : Green Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Blue Squad is Out. Start A New Game");
					//greenSquad Wins
				}
			}else if (SquadColor == "Red"){
				redSquadStatus = "Lost";
				if(blueSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Blue Squad Victory");
					CodeNames.VerificationStatus.setText("Red and Green Revealed Assassins : Blue Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Red Squad is Out. Start A New Game");
					//blueSquad Wins
				}else if(greenSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Green Squad Victory");
					CodeNames.VerificationStatus.setText("Red and Blue Revealed Assassins : Green Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Red Squad is Out. Start A New Game");
					//greenSquad Wins
				}
			}else if(SquadColor == "Green"){
				greenSquadStatus = "Lost";
				if(redSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Red Squad Victory");
					CodeNames.VerificationStatus.setText("Blue and Green Revealed Assassins : Red Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Green Squad is Out. Start A New Game");
					//redSquad Wins
				}else if(blueSquadStatus == "Playing"){
					CodeNames.alertPanel.setText("Blue Squad Victory");
					CodeNames.VerificationStatus.setText("Red and Green Revealed Assassins : Blue Squad Congrats On The Win");
					CodeNames.VerificationStatus.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Second Assassin Was Chosen: Green Squad is Out. Start A New Game");
					/**
					 * blueSquad Wins
					 */
				}
			}
		}
	}

	/**@Info
	 * Checks to see if the guess given by the blue team is correct or not
	 */
	public Integer getBlueAgentStatus(ArrayList<Location> LocationStatus, String Entry) {
		Integer Increment = 0;
		String RA = "RedAgent";
		String BA = "BlueAgent";
		String GA = "GreenAgent";
		String IB = "Innocent Bystander";
		String EMPTYSTRING = "";
		String CorrectAgent = "Correct Guess One Step Closer Blue Squad - Blue Squad GO!";
		String WrongAgentSelected = "You Fool You Contacted A RedAgent - Red Squad GO!";
		String InnocentCasualty = "You Monster You Murdered An Innocent Bystander - Red Squad GO!";

		if(!threePlayerGame){
			while (Increment < gameBoard.size()) {
				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(BA)) {
					CodeNames.count --;
					CodeNames.VerificationStatus.setText(CorrectAgent);
					CodeNames.VerificationStatus.setForeground(Color.getHSBColor((float)114.64,(float) 50.91,(float) 43.14));
					return 1;
				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(RA)) {
					TrackingNumber ++;
					CodeNames.VerificationStatus
					.setText(WrongAgentSelected);
					CodeNames.VerificationStatus.setForeground(Color.RED);
					SpyMaster.textEntryOne.setText(EMPTYSTRING);
					SpyMaster.textEntryTwo.setText(EMPTYSTRING);
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
					return 1;
				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(Assassin)) {
					TrackingNumber++;
					CodeNames.alertPanel.setText("Game Over Red Squad Wins");
					return 1;
				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(IB)) {
					TrackingNumber++;
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.VerificationStatus.setText(InnocentCasualty);
					CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad's SpyMaster");

					return 1;
				}

				if (CodeNames.count == 0) {
					TrackingNumber++;
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad's SpyMaster");
					CodeNames.VerificationStatus.setText("Blue Squad's You Used UP The Count ! -- Red Squad's Turn now.");
					CodeNames.VerificationStatus.setForeground(Color.ORANGE);

				}
				Increment++;
			}
		}else if(threePlayerGame){
			while (Increment < gameBoard.size()) {
				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(BA)) {
					CodeNames.count --;
					CodeNames.VerificationStatus.setText(CorrectAgent);
					CodeNames.VerificationStatus.setForeground(Color.getHSBColor((float)114.64,(float) 50.91,(float) 43.14));
					return 1;
				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(RA)) {
					//now the turn calculation work should be done here
					if(greenSquadStatus == "Playing"){
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Red Agent - Green Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = false;
						redSquadTurn = true;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Red Agent - Red Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Red Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
						return 1;
					}

				}
				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(GA)) {
					//now the turn calculation work should be done here
					if(greenSquadStatus == "Playing"){
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Green Agent - Green Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = false;
						redSquadTurn = true;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Green Agent - Red Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Red Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
						return 1;
					}

				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(Assassin)) {


					assassinMethod(LocationStatus, "Blue");

					return 1;
				}

				if (LocationStatus.get(Increment).getCodename().equals(Entry)
						&& LocationStatus.get(Increment).getOccupant().getAgentColor().equals(IB)) {
					if(greenSquadStatus == "Playing"){
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;

						SpyMaster.textEntryOne.setText("");
						SpyMaster.textEntryTwo.setText("");
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Green Squad GO!");
						CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = false;
						redSquadTurn = true;
						blueSquadTurn = false;

						SpyMaster.textEntryOne.setText("");
						SpyMaster.textEntryTwo.setText("");
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Red Squad GO!");
						CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
						CodeNames.alertPanel.setText("Red Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Red Squad's SpyMaster");
						return 1;
					}

				}

				if (CodeNames.count == 0) {
					if(greenSquadStatus == "Playing"){
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;

						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad's SpyMaster");
						CodeNames.VerificationStatus.setText("Blue Squad's You Used UP The Count ! -- Green Squad's Turn now.");
						CodeNames.VerificationStatus.setForeground(Color.ORANGE);
					}else{
						greenSquadTurn = false;
						redSquadTurn = true;
						blueSquadTurn = false;

						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Red Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Red Squad's SpyMaster");
						CodeNames.VerificationStatus.setText("Blue Squad's You Used UP The Count ! -- Red Squad's Turn now.");
						CodeNames.VerificationStatus.setForeground(Color.ORANGE);
					}
				}
				Increment++;
			}
		}
		return 0;
	}

	/**
	 * @Info
	 * Checks to see if the guess given by the red team is correct or not
	 *
	 */
	public Integer getRedAgentStatus(ArrayList<Location> GUIBoard, String Entry) {
		Integer Increment = 0;
		String RA = "RedAgent";
		String BA = "BlueAgent";
		String GA = "GreenAgent";
		String IB = "Innocent Bystander";
		String EMPTYSTRING = "";
		String CorrectAgent = "Correct Guess One Step Closer Red Squad - Red Squad GO!";
		String WrongAgentSelected = "You Fool You Contacted A RedAgent - Red Squad GO!";
		String InnocentCasualty = "You Monster You Murdered An Innocent Bystander - Red Squad GO!";

		if(!threePlayerGame){
			while (Increment < gameBoard.size()) {
				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(RA)) {
					CodeNames.count--;
					CodeNames.VerificationStatus.setText(CorrectAgent);
					CodeNames.VerificationStatus.setForeground(Color.green);
					return 1;
				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(BA)) {
					CodeNames.VerificationStatus
					.setText(WrongAgentSelected);
					CodeNames.VerificationStatus.setForeground(Color.RED);
					TrackingNumber++;
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
					return 1;
				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(Assassin)) {
					TrackingNumber++;

					CodeNames.alertPanel.setText("Game Over Blue Squad");

					return 1;
				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(IB)) {
					TrackingNumber++;
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");

					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);

					CodeNames.VerificationStatus.setText(InnocentCasualty);
					CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad's SpyMaster");

					return 1;
				}

				if (CodeNames.count == 0) {
					Board.TrackingNumber++;
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.VerificationStatus.setText("Red Squad Guesses Used up! -- Blue Squad's Turn now.");
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad's SpyMaster");
					CodeNames.VerificationStatus.setForeground(Color.CYAN);

				}
				Increment++;
			}
		}else if(threePlayerGame){
			while (Increment < gameBoard.size()) {
				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(RA)) {
					CodeNames.count--;
					CodeNames.VerificationStatus.setText(CorrectAgent);
					CodeNames.VerificationStatus.setForeground(Color.green);
					return 1;
				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(GA)) {
					//now the turn calculation work should be done here
					if(blueSquadStatus == "Playing"){
						greenSquadTurn = false;
						redSquadTurn = false;
						blueSquadTurn = true;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Green Agent - Blue Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Green Agent - Green Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad SpyMaster");
						return 1;
					}

				}
				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(BA)) {
					//now the turn calculation work should be done here
					if(blueSquadStatus == "Playing"){
						greenSquadTurn = false;
						redSquadTurn = false;
						blueSquadTurn = true;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Blue Agent - Blue Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;
						CodeNames.VerificationStatus.setText("You Fool You Contacted a Blue Agent - Green Squad Go");
						CodeNames.VerificationStatus.setForeground(Color.RED);
						SpyMaster.textEntryOne.setText(EMPTYSTRING);
						SpyMaster.textEntryTwo.setText(EMPTYSTRING);
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad SpyMaster");
						return 1;
					}

				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(Assassin)) {

					assassinMethod(GUIBoard, "Red");

					return 1;
				}

				if (GUIBoard.get(Increment).getCodename().equals(Entry)
						&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(IB)) {
					if(blueSquadStatus == "Playing"){
						greenSquadTurn = false;
						redSquadTurn = false;
						blueSquadTurn = true;

						SpyMaster.textEntryOne.setText("");
						SpyMaster.textEntryTwo.setText("");
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Blue Squad GO!");
						CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
						CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
						return 1;
					}else{
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;

						SpyMaster.textEntryOne.setText("");
						SpyMaster.textEntryTwo.setText("");
						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Green Squad GO!");
						CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad's SpyMaster");
						return 1;
					}

				}

				if (CodeNames.count == 0) {
					if(blueSquadStatus == "Playing"){
						greenSquadTurn = false;
						redSquadTurn = false;
						blueSquadTurn = true;

						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Blue Squad's SpyMaster");
						CodeNames.VerificationStatus.setText("Red Squad's You Used UP The Count ! -- Blue Squad's Turn now.");
						CodeNames.VerificationStatus.setForeground(Color.ORANGE);
					}else{
						greenSquadTurn = true;
						redSquadTurn = false;
						blueSquadTurn = false;

						CodeNames.clueLabel.setText("Clue: " + emptyString);
						CodeNames.countLabel.setText("   Count: " + emptyString);
						CodeNames.alertPanel.setText("Green Squad Make Your Guess");
						SpyMaster.spyMasterStatus.setText("Green Squad's SpyMaster");
						CodeNames.VerificationStatus.setText("Red Squad's You Used UP The Count ! -- Green Squad's Turn now.");
						CodeNames.VerificationStatus.setForeground(Color.ORANGE);
					}
				}
				Increment++;
			}
		}
		return 0;
	}

	/**
	 * @Info
	 * Checks to see if the guess given by the green team is correct or not
	 * Takes in The Location on the Board and a related String  
	 *
	 */
	public Integer getGreenAgentStatus(ArrayList<Location> GUIBoard, String Entry) {

		String RA = "RedAgent";
		String BA = "BlueAgent";
		String GA = "GreenAgent";
		String IB = "Innocent Bystander";
		String CorrectAgent = "Correct Guess One Step Closer Green Squad - Green Squad GO!";
		Integer Increment = new Integer(0);

		while (Increment < gameBoard.size()) {
			if (GUIBoard.get(Increment).getCodename().equals(Entry)
					&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(GA)) {
				CodeNames.count--;
				CodeNames.VerificationStatus.setText(CorrectAgent);
				CodeNames.VerificationStatus.setForeground(Color.green);
				return 1;
			}

			if (GUIBoard.get(Increment).getCodename().equals(Entry)
					&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(RA)) {
				//now the turn calculation work should be done here
				if(redSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = true;
					blueSquadTurn = false;
					CodeNames.VerificationStatus.setText("You Fool You Contacted a Red Agent - Red Squad Go");
					CodeNames.VerificationStatus.setForeground(Color.RED);
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
					return 1;
				}else{
					greenSquadTurn = false;
					redSquadTurn = false;
					blueSquadTurn = true;
					CodeNames.VerificationStatus.setText("You Fool You Contacted a Red Agent - Blue Squad Go");
					CodeNames.VerificationStatus.setForeground(Color.RED);
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
					return 1;
				}

			}
			if (GUIBoard.get(Increment).getCodename().equals(Entry)
					&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(BA)) {
				//now the turn calculation work should be done here
				if(redSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = true;
					blueSquadTurn = false;
					CodeNames.VerificationStatus.setText("You Fool You Contacted a Blue Agent - Red Squad Go");
					CodeNames.VerificationStatus.setForeground(Color.RED);
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
					return 1;
				}else{
					greenSquadTurn = false;
					redSquadTurn = false;
					blueSquadTurn = true;
					CodeNames.VerificationStatus.setText("You Fool You Contacted a Blue Agent - Blue Squad Go");
					CodeNames.VerificationStatus.setForeground(Color.RED);
					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad SpyMaster");
					return 1;
				}

			}

			if (GUIBoard.get(Increment).getCodename().equals(Entry)
					&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(Assassin)) {

				assassinMethod(GUIBoard, "Green");

				return 1;
			}

			if (GUIBoard.get(Increment).getCodename().equals(Entry)
					&& GUIBoard.get(Increment).getOccupant().getAgentColor().equals(IB)) {
				if(redSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = true;
					blueSquadTurn = false;

					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Red Squad GO!");
					CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad SpyMaster");
					return 1;
				}else{
					greenSquadTurn = false;
					redSquadTurn = false;
					blueSquadTurn = true;

					SpyMaster.textEntryOne.setText("");
					SpyMaster.textEntryTwo.setText("");
					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.VerificationStatus.setText("You Monster You Murdered An Innocent Bystander - Blue Squad GO!");
					CodeNames.VerificationStatus.setForeground(Color.MAGENTA);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad's SpyMaster");
					return 1;
				}

			}

			if (CodeNames.count == 0) {
				if(redSquadStatus == "Playing"){
					greenSquadTurn = false;
					redSquadTurn = true;
					blueSquadTurn = false;

					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Red Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Red Squad's SpyMaster");
					CodeNames.VerificationStatus.setText("Green Squad's You Used UP The Count ! -- Red Squad's Turn now.");
					CodeNames.VerificationStatus.setForeground(Color.ORANGE);
				}else{
					greenSquadTurn = false;
					redSquadTurn = false;
					blueSquadTurn = true;

					CodeNames.clueLabel.setText("Clue: " + emptyString);
					CodeNames.countLabel.setText("   Count: " + emptyString);
					CodeNames.alertPanel.setText("Blue Squad Make Your Guess");
					SpyMaster.spyMasterStatus.setText("Blue Squad's SpyMaster");
					CodeNames.VerificationStatus.setText("Green Squad's You Used UP The Count ! -- Blue Squad's Turn now.");
					CodeNames.VerificationStatus.setForeground(Color.ORANGE);
				}
			}
			Increment++;
		}
		return 0;
	}

	/**
	 * Shows if the gameBoard is in any of the possbile winning states
	 * 
	 * @return If the gameBoard has achieved any of the winning states
	 */
	public Boolean getVictoryFormation(ArrayList<Location> codeNameBoard) {
		Integer BlueCounter = 0;
		Integer RedCounter = 0;
		Integer GreenCounter = 0;
		Integer AssassinCounter = 0;
		Integer Index = 0;
		String  Assassin = "Assassin";
		String BA = "BlueAgent";
		String RA = "RedAgent";
		String GA = "GreenAgent";
		if(!threePlayerGame){
			while (Index < gameBoard.size()) {

				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(Assassin)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					AssassinCounter++;
					if (AssassinCounter.equals(1)) {
						JOptionPane.showMessageDialog(null, "An Assassin Was Chosen : Start a New Game");
						CodeNames.VerificationStatus.setText("That's a Tough Loss, RedSquad Loses");
						CodeNames.alertPanel.setText("Red Squad Has Lost ");
						CodeNames.VerificationStatus.setForeground(Color.RED);
					} else {
						JOptionPane.showMessageDialog(null, "An Assassin Was Chosen : Start a New Game");
						CodeNames.alertPanel.setText("Blue Squad Has Lost");
					}
				}

				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(BA)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					BlueCounter++;
					if (BlueCounter.equals(8)) {
						CodeNames.VerificationStatus.setText("All Blue Agents Revealed : Congrats On The Win");
						CodeNames.alertPanel.setText("Blue Squad Victory");
						CodeNames.VerificationStatus.setForeground(Color.GREEN);
						return true;
					}
				}

				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(RA)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					RedCounter++;
					if (RedCounter.equals(9)) {
						CodeNames.VerificationStatus.setText("All Red Agents Reveled : Congrats On The Win");
						CodeNames.alertPanel.setText("Red Squad Victory");
						CodeNames.VerificationStatus.setForeground(Color.GREEN);
						return true;
					}

				}
				Index++;
			}
		}else if(threePlayerGame){
			while (Index < gameBoard.size()) {
				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(BA)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					BlueCounter++;
				}

				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(RA)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					RedCounter++;
				}

				if (codeNameBoard.get(Index).getOccupant().getAgentColor().equals(GA)
						&& codeNameBoard.get(Index).getOccupantStatus() == true) {
					GreenCounter++;
				}

				Index++;
			}

			
			if (BlueCounter.equals(5) && blueSquadStatus == "Playing") {
				CodeNames.VerificationStatus.setText("All Blue Agents Revealed : Congrats On The Win");
				CodeNames.alertPanel.setText("Blue Squad Victory");
				CodeNames.VerificationStatus.setForeground(Color.GREEN);
				return true;
			}
			else if (RedCounter.equals(6) && redSquadStatus == "Playing") {
				CodeNames.VerificationStatus.setText("All Red Agents Reveled : Congrats On The Win");
				CodeNames.alertPanel.setText("Red Squad Victory");
				CodeNames.VerificationStatus.setForeground(Color.GREEN);
				return true;
			}
			else if (GreenCounter.equals(5) && greenSquadStatus == "Playing") {
				CodeNames.VerificationStatus.setText("All Green Agents Reveled : Congrats On The Win");
				CodeNames.alertPanel.setText("Green Squad Victory");
				CodeNames.VerificationStatus.setForeground(Color.GREEN);
				return true;
			}
		}
		return false;
	}

	/**
	 *	@Info Checks if the EntryClue given is legal or illegal given that a EntryClue is legal if
	 * the location is revealed and that the the EntryClue is the same as the codename at
	 * that given location
	 *
	 */
	public Boolean checkClue( ArrayList<Location> codeNameBoard, String EntryClue) {
		Integer countTracker = 1;
		Integer Increment = 0;
		while (Increment < codeNameBoard.size()) {

			if (codeNameBoard.get(Increment).getOccupantStatus() == true   && codeNameBoard.get(Increment).getCodename().equals(EntryClue)) {
				return true;
			}

			if ( codeNameBoard.get(Increment).getOccupantStatus() == false   && codeNameBoard.get(Increment).getCodename().equals(EntryClue) ) {
				TrackingNumber++;
				return false;
			}

			if (!codeNameBoard.get(Increment).getCodename().equals(EntryClue)) {
				countTracker++;
				if (countTracker.equals(25)) {
					return true;
				}
			}
			Increment++;
		}
		return false;
	}

	/**
	 * @Info
	 *
	 * Changes turn between red team and blue team
	 */
	public Boolean getTurn(ArrayList<Location> gameBoard, String entry) {

		if(!threePlayerGame){
			if ((TrackingNumber & 1) == 0) {
				getRedAgentStatus(gameBoard, entry);
				return true;
			}

			else {
				getBlueAgentStatus(gameBoard, entry);
				return true;
			}
		}else if(threePlayerGame){
			if(redSquadTurn){
				getRedAgentStatus(gameBoard, entry);
				return true;
			}else if(greenSquadTurn){
				getGreenAgentStatus(gameBoard, entry);
				return true;
			}else if(blueSquadTurn){
				getBlueAgentStatus(gameBoard, entry);
				return true;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}

	/**
	 * 
	 * @return currentTurn used to initially track team during the game / Derelict
	 */
	public Integer currentTurn() {
		if (TrackingNumber % 2 == 0) {
			return 0;
		}else {
			return 1;
		}
	}

	/**
	 * 
	 * @param AssassinCounter
	 * @return
	 */
	public String winningSquadAfterSecondAssassinReveal(Integer AssassinCounter){
		Integer i= 0;

		if(AssassinCounter == 2){
			if(redSquadStatus == "Playing" && greenSquadStatus == "Lost" && blueSquadStatus == "Lost"){
				return "red";
			}else if(greenSquadStatus == "Playing" && redSquadStatus == "Lost" && blueSquadStatus == "Lost"){
				return "green";
			}else if(blueSquadStatus == "Playing" && greenSquadStatus == "Lost" && redSquadStatus == "Lost"){
				return "blue";
			}
		}
		return "no one wins";
	}
	
	/**
	 * @Info Correctly gives the name of the current turn's team
	 * @returns returns the current turn's team
	 */
	public String SquadTurn(){
		if(redSquadTurn){
			return "red";
		}else if(greenSquadTurn){
			return "green";
		}else if(blueSquadTurn){
			return "blue";
		}else{
			return "nothing";
		}
	}

}	class ListCreator {

	/**
	 * Inner Class List Creators used to generate random assingments
	 * @param codeNames
	 * @param gameBoard
	 * @param threePlayerGame
	 * @return
	 */
	public static ArrayList<Location> randomGeneratedAssignments(
		ArrayList<String> codeNames, ArrayList<Location> gameBoard, Boolean threePlayerGame) {
		Person Object;
		Integer Increment = 0;
		Integer I1 = 0; Integer I2 = 0; Integer I3 = 0; Integer I4 = 0;Integer I5 = 0;
		ArrayList<Person> GeneratedAssingments = new ArrayList<Person>(25);
		if(!threePlayerGame){
			while(I1 < 9) {
				GeneratedAssingments.add(new RedAgent());
				I1++;
			}

			while (I2 < 8) {
				GeneratedAssingments.add(new BlueAgent());
				I2++;
			}
			while(I3 < 7) {
				GeneratedAssingments.add(new InnocentBystander());
				I3++;
			}
			while(I4 < 1) {
				GeneratedAssingments.add(new Assassin());
				I4++;
			}
		}else if(threePlayerGame){
			while(I1 < 6) {
				GeneratedAssingments.add(new RedAgent());
				I1++;
			}

			while (I2 < 5) {
				GeneratedAssingments.add(new BlueAgent());
				I2++;
			}
			while (I5 < 5) {
				GeneratedAssingments.add(new GreenAgent());
				I5++;
			}
			while(I3 < 7) {
				GeneratedAssingments.add(new InnocentBystander());
				I3++;
			}
			while(I4 < 2) {
				GeneratedAssingments.add(new Assassin());
				I4++;
			}
		}

		Collections.shuffle(GeneratedAssingments);
		Collections.shuffle(codeNames);

		while(Increment < 25) {
			Object = GeneratedAssingments.get(Increment);
			String entryString = codeNames.get(Increment);
			if (!gameBoard.get(Increment).isOccupied() && !gameBoard.get(Increment).hasCodeName()) {
				gameBoard.set(Increment, new Location(entryString, Object));
			}
			Increment++;
		}

		return gameBoard;
	}

}