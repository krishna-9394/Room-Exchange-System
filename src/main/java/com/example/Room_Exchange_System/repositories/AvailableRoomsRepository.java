package com.example.Room_Exchange_System.repositories;

import com.example.Room_Exchange_System.domain.Entity.AvailableRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableRoomsRepository extends JpaRepository<AvailableRoomEntity,String> {
    public boolean existsAvailableRoomEntitiesByAvailableStudent_Room_RoomId(String roomId);
    public List<AvailableRoomEntity> findAvailableRoomEntitiesByAvailableStudent_Room_BuildingName(String buildingName);
    public boolean existsAvailableRoomEntitiesByAvailableStudent_StudentId(String studentId);

//    void deleteById(String studentID);
}
