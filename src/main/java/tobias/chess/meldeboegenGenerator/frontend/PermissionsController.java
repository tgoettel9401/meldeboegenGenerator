package tobias.chess.meldeboegenGenerator.frontend;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.ExtractionProcess;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.ExtractionProcessRepository;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.MivisPlayer;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.MivisPlayerRepository;
import tobias.chess.meldeboegenGenerator.mivis.MivisConnector;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/permissions")
public class PermissionsController {

    private final MivisConnector mivisConnector;
    private final ExtractionProcessRepository extractionProcessRepository;
    private final MivisPlayerRepository mivisPlayerRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("reloadMivisData")
    public void reloadMivisData() {
        this.mivisConnector.reloadMivisData();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("extractionStatus")
    public List<ExtractionProcess> getExtractionStatus() {
        return this.extractionProcessRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("mivisPlayerSize")
    public Integer getMivisPlayers() {
        return this.mivisPlayerRepository.findAll().size();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = {"resultCsv"}, produces = {"text/csv"})
    public byte[] getPlayersCsv() throws IOException {
        List<MivisPlayer> players = this.mivisPlayerRepository.findAll();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(MivisPlayer.class).withHeader();
        return mapper.writer(schema).writeValueAsString(players).getBytes();
    }

}
