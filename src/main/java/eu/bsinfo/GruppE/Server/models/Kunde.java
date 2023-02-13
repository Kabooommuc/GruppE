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
        this.id = UUID.randomUUID();
    }

    public Kunde(String name, String vorname) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.vorname = vorname;
    }

    public Kunde(UUID uuid, String name, String vorname) {
        this.id = uuid;
        this.name = name;
        this.vorname = vorname;
    }

    public static Kunde getKundeFromKunden(UUID id) {
        for(Kunde kunde : KundenRessource.kunden) {
            if(!kunde.getId().equals(id))
                continue;

            return kunde;
        }
        return null;
    }
}
