package tobias.chess.meldeboegenGenerator.lstImport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tobias.chess.meldeboegenGenerator.player.Player;

@RestController
public class LstImportController {
	
	private final LstImportService lstImportService;
	
	public LstImportController(LstImportService lstImportService) {
		this.lstImportService = lstImportService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("importPlayersAndTeams")
	public ResponseEntity<List<Player>> importCsv(@RequestParam("file") MultipartFile csvFile) throws Exception {
		List<Player> importEntries = lstImportService.importLstEntries(csvFile);	
		return new ResponseEntity<>(importEntries, HttpStatus.OK);	
	}

}
