package tobias.chess.meldeboegenGenerator.pdfGeneration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

@Service
public class MeldeboegenAnreiseGenerator {
	
	private final String saveBasePath = "C:/jasperoutput";
	
	private final TeamService teamService;
	
	public MeldeboegenAnreiseGenerator(TeamService teamService) {
		this.teamService = teamService;
	}
	
	public PdfGenerationResult generateReport(MeldeboegenType meldeboegenType, String title) throws JRException {
		
		// Import all Teams with their Players. 
		List<Team> teams = teamService.findAll();
		
		// Load and compile JasperReport
		InputStream meldeboegenAnreiseReportStream
		  = getClass().getResourceAsStream("/" + meldeboegenType.getTemplateFilename());
		JasperReport jasperReport
		  = JasperCompileManager.compileReport(meldeboegenAnreiseReportStream);
		
		// Generate jasperPrint for every team
		List<JasperPrint> jasperPrints = generateJasperPrints(jasperReport, meldeboegenType, teams, title);
		
		// Make sure the output directory exists.
		File outDir = new File(saveBasePath);
		outDir.mkdirs();
		
		// Export to PDF.
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
		//exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(saveBasePath + "/" + meldeboegenType.getFilename()));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		//set your configuration
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		
		PdfGenerationResult result = new PdfGenerationResult();
		result.setResultCode(HttpStatus.OK);
		result.setMessage("The final document has been saved to folder " + saveBasePath + " with the filename " + meldeboegenType.getFilename());
        result.setFileByteArray(outputStream.toByteArray());
		
		System.out.println("Done!");
		
		return result;

		
	}
	
	private List<JasperPrint> generateJasperPrints(JasperReport jasperReport, MeldeboegenType meldeboegenType, List<Team> teams, String title) throws JRException {
		
		List<JasperPrint> jasperPrints = Lists.newArrayList();
		
		if (meldeboegenType.equals(MeldeboegenType.ANREISE_DLM)) {
			
			// For every team generate exactly one Meldebogen. 
			for (Team team : teams) {
				Map<String, Object> parameters = generateParameters(meldeboegenType, team, null, Optional.empty());
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
			}
			
		} else if (meldeboegenType.equals(MeldeboegenType.ANREISE_DVM)) {

			// For every team generate exactly one Meldebogen.
			for (Team team : teams) {
				Map<String, Object> parameters = generateParameters(meldeboegenType, team, null, Optional.of(title));
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
			}
		}
		
		else { // MeldeboegenType = ROUND
			
			// A Meldebogen contains always two teams, hence we need NUMBER_OF_TEAMS / 2 (rounded up) Meldeboegen. 
			int numberOfTeams = teams.size();
			int numberOfMeldeboegen = (int) Math.ceil(numberOfTeams / 2.0);
			
			for (int i = 0; i < numberOfMeldeboegen; i++) {
				Map<String, Object> parameters = Maps.newHashMap();
				Integer team1Index = i*2;
				if (team1Index+1 <= numberOfTeams) {
					Team team1 = teams.get(team1Index);
					parameters.putAll(generateParameters(meldeboegenType, team1, 1, Optional.empty()));
				}
				Integer team2Index = i*2+1;
				if (team2Index+1 <= numberOfTeams) {
					Team team2 = teams.get(team2Index);
					parameters.putAll(generateParameters(meldeboegenType, team2, 2, Optional.empty()));
				}
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,  parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
			}
			
		}
		
		return jasperPrints;
		
	}
	
	/**
	 * Generates the parameters dependent on meldeboegenType. For Anreise-Boegen this is called once, 
	 * for Round-Boegen called twice (once for every team). TeamNumber may be null if meldeboegenType
	 * is ANREISE.
	 * @param meldeboegenType
	 * @param team
	 * @return Map<String, Object>
	 */
	private Map<String, Object> generateParameters(MeldeboegenType meldeboegenType, Team team, Integer teamNumber, Optional<String> titleOptional) {
		
		Map<String, Object> parameters = Maps.newHashMap();
		
		if (meldeboegenType.equals(MeldeboegenType.ANREISE_DLM)) {
			
			parameters.put("title", "Deutsche Ländermeisterschaft 2021");
			parameters.put("teamName", team.getName());

			Integer playerNumber = 1;
			for (Player player : team.getPlayers()) {
				parameters.put("player" + playerNumber + ".name", player.getName());
				parameters.put("player" + playerNumber + ".dwz", player.getDwzRating().toString());
				parameters.put("player" + playerNumber + ".ageGroup", player.getAgeGroup().name());
				playerNumber++;
			}
			
			// If playerNumber stayed lower than 25, then the players have to be initialized with "".
			for (; playerNumber <= 25; playerNumber++) {
				parameters.put("player" + playerNumber + ".name", "");
				parameters.put("player" + playerNumber + ".dwz", "");
				parameters.put("player" + playerNumber + ".ageGroup", "");
			}
			
		} else if (meldeboegenType.equals(MeldeboegenType.ANREISE_DVM)) {
			titleOptional.ifPresentOrElse(
					title -> parameters.put("title", title),
					() -> parameters.put("title", "Deutsche Vereinsmeisterschaft")
			);
			parameters.put("teamName", team.getName());

			Integer playerNumber = 1;
			for (Player player : team.getPlayers()) {
				parameters.put("player" + playerNumber + ".name", player.getName());
				parameters.put("player" + playerNumber + ".dwz", player.getDwzRating().toString());
				parameters.put("player" + playerNumber + ".ageGroup", player.getAgeGroup().name());
				playerNumber++;
			}

			// If playerNumber stayed lower than 25, then the players have to be initialized with "".
			for (; playerNumber <= 25; playerNumber++) {
				parameters.put("player" + playerNumber + ".name", "");
				parameters.put("player" + playerNumber + ".dwz", "");
				parameters.put("player" + playerNumber + ".ageGroup", "");
			}
		}
		
		else { // MeldeboegenType = ROUND
			
			parameters.put("team" + teamNumber + ".name", team.getName());

			Integer playerNumber = 1;
			for (Player player : team.getPlayers()) {
				String playerString = player.getName() + " / " + player.getAgeGroup();
				parameters.put("team" + teamNumber + ".player" + playerNumber, playerString);
				playerNumber++;
			}
			
			// If playerNumber stayed lower than 10, then the players have to be initialized with "".
			for (; playerNumber <= 10; playerNumber++) {
				parameters.put("team" + teamNumber + ".player" + playerNumber, "");
			}
			
		}
		
		return parameters;
		
	}


}
