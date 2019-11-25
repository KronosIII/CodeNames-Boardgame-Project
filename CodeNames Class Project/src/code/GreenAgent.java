package code;
/*	Green Agents only Appear during the Three Player Mode.
 * 	The GreenAgent class extends the Person class
 * 	5 Green Agents must be for the game to end!
 */
public class GreenAgent extends Person {
	
	// A String representing the Green Agent Object's Team
	private String personTeam;
	
	public GreenAgent() {
		super("GreenAgent");
		personTeam = "GreenAgent";
	}
	
	// Gets the String associated with the Green Agent
	@Override
	public String getAgentColor() {
		return personTeam;
	}
	
}
