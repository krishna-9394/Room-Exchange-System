package com.example.Room_Exchange_System.repositories;

import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,String> {
      StudentEntity findStudentEntityByRoom_RoomId(String room_roomId);
}
