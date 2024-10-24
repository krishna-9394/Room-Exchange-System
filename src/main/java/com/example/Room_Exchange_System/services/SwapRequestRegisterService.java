package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.DuplicateResourceException;
import com.example.Room_Exchange_System.domain.DTO.StudentDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTO;
import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTOOutput;
import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import com.example.Room_Exchange_System.domain.Entity.SwapRequestEntity;
import com.example.Room_Exchange_System.mappers.impl.StudentMapper;
import com.example.Room_Exchange_System.mappers.impl.SwapRequestMapper;
import com.example.Room_Exchange_System.repositories.SwapRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class SwapRequestRegisterService {
    private final SwapRequestRepository swapRequestRepository;
    private final SwapRequestMapper swapRequestMapper;
    private final StudentService studentService;
    public SwapRequestRegisterService(SwapRequestRepository swapRequestRepository, SwapRequestMapper swapRequestMapper, StudentService studentService) {
        this.swapRequestRepository = swapRequestRepository;
        this.swapRequestMapper = swapRequestMapper;

        this.studentService = studentService;
    }

    public SwapRequestDTOOutput convertOutput(SwapRequestDTO swapRequestDTO) {
        return SwapRequestDTOOutput.builder()
                .reqStudent(studentService.mapStudentDTOToOutput(swapRequestDTO.getReqStudent()))
                .requestID(swapRequestDTO.getRequestID())
                .reqToStudent(studentService.mapStudentDTOToOutput(swapRequestDTO.getReqToStudent()))
                .build();
    }

    public SwapRequestDTO registerSwapRequests(StudentEntity reqStudent, StudentEntity reqToStudent) {
        boolean check = swapRequestRepository.existsSwapRequestEntityByReqToStudent_StudentIdAndReqStudent_StudentId
                (reqToStudent.getStudentId(),reqStudent.getStudentId());
        if (check) {
            throw new DuplicateResourceException("Swap Request already exists");
        }
        SwapRequestEntity build = SwapRequestEntity.builder()
                .reqStudent(reqStudent)
                .reqToStudent(reqToStudent)
                .build();

        SwapRequestEntity savedEntity = swapRequestRepository.save(build);
        return swapRequestMapper.mapTo(savedEntity);
    }
}
