package tobias.chess.meldeboegenGenerator.pdfGeneration;

import lombok.Getter;

@Getter
public enum MeldeboegenType {
	ANREISE ("Meldeboegen-Anreise.pdf", "meldebogen-anreise.jrxml"), ROUND ("Meldeboegen-Runde.pdf", "meldebogen-runde.jrxml");
	
	private String filename;
	private String templateFilename;
	
	MeldeboegenType(String filename, String templateFilename) {
		this.filename = filename; 
		this.templateFilename = templateFilename;
	}
}
