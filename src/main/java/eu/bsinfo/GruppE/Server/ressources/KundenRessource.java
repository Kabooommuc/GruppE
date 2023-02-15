package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.Database.Util;
import eu.bsinfo.GruppE.Server.Database.databaseCRUD;
import eu.bsinfo.GruppE.Server.Server;
import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Path("kunden")
public class KundenRessource {

    public static ArrayList<Kunde> kunden = new ArrayList<>();
    public static final String MSG_NOT_FOUND = "Kunde not found!";
    private static final String MSG_UPDATED = " was successfully updated!";
    private static final String MSG_IS_NULL = "Kunde is null!";

    /**
     * @param postKunde uebergebenes Kundenobjekt
     * @return daten, die uebermittelt wurden
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
     * Gibt alle vorhandenen Kunden als ArrayList aus
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKunden() throws SQLException {
        System.out.println("KundenRessource.getAllKunden");

        ArrayList<Kunde> kundenList = databaseCRUD.readAllKunden();
        return Response.status(Response.Status.OK).entity(kundenList).build();
    }

    /**
     * Fordert uuid des kunden, wenn vorhanden gibt kundendaten zurueck.
     * @param id == UUID
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKundeById(@PathParam("id") String id) throws SQLException {
        System.out.println("KundenRessource.getKundeById");

        UUID kundenId = Server.convertStringToUUID(id);
        if(kundenId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        Kunde kunde = databaseCRUD.readKunde(kundenId);
        return Response.status(Response.Status.OK).entity(kunde).build();
    }

    /**
     * Veraendert Daten eines Kunden anhand der uuid
     * @param putKunde == Kundenobjekt
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putKunde(Kunde putKunde) {
        try {

            Connection con = Util.getConnection("gm3");
            PreparedStatement checkUUID = con.prepareStatement("SELECT uuid From Kunde WHERE uuid=?;");
            checkUUID.setString(1, String.valueOf(putKunde.getId()));
            ResultSet rsUUID = checkUUID.executeQuery();
            Util.printRs(rsUUID);

            // Check if UUID already exists in database
            if (rsUUID.first()) {
                throw new Error("Kunde already exists");
            }
        } catch (SQLException e) {
            System.err.println(e);
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
        }

        try {
            databaseCRUD.updateKunde(putKunde);
            return Response.status(Response.Status.OK).entity(putKunde.getId() + MSG_UPDATED).build();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * Loescht Kundenobjekt anhand der uuid
     * @param id == UUID
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKunde(@PathParam("id") String id) {
        ArrayList<Ablesung> ablesungen = new ArrayList<>();

        UUID kundenId = Server.convertStringToUUID(id);
        if(kundenId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Kunde kunde : kunden) {
            if(!kunde.getId().equals(kundenId))
                continue;

            for(Ablesung ablesung : AblesungRessource.ablesungen) {
                if(!ablesung.getKunde().getId().equals(kunde.getId()))
                    continue;

                ablesung.setKunde(null);
                ablesungen.add(ablesung);
            }

            kunden.remove(kunde);
            HashMap<String, Object> responseHashMap = new HashMap<>();
            responseHashMap.put("kunde", kunde);
            responseHashMap.put("ablesungen", ablesungen);
            return Response.status(Response.Status.OK).entity(responseHashMap).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();
    }

}
