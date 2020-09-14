package tobias.chess.meldeboegenGenerator.checkAufstellung;

import lombok.Data;

@Data
public class DetailedCheckResponse {
	private CheckCriterium criterium;
	private CheckResponseStatus status;
	private String message;
	
	/**
	 * Checks if the response is an error-response.
	 * @return
	 */
	public boolean isError() {
		return this.getStatus().equals(CheckResponseStatus.ERROR);
	}
}
