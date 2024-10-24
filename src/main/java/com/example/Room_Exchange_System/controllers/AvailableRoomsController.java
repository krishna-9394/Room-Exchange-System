package com.example.Room_Exchange_System.controllers;

import com.example.Room_Exchange_System.domain.DTO.AvailableRoomsDTO;
import com.example.Room_Exchange_System.domain.DTO.AvailableRoomsDetailsDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTOOutput;
import com.example.Room_Exchange_System.services.AvailableRoomsService;
import jakarta.persistence.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AvailableRoomsController {
    private final AvailableRoomsService roomsService;

    public AvailableRoomsController(AvailableRoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping("/availableRooms")
    public List<AvailableRoomsDetailsDTO> fetchAvailableRooms() {
        return roomsService.fetchAllAvailableRooms();
    }

    @PostMapping("/availableRooms/{studentID}")
    public AvailableRoomsDetailsDTO registerRoomAvailableForSwap(@PathVariable String studentID,
                                                                 @RequestParam(required = false) String description) {
        return roomsService.registerRoomToBeAvailableForSwap(studentID,description);
    }

    @GetMapping("/availableRoomsExists/{roomId}")
    public ResponseEntity<?> checkRoomIsAvailable(@PathVariable String roomId) {
        return roomsService.checkRoomExists(roomId);
    }

    @GetMapping("/availableRooms/{buildingName}")
    public List<AvailableRoomsDetailsDTO> filterAvailableRoomsByBuildingName(@PathVariable String buildingName) {
        return roomsService.fetchFilterRoomsByBuilding(buildingName);
    }

    @PostMapping("/availableRoomsSwapRequest")
    public SwapRequestDTOOutput requestForSwap(@RequestParam String reqStudentID, @RequestParam String reqToStudentID) {
        return roomsService.swapRequestRegister(reqStudentID,reqToStudentID);
    }
}
