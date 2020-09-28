package tobias.chess.meldeboegenGenerator.calculateAverageRating;

public class ZeroPlayersShouldBeReducedException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ZeroPlayersShouldBeReducedException() {
        super("It is not allowed to reduce players in a AgeGroup with only one or less players!");
    }

}
