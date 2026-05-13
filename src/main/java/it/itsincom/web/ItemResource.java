package it.itsincom.web;

import it.itsincom.persistence.entity.Item;
import it.itsincom.persistence.repository.ItemRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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

    @Inject
    ItemRepository itemRepository;

    @GET
    public List<Item> list() {
        return itemRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Item get(@PathParam("id") Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) throw new NotFoundException();
        return item;
    }

    @POST
    @Transactional
    public Response create(Item item) {
        itemRepository.persist(item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Item update(@PathParam("id") Long id, Item updated) {
        Item item = itemRepository.findById(id);
        if (item == null) throw new NotFoundException();
        item.name = updated.name;
        item.description = updated.description;
        return item;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = itemRepository.deleteById(id);
        if (!deleted) throw new NotFoundException();
        return Response.noContent().build();
    }
}
