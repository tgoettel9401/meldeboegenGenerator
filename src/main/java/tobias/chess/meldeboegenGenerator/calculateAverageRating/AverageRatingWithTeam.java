package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import lombok.Data;

@Data
public class AverageRatingWithTeam {
	
	private Integer teamNumber; 
	private String teamName; 
	private Double averageRating;

}
