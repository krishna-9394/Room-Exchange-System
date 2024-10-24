package com.example.Room_Exchange_System.repositories;

import com.example.Room_Exchange_System.domain.Entity.SwapRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SwapRequestRepository extends JpaRepository<SwapRequestEntity,Integer> {
    int deleteSwapRequestEntitiesByReqStudent_StudentIdAndReqToStudent_StudentId(String id1, String id2);
    int deleteSwapRequestEntitiesByReqStudent_StudentIdOrReqToStudent_StudentId(String id1, String id2);
    List<SwapRequestEntity> findSwapRequestEntityByReqToStudent_StudentId(String studentId);
    boolean existsSwapRequestEntityByReqToStudent_StudentIdAndReqStudent_StudentId(String reqToStudentID, String reqStudentID);
}
