package tobias.chess.meldeboegenGenerator.player;

public enum Gender {
	MALE, FEMALE;
	
	public static Gender getFromString(String genderString) {
		
		if (genderString.equals("m")) {
			return MALE; 
		}
		
		else {
			return FEMALE;
		}
		
	}
}
