package tobias.chess.meldeboegenGenerator.team;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TeamService {
	
	private TeamRepository teamRepository;
	
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public Optional<Team> findByName(String name) {
		return this.teamRepository.findFirstByName(name);
	}
	
	public List<Team> findAll() {
		return this.teamRepository.findAll();
	}
	
	public Team findFirstOrCreate(String name) {
		
		// First check if the team already exists. If not, create a new one. 
		Optional<Team> teamOptional = teamRepository.findFirstByName(name);
		if (teamOptional.isPresent()) {
			return teamOptional.get();
		}
		else {
			return this.create(name);
		}
	}
	
	public Team create(String name) {
		Team newTeam = new Team();
		newTeam.setName(name);
		return teamRepository.save(newTeam);
	}
	
	public void deleteAll() {
		this.teamRepository.deleteAll();
	}

}
