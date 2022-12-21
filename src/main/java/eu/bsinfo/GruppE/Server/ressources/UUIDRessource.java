package eu.bsinfo.GruppE.Server.ressources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.UUID;

@Path("UUID")
public class UUIDRessource {

    public static HashMap<Integer, UUID> idUUIDpairs = new HashMap<>();

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUUID(@PathParam("id") Integer id){
        if(!idUUIDpairs.containsKey(id))
            idUUIDpairs.put(id, UUID.randomUUID());

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
    public Response getPair(@PathParam("id") Integer id) {
        if(!idUUIDpairs.containsKey(id))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(idUUIDpairs.get(id)).build();
    }

}
