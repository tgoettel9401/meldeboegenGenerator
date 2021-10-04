package tobias.chess.meldeboegenGenerator.team;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.ToString;
import tobias.chess.meldeboegenGenerator.player.Player;

@Entity
@Data
public class Team {
	
	@Id @GeneratedValue
	private Long id; 
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Player> players; 
	
	private String name;

	public Integer getPlayersSize() {
		return players == null ? 0 : players.size();
	}

}
