package com.example.Room_Exchange_System.controllers;

import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTOOutput;
import com.example.Room_Exchange_System.services.SwapRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@RestController
public class SwapRequestController {
    private final SwapRequestService swapRequestService;

    public SwapRequestController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

    @PutMapping("/SwapRequest/reject")
    public ResponseEntity<?> rejectSwapRequest(@RequestParam String rejectingStudent, @RequestParam String rejectedStudent){
        return swapRequestService.rejectSwapRequest(rejectingStudent,rejectedStudent);
    }

    @PutMapping("/SwapRequest/accept")
    public ResponseEntity<?> acceptSwapRequest(@RequestParam String acceptingStudent, @RequestParam String acceptedStudentId) throws InvalidPropertiesFormatException {
        return swapRequestService.acceptSwapRequest(acceptingStudent,acceptedStudentId);
    }

    @GetMapping("/SwapRequest/get/{studentId}")
    public List<SwapRequestDTOOutput> fetchSwapRequests(@PathVariable String studentId) {
        return swapRequestService.fetchMySwapRequests(studentId);
    }

}
