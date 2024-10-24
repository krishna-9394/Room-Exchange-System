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
@Table(name = "auth")
public class AuthorizationEntity {
    @Id
    @Column(nullable = false, length = 320,unique = true)
    private String email;

    @Column(nullable = false)
    private String passcode;

}
