package tobias.chess.meldeboegenGenerator.player;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import tobias.chess.meldeboegenGenerator.team.Team;

@Entity
@Data
public class Player {
	
	@Id @GeneratedValue
	private Long id; 
	
	@ManyToOne
	private Team team; 
	
	private String name;
	private Integer eloRating = 0;
	private Integer dwzRating = 0;
	private String fideTitle;
	private LocalDate birthDay;
	private Gender gender;
	
	public AgeGroup getAgeGroup() {
		
		Integer currentYear = LocalDate.now().getYear();
		Integer birthYear = birthDay.getYear();
		Integer age = currentYear - birthYear;
		
		if (this.gender.equals(Gender.FEMALE)) {
			
			// For female players it exists U20w, U16w and U12w
			
			if (age > 16 && age <= 20) {
				return AgeGroup.U20w;
			}
			
			if (age > 12 && age <= 16) {
				return AgeGroup.U16w;
			}
			
			if (age <= 12) {
				return AgeGroup.U12w;
			}
			
		}
		
		else { // Gender is MALE
			
			// For male players it exists U20, U18, U16, U14, U12
			
			if (age > 18 && age <= 20) {
				return AgeGroup.U20;
			}
			
			if (age > 16 && age <= 18) {
				return AgeGroup.U18;
			}
			
			if (age > 14 && age <= 16) {
				return AgeGroup.U16;
			}
			
			if (age > 12 && age <= 14) {
				return AgeGroup.U14;
			}
			
			if (age <= 12) {
				return AgeGroup.U12;
			}
			
		}
		
		// If no ageGroup was returned yet, then we have a problem!
		System.out.println("Player " + this + " resulted in an Error-AgeGroup!");
		return AgeGroup.ERROR;
		
	}

}
