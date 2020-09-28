package tobias.chess.meldeboegenGenerator.calculateAverageRating;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import tobias.chess.meldeboegenGenerator.player.AgeGroup;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

public class AverageRatingServiceTest {
	
	@Spy
	@InjectMocks
	private AverageRatingService averageRatingService;
	
	@BeforeEach
	void setUp() {
		initMocks(this);
	}
	
	@ParameterizedTest
	@MethodSource("providePlayerSets")
	void generatePotentialLineupsOfTeamTest(PlayerListWithPotentialLineups playerListWithPotentialLineups, boolean paramIgnored) throws InvalidLineupException, ZeroPlayersShouldBeReducedException {
		
		Team team = playerListWithPotentialLineups.getTeam();
		List<Player> playerList = playerListWithPotentialLineups.getPlayers();
		team.setPlayers(playerList);
		List<PotentialLineupOfTeam> correctLineups = playerListWithPotentialLineups.getPotentialLineups();
		
		List<PotentialLineupOfTeam> returnedLineups = averageRatingService.generatePotentialLineupsOfTeam(team);
		
		assertThat(returnedLineups).hasSize(correctLineups.size());
		assertThat(returnedLineups.containsAll(correctLineups));
		
	}
	
	@ParameterizedTest
	@MethodSource("providePlayerSets")
	void allAgeGroupsContainedTest(PlayerListWithPotentialLineups playerListWithPotentialLineups, boolean expected) throws ZeroPlayersShouldBeReducedException {
		
		Team team = playerListWithPotentialLineups.getTeam();
		List<Player> playerList = playerListWithPotentialLineups.getPlayers();
		team.setPlayers(playerList);
		
		boolean result = averageRatingService.allAgeGroupsContained(Sets.newHashSet(playerList));
		
		assertThat(result).isEqualTo(expected);
		
	}
	
	// False means error, true means perfect
	private static Stream<Arguments> providePlayerSets() {
		Team team = new Team();
		return Stream.of(
				Arguments.of(create8PlayersPerfect(team), true),
				Arguments.of(create10PlayersPerfect(team), true),
				Arguments.of(create8PlayersU12wMissing(team), false),
				Arguments.of(create8PlayersPerfectNoU20ButU18Twice(team), true),
				Arguments.of(create8PlayersPerfectAllU12w(team), true),
				Arguments.of(create8PlayersErrorU14AndU16Missing(team), false),
				Arguments.of(create8PlayersOkButSpecial(team), true),
				Arguments.of(create8PlayersErrorTwoManyU20(team), false),
				Arguments.of(create8PlayersNoU16ButTwoU18(team), false),
				Arguments.of(create8PlayersNoU14ButTwoU12AndU18TwiceButNoU20(team), true),
				Arguments.of(create8PlayersNoU16ButTwoU12AndU18TwiceButNoU20(team), true)
				);
	}

	private static PlayerListWithPotentialLineups create8PlayersNoU16ButTwoU12AndU18TwiceButNoU20(Team team) {

		team.setName("create8PlayersNoU16ButTwoU12AndU18TwiceButNoU20");

		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U14, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U12, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 2000, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U18, 2000, 8);

		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);

		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		potentialLineups.add(potentialLineup1);

		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);

		return setWithLineups;

	}

	private static PlayerListWithPotentialLineups create8PlayersNoU14ButTwoU12AndU18TwiceButNoU20(Team team) {

		team.setName("create8PlayersNoU14ButTwoU12AndU18TwiceButNoU20");

		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U16, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U12, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 2000, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);

		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);

		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		potentialLineups.add(potentialLineup1);

		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);

		return setWithLineups;

	}

	private static PlayerListWithPotentialLineups create8PlayersNoU16ButTwoU18(Team team) {
		
		team.setName("create8PlayersNoU16ButTwoU18");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U12, 1800, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U18, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 2000, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		// No valid lineup
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
	}

	private static PlayerListWithPotentialLineups create8PlayersPerfect(Team team) {
		
		team.setName("create8PlayersPerfect");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U14, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U16, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 2000, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		potentialLineups.add(potentialLineup1);
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static PlayerListWithPotentialLineups create10PlayersPerfect(Team team) {

		team.setName("create10PlayersPerfect");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U14, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U16, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 1800, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);
		Player player9 = createPlayerForGroup(AgeGroup.U18, 1900, 9);
		Player player10 = createPlayerForGroup(AgeGroup.U12, 1800, 10);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9, player10);
		
		// Original 8 and only player9 variations.
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		PotentialLineupOfTeam potentialLineup2 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player9));
		PotentialLineupOfTeam potentialLineup3 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player9, player8));
		
		// Player10 replaces player4
		PotentialLineupOfTeam potentialLineup4 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player10, player5, player6, player7, player8));
		PotentialLineupOfTeam potentialLineup5 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player10, player5, player6, player7, player9));
		PotentialLineupOfTeam potentialLineup6 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player10, player5, player6, player9, player8));
		
		// Player10 replaces player5
		PotentialLineupOfTeam potentialLineup7 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player10, player6, player7, player8));
		PotentialLineupOfTeam potentialLineup8 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player10, player6, player7, player9));
		PotentialLineupOfTeam potentialLineup9 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player10, player6, player9, player8));
		
		// Player10 replaces player6
		PotentialLineupOfTeam potentialLineup10 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player10, player7, player8));
		PotentialLineupOfTeam potentialLineup11 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player10, player7, player9));
		PotentialLineupOfTeam potentialLineup12 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player10, player9, player8));
		
		// Player10 replaces player7
		PotentialLineupOfTeam potentialLineup13 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player10, player8));
		PotentialLineupOfTeam potentialLineup14 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player10, player9));
		
		// Player10 replaces player8
		PotentialLineupOfTeam potentialLineup15 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player10));
		
		// Add all 15 lineups.
		potentialLineups.add(potentialLineup1);
		potentialLineups.add(potentialLineup2);
		potentialLineups.add(potentialLineup3);
		potentialLineups.add(potentialLineup4);
		potentialLineups.add(potentialLineup5);
		potentialLineups.add(potentialLineup6);
		potentialLineups.add(potentialLineup7);
		potentialLineups.add(potentialLineup8);
		potentialLineups.add(potentialLineup9);
		potentialLineups.add(potentialLineup10);
		potentialLineups.add(potentialLineup11);
		potentialLineups.add(potentialLineup12);
		potentialLineups.add(potentialLineup13);
		potentialLineups.add(potentialLineup14);
		potentialLineups.add(potentialLineup15);
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static PlayerListWithPotentialLineups create8PlayersU12wMissing(Team team) {
		
		team.setName("create8PlayersU12wMissing");
		
		Player player1 = createPlayerForGroup(AgeGroup.U16w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U14, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U16, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 2000, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		// No valid lineup
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}

	private static PlayerListWithPotentialLineups create8PlayersPerfectNoU20ButU18Twice(Team team) {

		team.setName("create8PlayersPerfectNoU20ButU18Twice");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U14, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U16, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 1800, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U18, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
	
		// Player 7 is replaced by Player 9
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		
		potentialLineups.add(potentialLineup1);
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}

	private static PlayerListWithPotentialLineups create8PlayersPerfectAllU12w(Team team) {

		team.setName("create8PlayersPerfectAllU12w");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U12w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U12w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12w, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U12w, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U12w, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U12w, 1800, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U12w, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
	
		// Player 7 is replaced by Player 9
		PotentialLineupOfTeam potentialLineup1 = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		
		potentialLineups.add(potentialLineup1);
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static PlayerListWithPotentialLineups create8PlayersErrorU14AndU16Missing(Team team) {

		team.setName("create8PlayersErrorU14AndU16Missing");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20w, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U12, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U18, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U18, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U18, 1800, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		// No possible lineup
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static PlayerListWithPotentialLineups create8PlayersOkButSpecial(Team team) {

		team.setName("create8PlayersOkButSpecial");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U16w, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U12, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U16, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U18, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U12w, 2000, 6);
		Player player7 = createPlayerForGroup(AgeGroup.U16w, 1800, 7);
		Player player8 = createPlayerForGroup(AgeGroup.U12, 2000, 8);
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		PotentialLineupOfTeam lineup = PotentialLineupService.generateLineupOfTeam(team, Sets.newHashSet(
				player1, player2, player3, player4, player5, player6, player7, player8));
		potentialLineups.add(lineup);
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static PlayerListWithPotentialLineups create8PlayersErrorTwoManyU20(Team team) {

		team.setName("create8PlayersErrorTwoManyU20");
		
		Player player1 = createPlayerForGroup(AgeGroup.U12w, 2000, 1);
		Player player2 = createPlayerForGroup(AgeGroup.U20, 2000, 2);
		Player player3 = createPlayerForGroup(AgeGroup.U20, 2000, 3);
		Player player4 = createPlayerForGroup(AgeGroup.U20, 2000, 4);
		Player player5 = createPlayerForGroup(AgeGroup.U20, 2000, 5);
		Player player6 = createPlayerForGroup(AgeGroup.U20, 2000, 6); // U12w but has to play U20w
		Player player7 = createPlayerForGroup(AgeGroup.U20, 1800, 7); // U16w but has to play U20
		Player player8 = createPlayerForGroup(AgeGroup.U20, 2000, 8); // U14 but has to play U14
		
		List<Player> players = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8);
		
		List<PotentialLineupOfTeam> potentialLineups = Lists.newArrayList();
		// No possible lineup
		
		PlayerListWithPotentialLineups setWithLineups = new PlayerListWithPotentialLineups();
		setWithLineups.setTeam(team);
		setWithLineups.setPlayers(players);
		setWithLineups.setPotentialLineups(potentialLineups);
		
		return setWithLineups;
		
	}
	
	private static Player createPlayerForGroup(AgeGroup ageGroup, Integer dwzRating, Integer playerNumber) {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getName()).thenReturn("" + playerNumber);
        Mockito.when(player.getAgeGroup()).thenReturn(ageGroup);
        Mockito.when(player.getDwzRating()).thenReturn(dwzRating);
        Mockito.when(player.toString()).thenReturn("" + playerNumber + " / " + ageGroup + " / " + dwzRating);
        return player;
    }

}
