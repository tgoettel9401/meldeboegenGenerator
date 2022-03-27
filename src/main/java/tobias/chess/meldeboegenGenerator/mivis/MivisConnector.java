package tobias.chess.meldeboegenGenerator.mivis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.ExtractionProcess;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.ExtractionProcessRepository;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.MivisPlayer;
import tobias.chess.meldeboegenGenerator.baseData.mivisPlayer.MivisPlayerRepository;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MivisConnector {
    
    private final MivisPlayerRepository mivisPlayerRepository;
    private final ExtractionProcessRepository extractionProcessRepository;

    private final MivisConfig mivisConfig;

    public void reloadMivisData() {

        // Clear old data
        this.mivisPlayerRepository.deleteAll();
        this.extractionProcessRepository.deleteAll();

        // First create the extraction process and save it. 
        ExtractionProcess process = new ExtractionProcess();
        process.setFinishedVerbaende("");
        process = this.extractionProcessRepository.save(process);

        // Perform the extraction for every verband and store the extraction result as well.
        int number = 0;
        for(int i = 4; i <= 20; ++i) {
            try {
                number++;
                this.loadDataForVerband(i);
                process.setFinishedVerbaende(process.getFinishedVerbaende() + i + "-success;");
                process.setSize(number);
                this.extractionProcessRepository.save(process);
            } catch (Exception ex) {
                number++;
                process.setFinishedVerbaende(process.getFinishedVerbaende() + i + "-error;");
                process.setSize(number);
                ex.printStackTrace();
                this.extractionProcessRepository.save(process);
            }
        }
        
        
    }

    private void loadDataForVerband(Integer verbandId) throws Exception {

        URI url = URI.create("https://mivis.svw.info/bericht/listen/mitgliederliste.php");

        // Create Parameters and Headers
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("stichtag", "01.03.2021");
        params.add("auswahlVkz", "");
        params.add("auswahlOrganisation[]", "1");
        params.add("geschlecht", "2");
        params.add("spielgenehmigung", "0");
        params.add("extern", "0");
        params.add("exportFiletyp", "csv");
        params.add("export", "Los!");
        params.add("auswahlOrganisation[]", verbandId.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", this.mivisConfig.COOKIE());

        // Create entity and perform request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String csv = template.postForEntity(url, requestEntity, String.class).getBody();

        // Manipulate file to make further processing easier
        csv = csv.replace("\ufeff", "");
        String finalCsv = csv;

        // Skip the first two rows as they are only header rows
        String[] lines = csv.split("\r\n");
        for (String line : lines) {
            if (line.contains("Mitgliederliste") || line.contains("Stichtag")) {
                finalCsv = finalCsv.substring(line.length());
            }
        }

        // Remove unnecessary line breaks
        finalCsv = finalCsv.replace("\"\"\r\n", "");

        // Create Schema and Mapper
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        CsvMapper mapper = new CsvMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

        // Create and parse Players
        List<Object> objectList = mapper.readerFor(MivisCsv.class).with(bootstrapSchema).readValues(finalCsv).readAll();
        List<MivisPlayer> players = new ArrayList<>();
        for (Object player : objectList) {
            players.add(this.convertCsvToPlayer((MivisCsv) player));
        }

        // Remove all players that are older than 25 as they are not relevant for our tournament-permissions
        players = players.stream().filter(player -> player.getAge() <= 25).collect(Collectors.toList());
        this.mivisPlayerRepository.saveAll(players);
    }

    private MivisPlayer convertCsvToPlayer(MivisCsv csv) throws Exception {
        MivisPlayer player = new MivisPlayer();
        player.setId(csv.getPid());
        player.setLastName(csv.getLastName());
        player.setFirstName(csv.getFirstName());
        player.setBirthday(csv.getBirthday());
        player.setAge(this.calculateAlter(csv.getBirthday()));
        player.setGender(csv.getGender());
        player.setNation(csv.getNation());
        player.setStatus(csv.getStatus());
        player.setClub(csv.getClub());
        return player;
    }

    private Integer calculateAlter(String geburtsdatum) throws Exception {
        Integer aktuellesJahr = LocalDate.now().getYear();
        Integer jahrgang = 0;
        if (geburtsdatum.length() == 4) {
            jahrgang = Integer.parseInt(geburtsdatum);
        } else {
            if (geburtsdatum.length() != 10) {
                throw new Exception("Geburtsdatum hat falsches Format! " + geburtsdatum);
            }

            LocalDate tmp = LocalDate.parse(geburtsdatum, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            jahrgang = tmp.getYear();
        }

        return aktuellesJahr - jahrgang;
    }

}
