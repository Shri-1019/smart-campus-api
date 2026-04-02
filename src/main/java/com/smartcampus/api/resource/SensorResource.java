package com.smartcampus.api.resource;

import javax.ws.rs.QueryParam;
import com.smartcampus.api.model.Room;
import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.store.RoomStore;
import com.smartcampus.api.store.SensorStore;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Collection<Sensor> getAllSensors(@QueryParam("type") String type) {
        Collection<Sensor> allSensors = SensorStore.getAllSensors();

        if (type == null || type.trim().isEmpty()) {
            return allSensors;
        }

        java.util.List<Sensor> filteredSensors = new java.util.ArrayList<>();

        for (Sensor sensor : allSensors) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return filteredSensors;
    }

    @POST
    public Response createSensor(Sensor sensor) {
        Room room = RoomStore.getRoomById(sensor.getRoomId());

        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room does not exist")
                    .build();
        }

        SensorStore.addSensor(sensor);
        room.getSensorIds().add(sensor.getId());

        return Response.created(URI.create("/sensors/" + sensor.getId()))
                .entity(sensor)
                .build();
    }
}