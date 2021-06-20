package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import lombok.Data;
import tobias.chess.meldeboegenGenerator.player.AgeGroup;

@Data
public class AgeGroupWithNumberOfPlayers {

    private AgeGroup ageGroup;
    private Integer numberOfPlayers;

    public void reduceNumberOfPlayers() throws ZeroPlayersShouldBeReducedException {
        if (numberOfPlayers <= 1) {
            throw new ZeroPlayersShouldBeReducedException();
        }
        else {
            numberOfPlayers = numberOfPlayers - 1;
        }
    }

    public void increaseNumberOfPlayers() {
        numberOfPlayers = numberOfPlayers + 1;
    }

    public Integer getAgeGroupSortingKey() {
        return this.getAgeGroup().getSortingKey();
    }
}
