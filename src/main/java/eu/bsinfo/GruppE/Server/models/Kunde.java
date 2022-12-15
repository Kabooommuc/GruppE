package eu.bsinfo.GruppE.Server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "kunde")
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
        do {
            this.id = UUID.randomUUID();
        }
        while(KundenRessource.isUUIDTaken(this.id));

        this.name = name;
        this.vorname = vorname;
    }
}
