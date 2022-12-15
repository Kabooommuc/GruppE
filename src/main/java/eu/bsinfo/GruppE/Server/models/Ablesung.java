package eu.bsinfo.GruppE.Server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class Ablesung {
    @JsonProperty
    private UUID id;
    @JsonProperty
    private String zaehlerNummer;
    @JsonProperty
    private LocalDate datum;
    @JsonProperty
    private Kunde kunde;
    @JsonProperty
    private String kommentar;
    @JsonProperty
    private boolean neuEingebaut;
    @JsonProperty
    private Number zaehlerstand;

    public Ablesung() {
        this.id = UUID.randomUUID();
    }

    public Ablesung(String zaehlerNummer, LocalDate datum, Kunde kunde, String kommentar, boolean neuEingebaut, Number zaehlerstand) {
        this.id = UUID.randomUUID();
        this.zaehlerNummer = zaehlerNummer;
        this.datum = datum;
        this.kunde = kunde;
        this.kommentar = kommentar;
        this.neuEingebaut = neuEingebaut;
        this.zaehlerstand = zaehlerstand;
    }
}
