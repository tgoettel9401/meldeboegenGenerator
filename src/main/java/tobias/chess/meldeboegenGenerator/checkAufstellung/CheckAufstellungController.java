package tobias.chess.meldeboegenGenerator.checkAufstellung;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckAufstellungController {
	
	private final CheckAufstellungService checkAufstellungService; 
	
	public CheckAufstellungController(CheckAufstellungService checkAufstellungService) {
		this.checkAufstellungService = checkAufstellungService;
	}
	
	@GetMapping("checkAufstellungen")
	public Map<String, CheckResponse> checkAufstellungen() {
		return checkAufstellungService.checkAufstellungen();
	}

}
