package com.example.Room_Exchange_System.domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "availableRoom")
public class AvailableRoomEntity {
    @Id
    @Column(nullable = false)
    private String studentId;

    @Column(length = 250)
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "studentId")
    @MapsId
    private StudentEntity availableStudent;
}
