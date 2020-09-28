package tobias.chess.meldeboegenGenerator.calculateAverageRating;

public class InvalidLineupException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidLineupException() {
		super("Lineup is invalid. This usually happens if the lineup contains more than 10 players or if the lineup does not fulfill the criteria.");
	}

}
