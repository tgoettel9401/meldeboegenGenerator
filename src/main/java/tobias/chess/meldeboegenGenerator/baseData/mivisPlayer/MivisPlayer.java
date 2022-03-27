package tobias.chess.meldeboegenGenerator.baseData.mivisPlayer;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class MivisPlayer {

    @Id
    @GeneratedValue
    private Long id;

    private Long pid;
    private String lastName;
    private String firstName;
    private String birthday;
    private String gender;
    private String nation;
    private String fideNation;
    private String permission;
    private String verband;
    private String club;
    private String status;
    private Integer age;
}
