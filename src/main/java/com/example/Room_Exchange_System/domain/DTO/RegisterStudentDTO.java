package com.example.Room_Exchange_System.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStudentDTO {
    private String studentId;
    private String name;
    private String contactNumber;
    private char gender;
    private String roomId;
    private String email;
}
