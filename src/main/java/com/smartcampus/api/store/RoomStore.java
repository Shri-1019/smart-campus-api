package com.smartcampus.api.store;

import com.smartcampus.api.model.Room;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoomStore {

    private static final Map<String, Room> rooms = new LinkedHashMap<>();

    static {
        Room sampleRoom = new Room("LIB-301", "Library Quiet Study", 40);
        rooms.put(sampleRoom.getId(), sampleRoom);
    }

    public static Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public static Room getRoomById(String id) {
        return rooms.get(id);
    }

    public static void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }
    public static Room deleteRoom(String id) {
    return rooms.remove(id);
    }
}