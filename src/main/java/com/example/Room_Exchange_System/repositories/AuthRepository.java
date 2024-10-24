package com.example.Room_Exchange_System.repositories;

import com.example.Room_Exchange_System.domain.Entity.AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface AuthRepository extends JpaRepository<AuthorizationEntity, String> {
}
