package tobias.chess.meldeboegenGenerator.checkAufstellung;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import tobias.chess.meldeboegenGenerator.player.AgeGroup;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

@Service
public class CheckAufstellungService {
	
	private final TeamService teamService; 
	
	private final List<AgeGroup> allAgeGroups = Arrays.asList(AgeGroup.U20, AgeGroup.U18, AgeGroup.U16, AgeGroup.U14, AgeGroup.U12, 
			AgeGroup.U20w, AgeGroup.U16w, AgeGroup.U12w);
	
	public CheckAufstellungService(TeamService teamService) {
		this.teamService = teamService;
	}
	
	public Map<String, CheckResponse> checkAufstellungen() {
		
		Map<String, CheckResponse> teamCheckMap = Maps.newHashMap();
		
		List<Team> teams = teamService.findAll();
		
		// Check Aufstellungen for every team. 
		for (Team team : teams) {
			CheckResponse checkForTeam = checkAufstellungForTeam(team);
		}
		
		return teamCheckMap;
	}
	
	private CheckResponse checkAufstellungForTeam(Team team) {

		// Check 200er-Border for every team. 
		DetailedCheckResponse dwz200Response = check200erBorder(team);
		
		// Check if all AgeGroups exist
		DetailedCheckResponse ageGroupResponse = checkAllAgeGroups(team);
		
		// Final CheckResponse
		CheckResponse finalResponse = new CheckResponse();
		finalResponse.addDetailedCheckResponse(dwz200Response);
		finalResponse.addDetailedCheckResponse(ageGroupResponse);
		
		return finalResponse;
	}
	
	private DetailedCheckResponse check200erBorder(Team team) {
		DetailedCheckResponse response = new DetailedCheckResponse();
		response.setCriterium(CheckCriterium.DWZ_200);
		
		// TODO: Make sure players are in order of Aufstellung!
		Player previousPlayer = null;
		for (Player player : team.getPlayers()) {
			
			if (previousPlayer != null) {
				Integer differenceBetweenPlayersInDWZ = previousPlayer.getDwzRating() - player.getDwzRating();
				if (differenceBetweenPlayersInDWZ < 200) {
					response.setStatus(CheckResponseStatus.ERROR);
					response.setMessage("Checking DWZ 200er Border has failed for player " + player.getName() + 
							" and previous player " + previousPlayer.getName());
					return response;
				}
			}
			
			previousPlayer = player;
		}
		
		response.setStatus(CheckResponseStatus.OK);
		response.setMessage("");
		return response;
	}
	
	private DetailedCheckResponse checkAllAgeGroups(Team team) {
		
		DetailedCheckResponse response = new DetailedCheckResponse();
		response.setCriterium(CheckCriterium.AGE_GROUPS);
		
		List<AgeGroup> ageGroups = Lists.newArrayList();
		Set<AgeGroup> ageGroupsDistinct = Sets.newHashSet();
		
		for (Player player : team.getPlayers()) {
			ageGroups.add(player.getAgeGroup());
			ageGroupsDistinct.add(player.getAgeGroup());
		}
		
		// First check if existing ageGroupsDistinct contain all AgeGroups. If that is the case, everything is fine. 
		if (ageGroupsDistinct.containsAll(allAgeGroups)) {
			response.setStatus(CheckResponseStatus.OK);
			response.setMessage("");
			return response;
		}
		
		// Not all groups are available, then check if any group with two entries is able to play in a different group. 
		List<AgeGroup> missingAgeGroups = Lists.newArrayList();
		for (AgeGroup ageGroup : allAgeGroups) {
			if (!ageGroupsDistinct.contains(ageGroup))
				missingAgeGroups.add(ageGroup);
		}
		
		// Find all ageGroups with at least two entries.
		Map<AgeGroup, Integer> ageGroupsWithNumberOfEntries = Maps.newHashMap();
		for (AgeGroup ageGroup : ageGroups) {
			if (ageGroupsWithNumberOfEntries.keySet().contains(ageGroup)) {
				Integer currentValue = ageGroupsWithNumberOfEntries.get(ageGroup);
				ageGroupsWithNumberOfEntries.remove(ageGroup);
				ageGroupsWithNumberOfEntries.put(ageGroup, currentValue + 1);
			}
			else {
				ageGroupsWithNumberOfEntries.put(ageGroup, 1);
			}
		}
		
		// Check if any ageGroup is contained twice. 
		Map<AgeGroup, Integer> ageGroupsContainedAtLeastTwice = Maps.newHashMap();
		for (Entry<AgeGroup, Integer> ageGroupEntry : ageGroupsWithNumberOfEntries.entrySet()) {
			if (ageGroupEntry.getValue() > 1) {
				ageGroupsContainedAtLeastTwice.put(ageGroupEntry.getKey(), ageGroupEntry.getValue());
			}
		}
		
		// Check if all ageGroupEntries can be added by one of those. 
		// TODO: Improve checking algorithm. 
		for (AgeGroup missingAgeGroup : missingAgeGroups) {
			for (Entry<AgeGroup, Integer> ageGroupEntry : ageGroupsContainedAtLeastTwice.entrySet()) {
				if (ageGroupEntry.getKey().isGroupAllowed(missingAgeGroup)) {
					missingAgeGroups.remove(missingAgeGroup);
				}
			}
		}
		
		if (missingAgeGroups.size() > 0) {
			response.setStatus(CheckResponseStatus.ERROR);
			String message = "Criterium ageGroups is not fulfilled for ageGroups: ";
			boolean firstValue = true; 
			for (AgeGroup ageGroup : missingAgeGroups) {
				if (!firstValue) 
					message = message.concat(", ");
				message = message.concat(ageGroup.toString());
				firstValue = false;
			}
			response.setMessage(message);
		}
		
		else {
			response.setStatus(CheckResponseStatus.OK);
			response.setMessage("");
		}
		
		return response;
	}

}
