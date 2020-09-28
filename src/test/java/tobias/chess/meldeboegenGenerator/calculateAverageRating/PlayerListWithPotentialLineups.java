package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.List;

import lombok.Data;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

@Data
public class PlayerListWithPotentialLineups {
	private Team team; 
	private List<Player> players;
	private List<PotentialLineupOfTeam> potentialLineups;
}
