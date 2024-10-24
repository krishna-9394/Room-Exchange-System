package com.example.Room_Exchange_System.domain.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String roomId;
    private String buildingName;
    private int floorNo;
    private String wing;
    private int roomNumber;
}
