package eu.bsinfo.GruppE.Client;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

import static jakarta.ws.rs.client.Entity.json;

public class GuiToRestClient {
    static final String url = "http://localhost:8080/rest";

    public static String getFromRest(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        System.out.println("Called " + path + ".");
        if (response.getStatus() == 404) {
            return "" + returnStatusCode(response) + " - not found";
        }
        return response.readEntity(String.class);
    }
    public static Kunde getKundeFromUUID(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        System.out.println("Called " + path + ".");
        if (response.getStatus() == 404) {
            // TODO Fehler abfangen
        }
        return response.readEntity(Kunde.class);
    }

    public static void postAblesung(Ablesung ablesung) {
        String path = "ablesungen";
        System.out.println("DEBUG: sending ablesung to REST");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.post(json(ablesung));
        System.out.println(response);
    }

    public static void postKunde(String path, String kundenNr) {
        String kundenUuid = getFromRest("UUID/" + kundenNr);
        Kunde kunde = new Kunde();
        kunde.setId(UUID.fromString(kundenUuid));

        // TODO wie kriegen wir den Vor- und Nachnamen des Kunden?
        // benoetigen wir fuer das Kundenobjekt.
        KundenRessource.kunden.add(kunde);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.post(json(kunde));
        System.out.println(response);
    }

    public static int returnStatusCode(Response response) {
        return response.getStatus();
    }
}
