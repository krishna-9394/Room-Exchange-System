package com.example.Room_Exchange_System.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwapRequestDTOOutput {
    private int requestID;
    private StudentDetailsDTO reqStudent;
    private StudentDetailsDTO reqToStudent;
}
