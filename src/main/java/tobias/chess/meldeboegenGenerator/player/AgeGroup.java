package tobias.chess.meldeboegenGenerator.player;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import lombok.Getter;

@Getter
public enum AgeGroup {
	U20 (Lists.newArrayList(), 8),
	U18 (Arrays.asList(U20), 7),
	U16 (Arrays.asList(U18, U20), 6),
	U14 (Arrays.asList(U16, U18, U20), 5),
	U12 (Arrays.asList(U14, U16, U18, U20), 4),
	U20w (Arrays.asList(U20), 3),
	U16w (Arrays.asList(U20w, U16, U18, U20), 2),
	U12w (Arrays.asList(U16w, U20w, U12, U14, U16, U18, U20), 1),
	ERROR (null, 0);
	
	private List<AgeGroup> otherAllowedAgeGroups;

	// The sorting key is being used for allocating while checking if all age groups are contained.
	private Integer sortingKey;
	
	AgeGroup(List<AgeGroup> otherAllowedAgeGroups, Integer sortingKey) {
		this.otherAllowedAgeGroups = otherAllowedAgeGroups;
		this.sortingKey = sortingKey;
	}
	
	public boolean isGroupAllowed(AgeGroup otherAgeGroup) {
		if (this.equals(otherAgeGroup) || this.getOtherAllowedAgeGroups().contains(otherAgeGroup)) {
			return true; 
		}
		else {
			return false;
		}
	}
	
}
