package com.example.Room_Exchange_System.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDetailsDTO {
    private String studentId;
    private String name;
    private String contactNumber;
    private String email;
    private char gender;
    private RoomDTO room;

}
