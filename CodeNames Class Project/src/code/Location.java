package code;

public class Location {

	private String codeName;
	private Person personObject;
	private boolean personStatus;

	public Location(String codename, Person person) {

		codeName = codename;
		personObject = person;
		personStatus = false;
	}

	/**
	 * @return if The Location Instance is Occupied by a String CodeName
	 */
	public Boolean hasCodeName() {
		if (codeName != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return if The Location Instance is Occupied by a Person
	 */
	public Boolean isOccupied() {
		if (personObject != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getCodename() {
		return codeName;
	}

	public Person getOccupant() {
		return personObject;
	}
	/**
	 * @info Gets The Status of the person to see if they are revealed or not
	 */
	public Boolean getOccupantStatus() {
		return personStatus;
	}

	public Boolean getOccupantInfo() {
		return personStatus = true;
	}



	public String getAgentsSquadColor() {
		return personObject.getAgentColor();
	}

}
