package code;
/*
 * The BlueAgent class extends the Person class
 * 8 Blue Agents must be for the game to end!
 */
public class BlueAgent extends Person {
	
	/**
	 * Creates a Blue-Agent
	 */
	private String personTeam; 
	
	public BlueAgent() {
		super("BlueAgent");
		personTeam = "BlueAgent";
	}

	
	// Gets the String associated with the Blue Agent.
	@Override
	public String getAgentColor() {
		return personTeam;
	}
	
	
	
}
