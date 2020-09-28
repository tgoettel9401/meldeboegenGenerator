package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.Set;

import org.springframework.stereotype.Service;

import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

@Service
public class PotentialLineupService {
	
	public static PotentialLineupOfTeam generateLineupOfTeam(Team team, Set<Player> players) {
		PotentialLineupOfTeam lineup = new PotentialLineupOfTeam();
		lineup.setLineupNumber(1);
		lineup.setPlayers(players);
		lineup.setTeam(team);
		return lineup;
	}

}
