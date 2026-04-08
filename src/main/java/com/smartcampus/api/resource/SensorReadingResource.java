package com.smartcampus.api.resource;

import com.smartcampus.api.exception.SensorUnavailableException;
import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.model.SensorReading;
import com.smartcampus.api.store.SensorReadingStore;
import com.smartcampus.api.store.SensorStore;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getAllReadings() {
        return SensorReadingStore.getReadingsForSensor(sensorId);
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = SensorStore.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is in maintenance mode and cannot accept readings");
        }

        SensorReadingStore.addReading(sensorId, reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}
