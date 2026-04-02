package com.smartcampus.api.resource;

import javax.ws.rs.DELETE;
import com.smartcampus.api.model.Room;
import com.smartcampus.api.store.RoomStore;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Collection<Room> getAllRooms() {
        return RoomStore.getAllRooms();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = RoomStore.getRoomById(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    @POST
    public Response createRoom(Room room) {
        RoomStore.addRoom(room);

        return Response.created(URI.create("/rooms/" + room.getId()))
                .entity(room)
                .build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = RoomStore.getRoomById(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Room cannot be deleted because it still has sensors assigned")
                    .build();
        }

        RoomStore.deleteRoom(roomId);

        return Response.ok("Room deleted successfully").build();
    }
}