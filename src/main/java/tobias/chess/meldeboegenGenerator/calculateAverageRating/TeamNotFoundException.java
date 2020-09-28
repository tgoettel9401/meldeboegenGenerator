package tobias.chess.meldeboegenGenerator.calculateAverageRating;

public class TeamNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TeamNotFoundException() {
        super();
    }

    public TeamNotFoundException(String teamName) {
        super("Team with name " + teamName + " does not exist!");
    }

}
