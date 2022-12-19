package eu.bsinfo.GruppE.Server.ressources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("world")
public class HelloWorldRessource {

    @Path("hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
        return "Hello World";
    }

}
