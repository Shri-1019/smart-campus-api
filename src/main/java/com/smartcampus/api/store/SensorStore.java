package com.smartcampus.api.store;

import com.smartcampus.api.model.Sensor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class SensorStore {

    private static final Map<String, Sensor> sensors = new LinkedHashMap<>();

    public static Collection<Sensor> getAllSensors() {
        return sensors.values();
    }

    public static Sensor getSensorById(String id) {
        return sensors.get(id);
    }

    public static void addSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
    }
}