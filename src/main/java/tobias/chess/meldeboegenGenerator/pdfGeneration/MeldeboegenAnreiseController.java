package tobias.chess.meldeboegenGenerator.pdfGeneration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ANREISE_DLM, "Deutsche Ländermeisterschaft 2021");
		
		if (result.getResultCode().isError()) {
			System.out.println("Exporting ANREISE was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting ANREISE was successful");
		}
			
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ANREISE_DLM.getFilename() + "\"")
				.body(result.getFileByteArray());
	}

	@GetMapping("generateAnreiseBogenDvm")
	public ResponseEntity<byte[]> generateAnreiseBogenDvm(@RequestParam("title") String title) throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ANREISE_DVM, title);

		if (result.getResultCode().isError()) {
			System.out.println("Exporting ANREISE (DVM) was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting ANREISE (DVM) was successful");
		}

		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ANREISE_DVM.getFilename() + "\"")
				.body(result.getFileByteArray());
	}
	
	@GetMapping("generateRundeBogen")
	public ResponseEntity<byte[]> generateRundeBogen() throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ROUND_DLM, "");
		
		if (result.getResultCode().isError()) {
			System.out.println("Exporting RUNDE was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting ANREISE was successful");
		}
			
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ROUND_DLM.getFilename() + "\"")
				.body(result.getFileByteArray());
	}

	@GetMapping("generateRundeBogenDvm")
	public ResponseEntity<byte[]> generateRundeBogenDvm(@RequestParam("ageGroup") String ageGroup) throws JRException {
		PdfGenerationResult result = pdfGenerator.generateReport(MeldeboegenType.ROUND_DVM, ageGroup);

		if (result.getResultCode().isError()) {
			System.out.println("Exporting RUNDE (Dvm) was not successful");
			System.out.println("Message is: " + result.getMessage());
		}
		else {
			System.out.println("Exporting RUNDE (Dvm) was successful");
		}

		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + MeldeboegenType.ROUND_DVM.getFilename() + "\"")
				.body(result.getFileByteArray());
	}

}
