package com.example.Room_Exchange_System.controllers;

import com.example.Room_Exchange_System.domain.DTO.RoomDTO;
import com.example.Room_Exchange_System.services.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public List<RoomDTO> allRooms() {
        return roomService.fetchAllRooms();
    }

    @GetMapping("/rooms/{roomId}")
    public RoomDTO findRoom(@PathVariable String roomId) {
        return roomService.findRoom(roomId);
    }

    @DeleteMapping("/rooms/{roomId}")
    public void deleteRoom(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
    }

    @PostMapping("/rooms")
    public RoomDTO createRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.createRoom(roomDTO);
    }
}
