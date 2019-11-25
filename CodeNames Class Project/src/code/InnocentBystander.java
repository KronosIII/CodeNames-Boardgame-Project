package code;
/*
 * The Innocent Bystander class extends the Person class
 * If a Innocent Bystander is killed the players turn automatically ends
 */
public class InnocentBystander extends Person{

	/*
	 *  @ The string of the InnocentBystander object
	 */
	private String personTeam;

	public InnocentBystander() {
		super("Innocent Bystander");
		personTeam = "Innocent Bystander";
	}

	// Gets the String associated with the InnocentBystander
	@Override
	public String getAgentColor() {
		return personTeam;
	}
	
	
}

