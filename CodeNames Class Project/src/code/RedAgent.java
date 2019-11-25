package code;

public class RedAgent extends Person {
	
	/*
	 * The RedAgent class extends the Person class
	 * 9 RedAgents must be selected for the game to end!
	 */
	private String personTeam;
	
	public RedAgent() {
		super("RedAgent");
		personTeam = "RedAgent";
	}
	

	// gets the String associated with the red agent
	@Override
	
	public String getAgentColor() {
		return personTeam;
	}
	
}
