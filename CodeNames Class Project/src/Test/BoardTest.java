package Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import code.Board;
import code.Location;



public class BoardTest {
	
	Board b = new Board(true);
	ArrayList<Location> game_board;
	
	@SuppressWarnings("static-access")
	public BoardTest() {
		game_board = new ArrayList<Location>();
		game_board = b.randomGeneratedAssignments(b.gameBoard,b.randomlySelectedCodeNames());
	}
	
	@Test
	public void testAssignments() {
		
		int blueagents = 0;
		int redagents = 0;
		int greenagents= 0;
		int innocentbystanders = 0;
		int assassincount = 0;
		for(Location game_board_1 : game_board){
			if(game_board_1.getOccupant().getAgentColor().equals("RedAgent")){
				redagents++;
			}else if(game_board_1.getOccupant().getAgentColor().equals("BlueAgent")){
				blueagents++;
			}else if(game_board_1.getOccupant().getAgentColor().equals("GreenAgent")){
				greenagents++;
			}else if(game_board_1.getOccupant().getAgentColor().equals("Innocent Bystander")){
				innocentbystanders++;
			}else if(game_board_1.getOccupant().getAgentColor().equals("Assassin")){
				assassincount++;
			}
		}
		assertEquals(5,blueagents);
		assertEquals(5,greenagents);
		assertEquals(6,redagents);
		assertEquals(7,innocentbystanders);
		assertEquals(2,assassincount);
	}

	@Test
	public void testWinningStates(){
		if(b.getVictoryFormation(game_board) == true){
			assertTrue(b.getVictoryFormation(game_board));
			
		}else if(b.getVictoryFormation(game_board) == false){
			assertFalse(b.getVictoryFormation(game_board));
		}
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testWinningTeamAfterSecondAssassinReveal(){
		
		b.blueSquadStatus = "Lost";
		b.redSquadStatus = "Playing";
		b.greenSquadStatus = "Lost";
		assertEquals("red",b.winningSquadAfterSecondAssassinReveal(2));
		
		b.blueSquadStatus = "Lost";
		b.redSquadStatus = "Lost";
		b.greenSquadStatus = "Playing";
		assertEquals("green",b.winningSquadAfterSecondAssassinReveal(2));
		
		b.blueSquadStatus = "Playing";
		b.redSquadStatus = "Lost";
		b.greenSquadStatus = "Lost";
		assertEquals("blue",b.winningSquadAfterSecondAssassinReveal(2));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testNextMoveTeam(){
		b.redSquadTurn = true;
		b.greenSquadTurn = false;
		b.blueSquadTurn = false;
		assertEquals("red", b.SquadTurn());
		
		b.redSquadTurn = false;
		b.greenSquadTurn = true;
		b.blueSquadTurn = false;
		assertEquals("green", b.SquadTurn());
		
		b.redSquadTurn = false;
		b.greenSquadTurn = false;
		b.blueSquadTurn = true;
		assertEquals("blue", b.SquadTurn());
	}
}
