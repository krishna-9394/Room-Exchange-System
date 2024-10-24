package com.example.Room_Exchange_System.repositories;

import com.example.Room_Exchange_System.domain.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,String> {
    @Override
    boolean existsById(String s);
}
