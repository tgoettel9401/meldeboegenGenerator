package tobias.chess.meldeboegenGenerator.baseData.mivisPlayer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ExtractionProcess implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String finishedVerbaende;

    private Integer size;

}
