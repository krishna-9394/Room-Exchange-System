package com.example.Room_Exchange_System.domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Room")
public class RoomEntity {
    @Id
    private String roomId;

    @Column(nullable = false)
    private String buildingName;

    @Column(nullable = false)
    private int floorNo;

    @Column(nullable = false)
    private String wing;

    @Column(nullable = false)
    private int roomNumber;

}