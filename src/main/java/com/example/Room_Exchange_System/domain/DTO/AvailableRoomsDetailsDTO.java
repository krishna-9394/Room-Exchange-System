package com.example.Room_Exchange_System.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableRoomsDetailsDTO {
    private String studentId;
    private String description;
    private StudentDetailsDTO availableStudent;
}
