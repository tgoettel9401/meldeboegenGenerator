package tobias.chess.meldeboegenGenerator.pdfGeneration;

import lombok.Getter;

@Getter
public enum MeldeboegenType {
	ANREISE_DLM("Meldeboegen-Anreise.pdf", "meldebogen-anreise.jrxml"),
	ROUND_DLM("Meldeboegen-Runde.pdf", "meldebogen-runde.jrxml"),
	ANREISE_DVM("Meldeboegen-DVM-Anreise.pdf", "meldebogen-dvm-anreise.jrxml"),
	ROUND_DVM("Meldeboegen-Runde.pdf", "meldebogen-runde.jrxml");
	
	private String filename;
	private String templateFilename;
	
	MeldeboegenType(String filename, String templateFilename) {
		this.filename = filename; 
		this.templateFilename = templateFilename;
	}
}
