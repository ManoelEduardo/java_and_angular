package com.saladereuniao.saladereuniao.controller;


import com.saladereuniao.saladereuniao.exeption.ResourceNotFoundExeception;
import com.saladereuniao.saladereuniao.model.Room;
import com.saladereuniao.saladereuniao.repository.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomId)
        throws ResourceNotFoundExeception {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new ResourceNotFoundExeception("Room not found:: "+ roomId));
            return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom (@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable (value = "id") long roomId, @Valid @RequestBody Room roomDetails)
            throws ResourceNotFoundExeception{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundExeception("Room not found for this id:: "+ roomId));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping("/rooms/{id}")
    public Map<String, Boolean> deleteRoom(@PathVariable(value = "id") Long roomId)
    throws ResourceNotFoundExeception{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundExeception("Room not found for this id:: "+ roomId));
        roomRepository.delete(room);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;

    }

}
