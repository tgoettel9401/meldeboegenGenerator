package tobias.chess.meldeboegenGenerator.pdfGeneration;

import java.io.File;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import tobias.chess.meldeboegenGenerator.player.Player;
import tobias.chess.meldeboegenGenerator.team.Team;
import tobias.chess.meldeboegenGenerator.team.TeamService;

@Service
public class MeldeboegenAnreiseGenerator {
	
	private final TeamService teamService;
	
	public MeldeboegenAnreiseGenerator(TeamService teamService) {
		this.teamService = teamService;
	}
	
	public void generateReport() throws JRException {
		
		// Import all Teams with their Players. 
		List<Team> teams = teamService.findAll();
		
		// Load and compile JasperReport
		InputStream meldeboegenAnreiseReportStream
		  = getClass().getResourceAsStream("/meldebogen-anreise.jrxml");
		JasperReport jasperReport
		  = JasperCompileManager.compileReport(meldeboegenAnreiseReportStream);
		
		List<JasperPrint> jasperPrints = Lists.newArrayList();
		
		for (Team team : teams) {
			
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("title", "Deutsche Ländermeisterschaft 2020");
			parameters.put("teamName", team.getName());
			
			List<Player> players = team.getPlayers();
			players.sort(Comparator.comparingInt(Player::getDwzRating).reversed());

			Integer playerNumber = 1;
			for (Player player : players) {
				String playerString = player.getAgeGroup() + ",  " + player.getName() + ",  " + player.getDwzRating();
				parameters.put("player" + playerNumber, playerString);
				playerNumber++;
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					  jasperReport, parameters, new JREmptyDataSource());
			
			jasperPrints.add(jasperPrint);
			
		}
		
		// Make sure the output directory exists.
		File outDir = new File("C:/jasperoutput");
		outDir.mkdirs();
		
		// Export to PDF.
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/jasperoutput/Meldeboegen-Anreise.pdf"));
		exporter.exportReport();
 
		/*
		// Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint,
               "C:/jasperoutput/Meldebogen-Anreise-" + team.getName() + ".pdf");
		*/
		
        
		System.out.println("Done!");

		
	}


}
