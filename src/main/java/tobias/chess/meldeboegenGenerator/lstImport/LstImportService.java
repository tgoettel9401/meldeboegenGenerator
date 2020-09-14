package tobias.chess.meldeboegenGenerator.lstImport;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Lists;

import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.player.PlayerService;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

@Service
public class LstImportService {
	
	private final PlayerService playerService;
	private final TeamService teamService;
	
	public LstImportService(PlayerService playerService, TeamService teamService) {
		this.playerService = playerService; 
		this.teamService = teamService;
	}
	
	public List<Player> importLstEntries(MultipartFile lstImportFile) throws IOException {
		
		// Extract LstImportEntries from the importFile. 
		CsvMapper csvMapper = new CsvMapper(); 
		CsvSchema csvSchema = CsvSchema.builder()
				.setColumnSeparator(';')
				.addColumn("playerName")
				.addColumn("teamName")
				.addColumn("ignored1")
				.addColumn("eloRating")
				.addColumn("dwzRating")
				.addColumn("fideTitle")
				.addColumn("birthDay")
				.addColumn("ignored2")
				.addColumn("ignored3")
				.addColumn("ignored4")
				.addColumn("gender")
				.addColumn("ignored5")
				.addColumn("ignored6")
				.addColumn("ignored7")
				.addColumn("ignored8")
				.addColumn("ignored9")
				.addColumn("ignored10")
				.addColumn("ignored11")
				.addColumn("ignored12")
				.addColumn("ignored13")
				.addColumn("ignored14")
				.build();
		List<LstImportEntry> csvEntries = csvMapper
				.readerFor(LstImportEntry.class)
				.with(csvSchema)
				.<LstImportEntry>readValues(lstImportFile.getInputStream())
				.readAll();
		
		// Generate and import Players
		List<Player> importedPlayers = generatePlayersFromLstImportEntries(csvEntries);
		
		return importedPlayers;
	}
	
	private List<Player> generatePlayersFromLstImportEntries(List<LstImportEntry> lstImportEntries) {
		
		// Delete all Players and Teams.
		playerService.deleteAll();
		teamService.deleteAll();
		
		// Initialize Teams and Players. 
		List<Player> players = Lists.newArrayList(); 
		
		// Generate Player for every Entry and add to the list of players. If a new team has to be generated, create the new team. 
		for (LstImportEntry lstImportEntry : lstImportEntries) {
			
			// First find or create the team. 
			Team team = teamService.findFirstOrCreate(lstImportEntry.getTeamName());
			
			// Now create the Player.
			Player player = playerService.createForTeamAndLstImportEntry(team, lstImportEntry);
			
			// Add the player to the PlayerList. 
			players.add(player);
			
		}
		
		return players;
		
	}

}