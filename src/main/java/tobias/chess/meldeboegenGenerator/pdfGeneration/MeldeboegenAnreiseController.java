package tobias.chess.meldeboegenGenerator.pdfGeneration;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<PdfGenerationResult> generateAnreiseBogen() throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ANREISE);
		return new ResponseEntity<>(result, result.getResultCode());
	}
	
	@GetMapping("generateRundeBogen")
	public ResponseEntity<PdfGenerationResult> generateRundeBogen() throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ROUND);
		return new ResponseEntity<>(result, result.getResultCode());
	}

}
