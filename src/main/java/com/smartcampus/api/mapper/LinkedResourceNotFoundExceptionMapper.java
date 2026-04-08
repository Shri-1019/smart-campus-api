package com.smartcampus.api.mapper;

import com.smartcampus.api.exception.LinkedResourceNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", "Linked Resource Not Found");
        error.put("message", exception.getMessage());
        error.put("status", 422);

        return Response.status(422)
                .entity(error)
                .build();
    }
}