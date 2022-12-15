package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.UUID;


@Path("ablesungen")
public class AblesungRessource {

    private static final ArrayList<Ablesung> ablesungen = new ArrayList<>();
    private static final String MSG_NOT_FOUND = "Ablesung not found!";
    private static final String MSG_UPDATED = " was successfully updated.";

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAblesung(Ablesung ablesung) {
        System.out.println(ablesung);
        ablesungen.add(ablesung);
        return Response.status(Response.Status.CREATED).entity(ablesung).build();
        //TODO: 400 und 404 Response Code
    }

    // not required, but maybe useful
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAblesungen() {
        return Response.status(Response.Status.OK).entity(ablesungen).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAblesungById(@PathParam("id") String id) {
        UUID ablesungId = convertStringToUUID(id);
        if (ablesungId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Ablesung ablesung : ablesungen) {
            if(!ablesung.getId().equals(ablesungId))
                continue;
            return Response.status(Response.Status.OK).entity(ablesung).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(MSG_NOT_FOUND).build();
    }

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

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAblesung(@PathParam("id") String id) {
        UUID ablesungId = convertStringToUUID(id);
        if(ablesungId == null)
            return Response.status(Response.Status.NOT_FOUND).entity(MSG_NOT_FOUND).build();

        for(Ablesung ablesung : ablesungen) {
            if(!ablesung.getId().equals(ablesungId))
                continue;

            ablesungen.remove(ablesung);
            return Response.status(Response.Status.OK).entity(ablesung).build();
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