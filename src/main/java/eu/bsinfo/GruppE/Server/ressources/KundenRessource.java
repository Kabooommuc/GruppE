package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.Database.databaseCRUD;
import eu.bsinfo.GruppE.Server.models.Kunde;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;

@Path("kunden")
public class KundenRessource {

    public static final String MSG_NOT_FOUND = "Kunde not found!";
    private static final String MSG_UPDATED = " was successfully updated!";
    private static final String MSG_IS_NULL = "Kunde is null!";

    /**
     * Erstellt neuen Eintrag im Table Kunden, fügt übergebenes Kundenobjekt hinzu
     * url: /kunden/
     * @param postKunde Kundenobjekt das in die Datenbank eingetragen wird
     * @return 201 CREATED, Kundenobjekt
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKunde(Kunde postKunde) {
        System.out.println("POST: "+postKunde);
        if(postKunde == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_IS_NULL).build();

        try {
            databaseCRUD.createKunde(postKunde);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.CREATED).entity(postKunde).build();
    }

    /**
     * Fragt in der Datenbank alle Kunden ab und gibt diese zurück
     *
     * @return 200 OK, ArrayList mit allen in der Datenbank eingetragenen Kunden
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKunden() {
        System.out.println("KundenRessource.getAllKunden");

        try {
            ArrayList<Kunde> kundenList = databaseCRUD.readAllKunden();
            return Response.status(Response.Status.OK).entity(kundenList).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Ermöglicht das Abfragen eines bestimmten Kunden anhand seiner ID
     *
     * @param id ID des Kunden
     * @return 200 OK, Abgefragtes Kundenobjekt
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKundeById(@PathParam("id") String id) {
        System.out.println("KundenRessource.getKundeById");

        if(id == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        try {
            Kunde kunde = databaseCRUD.readKunde(id);
            return Response.status(Response.Status.OK).entity(kunde).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Updated ein Kundenobjekt in der Datenbank
     *
     * @param putKunde Kundenobjekt mit aktualisierten Daten
     * @return 200 OK
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putKunde(Kunde putKunde) {
        try {
            databaseCRUD.updateKunde(putKunde);
            return Response.status(Response.Status.OK).entity(putKunde.getId() + MSG_UPDATED).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @DEPRECATED - NOT USED IN PROJECT
     * Entfernt Kundendatensatz aus der Datenbank, Kunde wird anhand ID in DB gesucht
     *
     * @param id ID des Kunden
     * @return 200 OK,
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKunde(@PathParam("id") String id) {
        try {
            databaseCRUD.deleteKunde(id);
            return Response.status(Response.Status.OK).build();
        } catch (SQLException e) {
                System.err.println();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
