package tobias.chess.meldeboegenGenerator.frontend;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tobias.chess.meldeboegenGenerator.player.PlayerService;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

import java.util.List;

@RestController
@AllArgsConstructor
public class DataController {

    private final Logger logger = LoggerFactory.getLogger(DataController.class);

    private final TeamService teamService;
    private final PlayerService playerService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/getTeams")
    public List<Team> getTeamsWithPlayers() {
        logger.info("Received request for loading teams with players");
        return teamService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/deleteData")
    public void deleteData() {
        logger.info("Received request for deleting data");
        playerService.deleteAll();
        teamService.deleteAll();
    }

}
