package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Data;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

@Data
public class PotentialLineupOfTeam {
	private Team team; 
	private Integer lineupNumber;
	private Set<Player> players = Sets.newHashSet();
	
	@Override
	public String toString() {
		String toString = ""; 
		int counter = 1; 
		List<Player> playersSortedByName = Lists.newArrayList(players);
		playersSortedByName.sort(Comparator.comparing(Player::getName));
		for (Player player : playersSortedByName) {
			if (counter != 1) {
				toString = toString.concat(", ");
			}
			toString = toString.concat(player.getName());
			counter++;
		}
		return toString; 
	}

	public Double getAverageRating() {
		Integer ratingSum = 0;
		Integer playerNumber = 0;
		for (Player player : this.getPlayers()) {
			ratingSum += player.getDwzRating();
			playerNumber++;
		}
		Double averageRating = 1.0 * ratingSum / playerNumber;
		return averageRating;
	}

}
