package tobias.chess.meldeboegenGenerator.player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.stereotype.Service;

import tobias.chess.meldeboegenGenerator.lstImport.LstImportEntry;
import tobias.chess.meldeboegenGenerator.team.Team;

@Service
public class PlayerService {
	
	private PlayerRepository playerRepository;
	
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	public Player createForTeamAndLstImportEntry(Team team, LstImportEntry lstImportEntry) {
		
		Player newPlayer = new Player();
		newPlayer.setTeam(team);
		newPlayer.setName(lstImportEntry.getPlayerName());
		newPlayer.setEloRating(lstImportEntry.getEloRating() != null ? lstImportEntry.getEloRating() : 0);
		newPlayer.setDwzRating(lstImportEntry.getDwzRating() != null ? lstImportEntry.getDwzRating() : 0);
		newPlayer.setFideTitle(lstImportEntry.getFideTitle());
		newPlayer.setGender(Gender.getFromString(lstImportEntry.getGender()));
		
		// Convert birthDay in String (German format, e.g. 01.02.2020 for 1st February 2020)
		newPlayer.setBirthDay(convertLstImportBirthdayToLocalDate(lstImportEntry.getBirthDay()));		
		
		return playerRepository.save(newPlayer);
		
	}
	
	public void deleteAll() {
		this.playerRepository.deleteAll();
	}
	
	private LocalDate convertLstImportBirthdayToLocalDate(String lstImportBirthday) {

		String trimmedBirthdayString = lstImportBirthday.trim();
		
		// Generate DateTimeFormatter for German format. 
		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(
		        FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		
		// The birthday may be 00.00.year, hence replace this with 01.01.
		if (trimmedBirthdayString.startsWith("00.00")) {
			trimmedBirthdayString= trimmedBirthdayString.replace("00.00", "01.01");
		}
		if (trimmedBirthdayString.length() == 4) {
			trimmedBirthdayString = "01.01." + trimmedBirthdayString;
		}
		if (trimmedBirthdayString.length() == 0) {
			trimmedBirthdayString = "01.01." + LocalDate.now().getYear();
		}
		return LocalDate.parse(trimmedBirthdayString, germanFormatter);
	}

}
