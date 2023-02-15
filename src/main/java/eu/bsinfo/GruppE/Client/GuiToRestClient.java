package eu.bsinfo.GruppE.Client;

import eu.bsinfo.GruppE.GUI.MeasurementData;
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
    static Client client = ClientBuilder.newClient();
    static WebTarget target = client.target(url);

    public static String getFromRest(String path) {
        System.out.println("GuiToRestClient.getFromRest");
        System.out.println("url + path = " + url + "/" + path);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        System.out.println("Called " + path + ".");
        if (response.getStatus() == 404) {
            return "" + returnStatusCode(response) + " - not found";
        }
        return response.readEntity(String.class);
    }
    public static Kunde getKundeFromUUID(String path) {
        Invocation.Builder builder = target.path("kunden").path(path).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        System.out.println("Called kunden/" + path + ".");
        if (response.getStatus() <= 400) {
            // TODO Fehler abfangen
        }
        return response.readEntity(Kunde.class);
    }

    public static void postAblesung(Ablesung ablesung) {
        String path = "ablesungen";
        System.out.println("DEBUG: sending ablesung to REST");
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.post(json(ablesung));
        System.out.println(response);
    }

    public static void postKunde(Kunde kunde) {
        String path = "kunden";
        System.out.println("DEBUG: sending kunde to REST");
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.post(json(kunde));
        System.out.println(response);
    }

    public static int returnStatusCode(Response response) {
        return response.getStatus();
    }
}
