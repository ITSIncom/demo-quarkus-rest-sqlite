package it.itsincom.resource;

import it.itsincom.entity.Item;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class ItemResource {

    @GET
    public List<Item> list() {
        return Item.listAll();
    }

    @GET
    @Path("/{id}")
    public Item get(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null) throw new NotFoundException();
        return item;
    }

    @POST
    @Transactional
    public Response create(Item item) {
        item.persist();
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Item update(@PathParam("id") Long id, Item updated) {
        Item item = Item.findById(id);
        if (item == null) throw new NotFoundException();
        item.name = updated.name;
        item.description = updated.description;
        return item;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = Item.deleteById(id);
        if (!deleted) throw new NotFoundException();
        return Response.noContent().build();
    }
}
