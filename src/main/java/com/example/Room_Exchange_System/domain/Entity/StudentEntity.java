package com.example.Room_Exchange_System.domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Student")
public class StudentEntity {
    @Id
    @Column(length = 8, nullable = false)
    private String studentId;

    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false, length = 10)
    private String contactNumber;

    @Column(nullable = false, length = 1)
    private char gender;

    @Column(length = 1, nullable = false)
    private char roomSwapAvailableFlag = 'N';

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId", nullable = false)
    private RoomEntity room;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", nullable = false)
    private AuthorizationEntity authorization;


}
