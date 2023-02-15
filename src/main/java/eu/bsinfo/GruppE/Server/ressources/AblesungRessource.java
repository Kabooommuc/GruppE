package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.Database.databaseCRUD;
import eu.bsinfo.GruppE.Server.Server;
import eu.bsinfo.GruppE.Server.models.Ablesung;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


@Path("ablesungen")
public class AblesungRessource {

    public static ArrayList<Ablesung> ablesungen = new ArrayList<>();
    private static final String MSG_NOT_FOUND = "Ablesung not found!";
    private static final String MSG_ERROR = "Error with Ablesung!";
    private static final String MSG_UPDATED = " was successfully updated.";

    /**
     * Prueft, ob KundenUUID aus der Ablesung existiert, wenn ja, speichert in ArrayList
     * @param postAblesung
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAblesung(Ablesung postAblesung) {
        System.out.println("POST: " + postAblesung);
        if(postAblesung == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_ERROR).build();

        try {
            databaseCRUD.createAblesung(postAblesung);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.CREATED).entity(postAblesung).build();

    }

    /**
     * Gibt alle vorhandenen Ablesungen aus
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAblesungen() throws SQLException {
        System.out.println("AblesungRessource.getAllAblesungen");
        ArrayList<Ablesung> allAblesungen = databaseCRUD.readAllAblesungen();
        return Response.status(Response.Status.OK).entity(allAblesungen).build();
    }

    /**
     * Gibt Ablesung fuer AbluesungUUID aus
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAblesungById(@PathParam("id") String id) {
        UUID ablesungId = Server.convertStringToUUID(id);
        if (ablesungId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Ablesung ablesung : ablesungen) {
            if(!ablesung.getId().equals(ablesungId))
                continue;
            return Response.status(Response.Status.OK).entity(ablesung).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

    /**
     * Veraendert Ablesung anhand von uebergebenen Daten
     *
     * @param putAblesung Ablesung mit veränderten Daten
     * @return Gibt 200 - OK und UUID zurück
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAblesung(Ablesung putAblesung) {
        for(Ablesung ablesung : ablesungen) {
            if(!ablesung.getId().equals(putAblesung.getId()))
                continue;

            ablesungen.set(ablesungen.indexOf(ablesung), putAblesung);
            return Response.status(Response.Status.OK).entity(putAblesung.getId() + MSG_UPDATED).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

    /**
     * Loescht Ablesung aus ArrayList anhand von AblesungUUID
     * @param id UUID des Kunden
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAblesung(@PathParam("id") String id) {
        UUID ablesungId = Server.convertStringToUUID(id);
        if(ablesungId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Ablesung ablesung : ablesungen) {
            if(!ablesung.getId().equals(ablesungId))
                continue;

            ablesung.setKunde(null);
            ablesungen.remove(ablesung);
            return Response.status(Response.Status.OK).entity(ablesung).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

}
