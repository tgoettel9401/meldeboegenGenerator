package tobias.chess.meldeboegenGenerator.player;

public enum Gender {
	MALE, FEMALE;
	
	public static Gender getFromString(String genderString) {
		return genderString.equalsIgnoreCase("W")
				? FEMALE
				: MALE;
	}
}
