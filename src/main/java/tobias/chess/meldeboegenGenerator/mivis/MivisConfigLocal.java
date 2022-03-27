package tobias.chess.meldeboegenGenerator.mivis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@PropertySource(value = "classpath:localcredentials.properties")
public class MivisConfigLocal implements MivisConfig {

    @Value("${cookie}")
    private String cookie = "";

    public String COOKIE() {
        return cookie;
    }
}

