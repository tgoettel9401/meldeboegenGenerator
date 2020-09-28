package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

@RestController
public class AverageRatingController {

	private final AverageRatingService averageRatingService;
	private final TeamService teamService;

	public AverageRatingController(AverageRatingService averageRatingService, TeamService teamService) {
		this.averageRatingService = averageRatingService;
		this.teamService = teamService;
	}
	
	@GetMapping("teams/{name}/potentialLineups")
	public List<PotentialLineupOfTeam> getPotentialLineupsOfTeam(@PathVariable("name") String name) throws TeamNotFoundException, ZeroPlayersShouldBeReducedException, InvalidLineupException {
		Optional<Team> team = teamService.findByName(name);
		if (team.isEmpty()) {
			throw new TeamNotFoundException(name);
		}
		else {
			List<PotentialLineupOfTeam> lineups = averageRatingService.generatePotentialLineupsOfTeam(team.get());
			return lineups;
		}
	}
	
	@GetMapping("getAverageRatings")
	public List<AverageRatingWithTeam> getAverageRatings() throws ZeroPlayersShouldBeReducedException, InvalidLineupException {
		List<Team> teams = teamService.findAll();
		List<AverageRatingWithTeam> averageRatings = averageRatingService.generateAverageRatingsWithTeams(teams);
		return averageRatings; 
	}

}
