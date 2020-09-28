package tobias.chess.meldeboegenGenerator.checkAufstellung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import tobias.chess.meldeboegenGenerator.player.AgeGroup;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;

class CheckAufstellungServiceTest {

    @InjectMocks
    @Spy
    private CheckAufstellungService checkAufstellungService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void checkAufstellungenForTeam_ok8Players() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
            createPlayerForGroup(AgeGroup.U12w, 2000),
            createPlayerForGroup(AgeGroup.U16w, 2000),
            createPlayerForGroup(AgeGroup.U20w, 2000),
            createPlayerForGroup(AgeGroup.U12, 2000),
            createPlayerForGroup(AgeGroup.U14, 2000),
            createPlayerForGroup(AgeGroup.U16, 2000),
            createPlayerForGroup(AgeGroup.U18, 2000),
            createPlayerForGroup(AgeGroup.U20, 2000)
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isFalse();
    }

    @Test
    void checkAufstellungForTeam_error8PlayersWithErrorInRating() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 1799),
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U20w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U14, 2000),
                createPlayerForGroup(AgeGroup.U16, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000)
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isTrue();
    }

    @Test
    void checkAufstellungForTeam_ok10Players() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U20w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U14, 2000),
                createPlayerForGroup(AgeGroup.U16, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000)
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isFalse();
    }

    @Test
    void checkAufstellungForTeam_errorU12wMissing() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U16w, 2000), // U12w is missing
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U20w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U14, 2000),
                createPlayerForGroup(AgeGroup.U16, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000)
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isTrue();
    }

    @Test
    void checkAufstellungForTeam_okNoU20ButU18Twice() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U20w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U14, 2000),
                createPlayerForGroup(AgeGroup.U16, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000) // No U20, but therefore two U18
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isFalse();
    }

    @Test
    void checkAufstellungForTeam_okuPlayerAllU12w() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000) // All U12w => allowed to play all ageGroups.
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isFalse();
    }

    @Test
    void checkAufstellungForTeam_errorU14AndU16Missing() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U20w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000), // No U14
                createPlayerForGroup(AgeGroup.U18, 2000), // No U14
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000)
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isTrue();
    }

    @Test
    void checkAufstellungForTeam_okButSpecial() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U16w, 2000),
                createPlayerForGroup(AgeGroup.U12, 2000),
                createPlayerForGroup(AgeGroup.U16, 2000),
                createPlayerForGroup(AgeGroup.U18, 2000),
                createPlayerForGroup(AgeGroup.U12w, 2000), // U12w but has to play U20w
                createPlayerForGroup(AgeGroup.U16w, 2000), // U16w but has to play U20
                createPlayerForGroup(AgeGroup.U12, 2000) // U14 but has to play U14
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isFalse();
    }

    @Test
    void checkAufstellungForTeam_errorOneU12wButRestU20() {
        Team team = Mockito.mock(Team.class);
        List<Player> players = Arrays.asList(
                createPlayerForGroup(AgeGroup.U12w, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000),
                createPlayerForGroup(AgeGroup.U20, 2000), // U12w but has to play U20w
                createPlayerForGroup(AgeGroup.U20, 2000), // U16w but has to play U20
                createPlayerForGroup(AgeGroup.U20, 2000) // U14 but has to play U14
        );
        Mockito.when(team.getPlayers()).thenReturn(players);

        CheckResponse response = checkAufstellungService.checkAufstellungForTeam(team);

        assertThat(response.isError()).isTrue();
    }
    
    private Player createPlayerForGroup(AgeGroup ageGroup, Integer dwzRating) {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getAgeGroup()).thenReturn(ageGroup);
        Mockito.when(player.getDwzRating()).thenReturn(dwzRating);
        return player;
    }

}