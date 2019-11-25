package code;
/*
 * The Assassin class extends the Person class
 * Once an Assassin is selected the game ends!
 */
public class Assassin extends Person {
	private String getAssassin;
	/**
	 * Creates an Assassin person
	 */
	public Assassin() {
		super("Assassin");
		getAssassin = "Assassin"; 
	}
	public String getGetAssassin() {
		return getAssassin;
	}
	public void setGetAssassin(String getAssassin) {
		this.getAssassin = getAssassin;
	}
	@Override
	public String getAgentColor() {
		return getAssassin;
	}
}

