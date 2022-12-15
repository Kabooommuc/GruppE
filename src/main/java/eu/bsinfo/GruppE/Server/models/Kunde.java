package eu.bsinfo.GruppE.Server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class Kunde {
    @JsonProperty
    private UUID id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String vorname;

    public Kunde() {
        this.id = generateRandomUUID();
    }

    public Kunde(String name, String vorname) {
        this.id = generateRandomUUID();
        this.name = name;
        this.vorname = vorname;
    }

    private UUID generateRandomUUID(){
        UUID randomUUID;
        do {
            randomUUID = UUID.randomUUID();
        } while(KundenRessource.isUUIDTaken(randomUUID));
        return randomUUID;
    }
}
