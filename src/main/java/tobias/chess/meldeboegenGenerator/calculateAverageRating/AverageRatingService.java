package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import tobias.chess.meldeboegenGenerator.player.AgeGroup;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

@Service
public class AverageRatingService {
	
	private final List<AgeGroup> allAgeGroups = Arrays.asList(AgeGroup.U20, AgeGroup.U18, AgeGroup.U16, AgeGroup.U14, AgeGroup.U12, 
			AgeGroup.U20w, AgeGroup.U16w, AgeGroup.U12w);

	public List<AverageRatingWithTeam> generateAverageRatingsWithTeams(List<Team> teams) throws ZeroPlayersShouldBeReducedException, InvalidLineupException {
		List<AverageRatingWithTeam> averageRatingWithTeams = Lists.newArrayList();
		for (Team team: teams) {
			averageRatingWithTeams.add(generateAverageRatingWithTeam(team));
		}

		averageRatingWithTeams.sort(Comparator.comparingDouble(AverageRatingWithTeam::getAverageRating));

		Integer teamNumber = 1;
		for (AverageRatingWithTeam averageRatingWithTeam : averageRatingWithTeams) {
			averageRatingWithTeam.setTeamNumber(teamNumber);
			teamNumber++;
		}

		return averageRatingWithTeams;
	}

	public AverageRatingWithTeam generateAverageRatingWithTeam(Team team) throws ZeroPlayersShouldBeReducedException, InvalidLineupException {

		List<PotentialLineupOfTeam> potentialLineupOfTeams = generatePotentialLineupsOfTeam(team);

		potentialLineupOfTeams.sort(Comparator.comparingDouble(PotentialLineupOfTeam::getAverageRating));

		AverageRatingWithTeam averageRatingWithTeam = new AverageRatingWithTeam();
		averageRatingWithTeam.setTeamName(team.getName());
		averageRatingWithTeam.setAverageRating(potentialLineupOfTeams.get(0).getAverageRating());

		return averageRatingWithTeam;

	}
	
	public List<PotentialLineupOfTeam> generatePotentialLineupsOfTeam(Team team) throws InvalidLineupException, ZeroPlayersShouldBeReducedException {
		
		// Throw exception if there are more than 10 players.
		if (!has10PlayersMax(team)) 
			throw new InvalidLineupException();
		
		// Initialize potential lineups.
		List<PotentialLineupOfTeam> lineups = Lists.newArrayList();
		
		// For every possible permutation with size 8 check if it fulfills the criteria. If it does, add it to the potential lineups.
		Set<Player> playerSet = Sets.newHashSet(team.getPlayers());
		Set<Set<Player>> permutations = Sets.combinations(playerSet, 8);

		Set<Set<Player>> distinctPlayerSets = Sets.newHashSet();
		Integer lineupCounter = 1;
		for (Set<Player> permutation : permutations) {
			if (allAgeGroupsContained(permutation)) {

				// Only add the playerSet if it is still unique which means not contained in distinctSet yet.
				boolean unique = true;
				for(Set<Player> distinctPlayerSetsEntry : distinctPlayerSets) {
					if (distinctPlayerSetsEntry.containsAll(permutation)) {
						unique = false;
					}
				}

				if (unique) {
					PotentialLineupOfTeam lineup = new PotentialLineupOfTeam();
					lineup.setLineupNumber(lineupCounter);
					lineup.setTeam(team);
					lineup.setPlayers(permutation);
					lineups.add(lineup);
					distinctPlayerSets.add(permutation);
					lineupCounter++;
				}

			}
		}
		
		return lineups;
	}
	
	public boolean allAgeGroupsContained(Set<Player> players) throws ZeroPlayersShouldBeReducedException {
		
		AgeGroup.values();
		
		List<AgeGroup> ageGroups = Lists.newArrayList();
		Set<AgeGroup> ageGroupsDistinct = Sets.newHashSet();
		
		for (Player player : players) {
			ageGroups.add(player.getAgeGroup());
			ageGroupsDistinct.add(player.getAgeGroup());
		}
		
		// First check if existing ageGroupsDistinct contain all AgeGroups. If that is the case, everything is fine. 
		if (ageGroupsDistinct.containsAll(allAgeGroups)) {
			return true;
		}
		
		// Not all groups are available, then check if any group with two entries is able to play in a different group. 
		List<AgeGroup> missingAgeGroups = Lists.newArrayList();
		for (AgeGroup ageGroup : allAgeGroups) {
			if (!ageGroupsDistinct.contains(ageGroup))
				missingAgeGroups.add(ageGroup);
		}
		
		// Find all ageGroups with at least two entries. Sort ageGroups according to their sortingKey in descending
		// order. This is necessary so that later on the highest ageGroups are tested first (meaning U20, U18, ...,
		// U12w).
		//Map<AgeGroup, Integer> ageGroupsWithNumberOfEntries = Maps.newHashMap();
		List<AgeGroupWithNumberOfPlayers> ageGroupsWithNumberOfPlayers = Lists.newArrayList();
		ageGroups.sort(Comparator.comparingInt(AgeGroup::getSortingKey));
		for (AgeGroup ageGroup : ageGroups) {
			Optional<AgeGroupWithNumberOfPlayers> ageGroupOptional = ageGroupsWithNumberOfPlayers.stream()
					.filter(ageGroupWithNumber -> ageGroupWithNumber.getAgeGroup().equals(ageGroup))
					.findFirst();
			if (ageGroupOptional.isPresent()) {


			/*if (ageGroupsWithNumberOfPlayers.stream().map(AgeGroupWithNumberOfPlayers::getAgeGroup)
					.collect(Collectors.toList()).contains(ageGroup)) {*/
				ageGroupOptional.get().increaseNumberOfPlayers();
				/*ageGroupsWithNumberOfEntries.remove(ageGroup);
				ageGroupsWithNumberOfEntries.put(ageGroup, currentValue + 1);*/
			}
			else {
				AgeGroupWithNumberOfPlayers newAgeGroup = new AgeGroupWithNumberOfPlayers();
				newAgeGroup.setAgeGroup(ageGroup);
				newAgeGroup.setNumberOfPlayers(1);
				ageGroupsWithNumberOfPlayers.add(newAgeGroup);
				//ageGroupsWithNumberOfEntries.put(ageGroup, 1);
			}
		}
		
		// Check if any ageGroup is contained twice. 
		List<AgeGroupWithNumberOfPlayers> ageGroupsContainedAtLeastTwice = Lists.newArrayList();
		for (AgeGroupWithNumberOfPlayers ageGroupEntry : ageGroupsWithNumberOfPlayers) {
		//for (Entry<AgeGroup, Integer> ageGroupEntry : ageGroupsWithNumberOfEntries.entrySet()) {
			if (ageGroupEntry.getNumberOfPlayers() > 1) {
				ageGroupsContainedAtLeastTwice.add(ageGroupEntry);
				//ageGroupsContainedAtLeastTwice.put(ageGroupEntry.getAgeGroup(), ageGroupEntry.getValue());
			}
		}
		
		// Check if all ageGroupEntries can be added by one of those.
		List<AgeGroup> tempMissingAgeGroups = Lists.newArrayList(missingAgeGroups);
		tempMissingAgeGroups.sort(Comparator.comparingInt(AgeGroup::getSortingKey));
		ageGroupsContainedAtLeastTwice.sort(Comparator.comparingInt(AgeGroupWithNumberOfPlayers::getAgeGroupSortingKey).reversed());
		for (AgeGroup missingAgeGroup : tempMissingAgeGroups) {
			for (AgeGroupWithNumberOfPlayers ageGroupEntry : ageGroupsContainedAtLeastTwice) {
			//for (Entry<AgeGroup, Integer> ageGroupEntry : ageGroupsContainedAtLeastTwice.entrySet()) {
				if (ageGroupEntry.getNumberOfPlayers() > 1 && ageGroupEntry.getAgeGroup().isGroupAllowed(missingAgeGroup)) {
					missingAgeGroups.remove(missingAgeGroup);
					ageGroupEntry.reduceNumberOfPlayers();
					break;
				}
			}
		}
		
		if (missingAgeGroups.size() > 0) {
			return false; 
		}
		
		return true;
		
	}

	private boolean has10PlayersMax(Team team) {
		List<Player> players = team.getPlayers();
		return (players.size() <= 10 && players.size() > 0);
	}
}
