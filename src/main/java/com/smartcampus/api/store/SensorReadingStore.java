package com.smartcampus.api.store;

import com.smartcampus.api.model.SensorReading;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SensorReadingStore {

    private static final Map<String, List<SensorReading>> readingsBySensor = new LinkedHashMap<>();

    public static List<SensorReading> getReadingsForSensor(String sensorId) {
        return readingsBySensor.getOrDefault(sensorId, new ArrayList<>());
    }

    public static void addReading(String sensorId, SensorReading reading) {
        readingsBySensor.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);
    }
}