package code;

public abstract class Person {
	
	/**
	 * This String represents what type of person the instance is
	 */
	
	private String personType;
	
	/**
	 * This @Boolean represents the status of the revealed person
	 */
	private Boolean revealedStatus;
	
	public Person(String string) {
		revealedStatus = false;
	}


	public void setRevealed() {
		revealedStatus = true;
	}
	

	public void setNotRevealed() {
		revealedStatus = false;
	}
	

	public Boolean getReveal() {
		return revealedStatus;
	}
	
	public abstract String getAgentColor();
	
	/**
	 * Returns what type of person the Person is
	 * @return the person's type
	 */
	public String getPersonType() {
		return personType;
	}
	
	/**
	 * Checks if the person is a Red Agent
	 * @return a boolean checking whether or not the person is a Red Agent
	 */
	public boolean isRedAgent() {
		return personType.equals("RedAgent");
	}

	/**
	 * Checks if the person is a Blue Agent
	 * @return a boolean checking whether or not the person is a Blue Agent
	 */
	public boolean isBlueAgent() {
		return personType.equals("BlueAgent");
	}
	
	/**
	 * Checks if the person is a Green Agent
	 * @return a boolean checking whether or not the person is a Green Agent
	 */
	public boolean isGreenAgent() {
		return personType.equals("GreenAgent");
	}
	
	/**
	 * Checks if the person is a Innocent Bystander
	 * @return a boolean checking whether or not the person is a Innocent Bystander
	 */
	public boolean isInnocentBystander() {
		return personType.equals("InnocentBystander");
	}
    
	
	/**
	 * Checks if the person is an Assassin
	 * @return a boolean checking whether or not the person is an Assassin 
	 */
	public boolean isAssassin() {
		return personType.equals("Assassin");
	}

	
	
}
