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
	public ResponseEntity<byte[]> generateAnreiseBogen() throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ANREISE);
		
		if (result.getResultCode().isError()) {
			System.out.println("Exporting ANREISE was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting ANREISE was successful");
		}
			
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ANREISE.getFilename() + "\"")
				.body(result.getFileByteArray());
	}
	
	@GetMapping("generateRundeBogen")
	public ResponseEntity<byte[]> generateRundeBogen() throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ROUND);
		
		if (result.getResultCode().isError()) {
			System.out.println("Exporting ANREISE was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting ANREISE was successful");
		}
			
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ROUND.getFilename() + "\"")
				.body(result.getFileByteArray());
	}

}
