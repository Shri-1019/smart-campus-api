package com.smartcampus.api.config;

import com.smartcampus.api.resource.DiscoveryResource;
import com.smartcampus.api.resource.RoomResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(JacksonFeature.class);
        return classes;
    }
}