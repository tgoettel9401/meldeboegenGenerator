package tobias.chess.meldeboegenGenerator.player;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import lombok.Getter;

@Getter
public enum AgeGroup {
	U20 (Lists.newArrayList()),
	U18 (Arrays.asList(U20)), 
	U16 (Arrays.asList(U18, U20)), 
	U14 (Arrays.asList(U16, U18, U20)), 
	U12 (Arrays.asList(U14, U16, U18, U20)),
	U20w (Arrays.asList(U20)), 
	U16w (Arrays.asList(U20w, U16, U18, U20)), 
	U12w (Arrays.asList(U16w, U20w, U12, U14, U16, U18, U20)), 
	ERROR (null);
	
	private List<AgeGroup> otherAllowedAgeGroups;
	
	AgeGroup(List<AgeGroup> otherAllowedAgeGroups) {
		this.otherAllowedAgeGroups = otherAllowedAgeGroups;
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
