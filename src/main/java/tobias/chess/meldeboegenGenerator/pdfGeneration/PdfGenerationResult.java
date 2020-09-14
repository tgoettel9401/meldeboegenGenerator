package tobias.chess.meldeboegenGenerator.pdfGeneration;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class PdfGenerationResult {
	
	private String message; 
	private HttpStatus resultCode;

}
