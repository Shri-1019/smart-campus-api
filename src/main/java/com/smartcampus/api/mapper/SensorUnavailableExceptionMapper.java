package com.smartcampus.api.mapper;

import com.smartcampus.api.exception.SensorUnavailableException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException exception) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", "Sensor Unavailable");
        error.put("message", exception.getMessage());
        error.put("status", 403);

        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}