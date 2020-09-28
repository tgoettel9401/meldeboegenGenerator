package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AverageRatingWithTeamIncludingPlayers extends AverageRatingWithTeam {
	private List<PlayerWithRating> relevantPlayers = Lists.newArrayList();
}
