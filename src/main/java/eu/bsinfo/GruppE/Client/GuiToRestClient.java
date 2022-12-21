package eu.bsinfo.GruppE.Client;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.client.Entity.json;

public class GuiToRestClient {
    static final String url = "http://localhost:8080/rest";

    public static String getClient(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        System.out.println("Called " + path + ".");
        return response.readEntity(String.class);
    }

    public static void postClient(String path, Ablesung ablesung) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.path(path).request(MediaType.APPLICATION_JSON);
        Response response = builder.post(json(ablesung));
        System.out.println(response);
        //TODO: maybe sinnvoll, response code zu returnen damit gui anzeigen kann ob funktioniert hat
    }
}
