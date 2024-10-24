package com.example.Room_Exchange_System.domain.DTO;

import com.example.Room_Exchange_System.domain.Entity.RoomEntity;
import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableRoomsDTO {

    private String studentId;
    private String description;
    private StudentDTO availableStudent;
}
