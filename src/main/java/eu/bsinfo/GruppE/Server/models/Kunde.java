package eu.bsinfo.GruppE.Server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.GruppE.GUI.CustomUUID;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Kunde {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String vorname;

    public Kunde() {
        this.id = CustomUUID.nextValue();
    }

    public Kunde(String name, String vorname) {
        this.id = CustomUUID.nextValue();
        this.name = name;
        this.vorname = vorname;
    }

    public Kunde(String id, String name, String vorname) {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
    }

    public static Kunde getKundeFromKunden(String id) {
        for(Kunde kunde : KundenRessource.kunden) {
            if(kunde.getId().equals(id))
                return kunde;
        }
        return null;
    }
}
