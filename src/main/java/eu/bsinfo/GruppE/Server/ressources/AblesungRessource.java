package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.Database.databaseCRUD;
import eu.bsinfo.GruppE.Server.Server;
import eu.bsinfo.GruppE.Server.models.Ablesung;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;


@Path("ablesungen")
public class AblesungRessource {

    public static ArrayList<Ablesung> ablesungen = new ArrayList<>();
    private static final String MSG_NOT_FOUND = "Ablesung not found!";
    private static final String MSG_ERROR = "Error with Ablesung!";
    private static final String MSG_UPDATED = " was successfully updated.";

    /**
     * Prueft, ob KundenUUID aus der Ablesung existiert, wenn nein neuer Eintrag in der Datenbank
     *
     * @param postAblesung übergebene neue Ablesung
     * @return 201 CREATED , Ablesung
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAblesung(Ablesung postAblesung) {
        System.out.println("POST: " + postAblesung);
        if (postAblesung == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_ERROR).build();

        try {
            databaseCRUD.createAblesung(postAblesung);
            return Response.status(Response.Status.CREATED).entity(postAblesung).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Gibt alle vorhandenen Ablesungen in der DB aus
     *
     * @return 200 OK
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAblesungen() {
        System.out.println("AblesungRessource.getAllAblesungen");

        try {
            ArrayList<Ablesung> allAblesungen = databaseCRUD.readAllAblesungen();
            return Response.status(Response.Status.OK).entity(allAblesungen).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Gibt Ablesung fuer AbluesungUUID aus
     *
     * @param id Ablesung UUID
     * @return 200 OK, abgefragtes Ablesungsobjekt
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAblesungById(@PathParam("id") String id) {
        System.out.println("AblesungRessource.getAblesungById");

        if (id == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        try {
            UUID ablesungId = Server.convertStringToUUID(id);

            Ablesung ablesung = databaseCRUD.readAblesung(ablesungId);
            return Response.status(Response.Status.OK).entity(ablesung).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Veraendert Ablesung anhand von uebergebenen Daten
     *
     * @param putAblesung
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAblesung(Ablesung putAblesung) {
        System.out.println("AblesungRessource.putAblesung");

        try {
            databaseCRUD.updateAblesung(putAblesung);
            return Response.status(Response.Status.OK).entity(putAblesung.getId() + MSG_UPDATED).build();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Loescht Ablesung aus ArrayList anhand von AblesungUUID
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAblesung(@PathParam("id") String id) {
        System.out.println("AblesungRessource.deleteAblesung");

        UUID ablesungId = Server.convertStringToUUID(id);
        if (ablesungId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        try {
            databaseCRUD.deleteAblesung(ablesungId);
            return Response.status(Response.Status.OK).build();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    // TODO @Tobi
    @GET
    @Path("VorZweiJahrenHeute")
    @Produces(MediaType.APPLICATION_JSON)
    public Response VorZweiJahrenHeute() {
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().minusYears(2));

        for (Ablesung ablesung : ablesungen) {
            System.out.println(ablesung.getDatum());

            if (ablesung.getDatum().isAfter(LocalDate.now()) && ablesung.getDatum().isBefore(LocalDate.now().minusYears(2)))
                continue;

            return Response.status(Response.Status.OK).entity(ablesung).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

    }
}
