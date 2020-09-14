package tobias.chess.meldeboegenGenerator.checkAufstellung;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class CheckResponse {
	
	List<DetailedCheckResponse> detailedCheckResponses = Lists.newArrayList();
	
	/**
	 * Checks if the response is an error-response.
	 * @return
	 */
	public boolean isError() {
		for (DetailedCheckResponse response : detailedCheckResponses) {
			if (response.isError()) {
				return true; 
			}
		}
		return false;
	}
	
	public void addDetailedCheckResponse(DetailedCheckResponse detailedResponseToAdd) {
		this.detailedCheckResponses.add(detailedResponseToAdd);
	}

}
