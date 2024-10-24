package com.example.Room_Exchange_System.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwapRequestDTO {
    private int requestID;
    private StudentDTO reqStudent;
    private StudentDTO reqToStudent;

}
