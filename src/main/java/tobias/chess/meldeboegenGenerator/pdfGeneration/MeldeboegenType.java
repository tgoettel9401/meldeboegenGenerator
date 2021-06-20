package tobias.chess.meldeboegenGenerator.pdfGeneration;

import lombok.Getter;

@Getter
public enum MeldeboegenType {
	ANREISE_DLM("Meldeboegen-Anreise.pdf", "meldebogen-dlm-anreise.jrxml"),
	ROUND_DLM("Meldeboegen-Runde.pdf", "meldebogen-dlm-runde.jrxml"),
	ANREISE_DVM("Meldeboegen-DVM-Anreise.pdf", "meldebogen-dvm-anreise.jrxml"),
	ROUND_DVM("Meldeboegen-DVM-Runde.pdf", "meldebogen-dvm-runde.jrxml");
	
	private String filename;
	private String templateFilename;
	
	MeldeboegenType(String filename, String templateFilename) {
		this.filename = filename; 
		this.templateFilename = templateFilename;
	}
}
