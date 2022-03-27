package tobias.chess.meldeboegenGenerator.mivis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MivisCsv {

    @JsonProperty("ID")
    private Long pid;
    @JsonProperty("Nachname")
    private String lastName;
    @JsonProperty("Vorname")
    private String firstName;
    @JsonProperty("Jahrgang")
    private Integer birthYear;
    @JsonProperty("Ort")
    private String location;
    @JsonProperty("Geschlecht")
    private String gender;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Geburtsdatum")
    private String birthday;
    @JsonProperty("Geburtsort")
    private String birthplace;
    @JsonProperty("Nation")
    private String nation;
    @JsonProperty("Verein")
    private String club;
    @JsonProperty("VKZ")
    private String vkz;
    @JsonProperty("Mgl.-Nr.")
    private String memberNumber;
    @JsonProperty("Titel")
    private String title;
    @JsonProperty("Straße")
    private String street;
    @JsonProperty("PLZ")
    private String postalCode;
    @JsonProperty("Telefon 1")
    private String phone1;
    @JsonProperty("Telefon 2")
    private String phone2;
    @JsonProperty("Telefon 3")
    private String phone3;
    @JsonProperty("Fax")
    private String fax;
    @JsonProperty("E-Mail 1")
    private String mail1;
    @JsonProperty("E-Mail 2")
    private String mail2;
    @JsonProperty("E-Mail 3")
    private String mail3;
    @JsonProperty("Funktion")
    private String function;

}
