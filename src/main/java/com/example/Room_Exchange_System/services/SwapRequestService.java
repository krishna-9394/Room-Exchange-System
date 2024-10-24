package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.StudentNotFoundException;
import com.example.Room_Exchange_System.domain.DTO.RoomDTO;
import com.example.Room_Exchange_System.domain.DTO.StudentDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTO;

import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTOOutput;
import com.example.Room_Exchange_System.domain.Entity.SwapRequestEntity;
import com.example.Room_Exchange_System.mappers.impl.StudentMapper;
import com.example.Room_Exchange_System.mappers.impl.SwapRequestMapper;
import com.example.Room_Exchange_System.repositories.SwapRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwapRequestService {
    private final SwapRequestRepository swapRequestRepository;
    private final SwapRequestMapper swapRequestMapper;
    private final StudentService studentService;
    private final AvailableRoomsService availableRoomsService;
    private final StudentMapper studentMapper;
    private final EmailService emailService;

    public SwapRequestService(SwapRequestRepository swapRequestRepository, SwapRequestMapper swapRequestMapper, StudentService studentService, AvailableRoomsService availableRoomsService, StudentMapper studentMapper, EmailService emailService) {
        this.swapRequestRepository = swapRequestRepository;
        this.swapRequestMapper = swapRequestMapper;
        this.studentService = studentService;
        this.availableRoomsService = availableRoomsService;
        this.studentMapper = studentMapper;
        this.emailService = emailService;
    }

    public SwapRequestDTOOutput convertOutput(SwapRequestDTO swapRequestDTO) {
        return SwapRequestDTOOutput.builder()
                .reqStudent(studentService.mapStudentDTOToOutput(swapRequestDTO.getReqStudent()))
                .requestID(swapRequestDTO.getRequestID())
                .reqToStudent(studentService.mapStudentDTOToOutput(swapRequestDTO.getReqToStudent()))
                .build();
    }

    @Transactional
    public ResponseEntity<?> rejectSwapRequest(String rejectingStudent, String rejectedStudent) {
        boolean checkDeleted = deleteRejectedEntity(rejectingStudent, rejectedStudent);
        if (!checkDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid StudentIDs");
        }

        StudentDTO rejectedStudentInfo = studentService.findStudent(rejectedStudent);
        StudentDTO rejectingStudentInfo = studentService.findStudent(rejectingStudent);

        String message = "Your Room swap Request for room "+rejectingStudentInfo.getRoom() + "has been rejected by " +
                rejectingStudentInfo.getName();

        emailService.sendSimpleMessage(rejectedStudentInfo.getAuthorization().getEmail(),"Room Swap Rejected",
                message);

        return ResponseEntity.ok("Room Swap Request has been rejected successfully");
    }

    @Transactional
    public boolean deleteRejectedEntity(String rejectingStudent, String rejectedStudent) {
        int deletedEntities = swapRequestRepository.
                deleteSwapRequestEntitiesByReqStudent_StudentIdAndReqToStudent_StudentId(rejectedStudent,
                        rejectingStudent);

        return deletedEntities > 0;
    }

    @Transactional
    public ResponseEntity<?> acceptSwapRequest(String acceptingStudentId, String acceptedStudentId) throws InvalidPropertiesFormatException {
        boolean checkSwapRequestExistence = swapRequestRepository.
                existsSwapRequestEntityByReqToStudent_StudentIdAndReqStudent_StudentId(acceptingStudentId, acceptedStudentId);
        if (!checkSwapRequestExistence){
            throw new StudentNotFoundException(acceptedStudentId+" "+acceptedStudentId+" ids are not valid");
        }

        boolean studentExistenceCheck = availableRoomsService.checkRoomPresentInAvailableRoomsTable(acceptingStudentId);
        if (!studentExistenceCheck) {
            throw new StudentNotFoundException(acceptingStudentId);
        }else{
            deleteOldSwapRequests(acceptingStudentId,acceptedStudentId);
        }

        StudentDTO s1 = studentService.findStudent(acceptingStudentId);
        StudentDTO s2 = studentService.findStudent(acceptedStudentId);

        studentService.deleteStudentById(acceptedStudentId);
        studentService.deleteStudentById(acceptingStudentId);

        RoomDTO prev = s1.getRoom();
        RoomDTO curr = s2.getRoom();
        s1.setRoom(curr);
        s2.setRoom(prev);

        studentService.newStudentRegisteration(s1);
        studentService.newStudentRegisteration(s2);

        String message = "Your Room Request for "+curr+" has been accepted by "+s1.getAuthorization().getEmail();
        emailService.sendSimpleMessage("aa","Room Swap Request Accepted",message);

        return ResponseEntity.ok("Swap Request Accepted Successfully");
    }


    void deleteOldSwapRequests(String acceptingStudentId, String acceptedStudentId) {
        System.out.println("hello");
        availableRoomsService.deleteAvailableRoomByID(acceptedStudentId);
        availableRoomsService.deleteAvailableRoomByID(acceptingStudentId);
        swapRequestRepository.deleteSwapRequestEntitiesByReqStudent_StudentIdOrReqToStudent_StudentId
                (acceptingStudentId,acceptingStudentId);
        swapRequestRepository.deleteSwapRequestEntitiesByReqStudent_StudentIdOrReqToStudent_StudentId(acceptedStudentId,acceptedStudentId);
    }

    public List<SwapRequestDTOOutput> fetchMySwapRequests(String studentID) {
        boolean studentExistenceCheck = availableRoomsService.checkRoomPresentInAvailableRoomsTable(studentID);
        if (!studentExistenceCheck) {
            throw new StudentNotFoundException(studentID);
        }

        List<SwapRequestEntity> swapRequests = swapRequestRepository.findSwapRequestEntityByReqToStudent_StudentId(studentID);
        return swapRequests
                .stream()
                .map(swapRequestEntity -> convertOutput(swapRequestMapper.mapTo(swapRequestEntity)))
                .collect(Collectors.toList());
    }
}
