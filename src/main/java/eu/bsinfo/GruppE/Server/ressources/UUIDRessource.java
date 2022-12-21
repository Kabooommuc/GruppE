package eu.bsinfo.GruppE.Server.ressources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.UUID;

@Path("UUID")
public class UUIDRessource {

    public static HashMap<Integer, UUID> idUUIDpairs = new HashMap<>();
    public static HashMap<UUID, Integer> UUIDidpairs = new HashMap<>();

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUUID(@PathParam("id") Integer id){
        if(!idUUIDpairs.containsKey(id)) {
            UUID putUUID = UUID.randomUUID();
            idUUIDpairs.put(id, putUUID);
            UUIDidpairs.put(putUUID, id);
        }

        return Response.status(Response.Status.OK).entity(idUUIDpairs.get(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPairs() {
        return Response.status(Response.Status.OK).entity(idUUIDpairs).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPair(@PathParam("id") String id) {
        Integer intId;
        UUID uuidId;

        try {
            intId = Integer.valueOf(id);
        } catch(NumberFormatException e) {
            intId = null;
        }
        if(intId != null && idUUIDpairs.containsKey(intId))
            return Response.status(Response.Status.OK).entity(idUUIDpairs.get(intId)).build();

        try {
            uuidId = UUID.fromString(id);
        } catch(IllegalArgumentException e){
            uuidId = null;
        }
        if(uuidId != null && UUIDidpairs.containsKey(uuidId))
            return Response.status(Response.Status.OK).entity(UUIDidpairs.get(uuidId)).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
