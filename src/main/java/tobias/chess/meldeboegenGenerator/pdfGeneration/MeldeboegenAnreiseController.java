package tobias.chess.meldeboegenGenerator.pdfGeneration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;

@RestController
public class MeldeboegenAnreiseController {
	
	private final MeldeboegenAnreiseGenerator pdfGenerator;
	
	public MeldeboegenAnreiseController(MeldeboegenAnreiseGenerator pdfGenerator) {
		this.pdfGenerator = pdfGenerator;
	}
	
	@GetMapping("generateAnreiseBogen")
	public void generateAnreiseBogen() throws JRException {
		pdfGenerator.generateReport();
	}

}
