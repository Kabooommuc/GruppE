package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.models.Kunde;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("kunden")
public class KundenRessource {

    private static final List<Kunde> kunden = new ArrayList<>();
    private static final String MSG_NOT_FOUND = "Kunde not found!";
    private static final String MSG_UPDATED = " was successfully updated!";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKunde(Kunde postKunde) {
        kunden.add(postKunde);
        return Response.status(Response.Status.CREATED).entity(postKunde).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKunden() {
        return Response.status(Response.Status.OK).entity(kunden).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKunde(@PathParam("id") String id) {
        UUID kundenId = convertStringToUUID(id);
        if(kundenId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Kunde kunde : kunden) {
           if(!kunde.getId().equals(kundenId))
               continue;
           return Response.status(Response.Status.OK).entity(kunde).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putKunde(Kunde putKunde) {
        for(Kunde kunde : kunden) {
            if(!kunde.getId().equals(putKunde.getId()))
                continue;

            kunden.set(kunden.indexOf(kunde), putKunde);
            return Response.status(Response.Status.OK).entity(putKunde.getId() + MSG_UPDATED).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKunde(@PathParam("id") String id) {
        UUID kundenId = convertStringToUUID(id);
        if(kundenId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Kunde kunde : kunden) {
            if(!kunde.getId().equals(kundenId))
                continue;

            // TODO: Die Ablesungen dieses Kunden sollen aber noch auf dem Server gespeichert bleiben,
            //  der Attributwert von kunde soll dabei auf null gesetzt werden.
            //  Das gelöschte Kunden-Objekt soll zusammen mit der Liste der Ablesungen dieses Kunden
            //  als Map mit dem Code 200 zurückgeschickt werden.

            kunden.remove(kunde);
            return Response.status(Response.Status.OK).entity(kunde).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

    private UUID convertStringToUUID(String strUUID) {
        UUID objUUID;

        try {
            objUUID = UUID.fromString(strUUID);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return objUUID;
    }

}
