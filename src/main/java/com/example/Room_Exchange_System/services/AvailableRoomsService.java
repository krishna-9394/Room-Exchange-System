package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.StudentNotFoundException;
import com.example.Room_Exchange_System.domain.DTO.*;
import com.example.Room_Exchange_System.domain.Entity.AvailableRoomEntity;
import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import com.example.Room_Exchange_System.mappers.impl.AvailableRoomsMapper;
import com.example.Room_Exchange_System.repositories.AvailableRoomsRepository;
import com.example.Room_Exchange_System.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailableRoomsService {
    private final AvailableRoomsRepository availableRoomsRepository;
    private final AvailableRoomsMapper mapper;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final SwapRequestRegisterService registerSwapRequests;
    public AvailableRoomsService(
            AvailableRoomsRepository availableRoomsRepository,
            AvailableRoomsMapper mapper,
            StudentService studentService,
            StudentRepository studentRepository, SwapRequestRegisterService registerSwapRequests) {
        this.availableRoomsRepository = availableRoomsRepository;
        this.mapper = mapper;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.registerSwapRequests = registerSwapRequests;
    }

    public AvailableRoomsDetailsDTO convertResponseDTO(AvailableRoomsDTO availableRoomsDTO) {
        return AvailableRoomsDetailsDTO.builder()
                .studentId(availableRoomsDTO.getStudentId())
                .description(availableRoomsDTO.getDescription())
                .availableStudent(studentService.mapStudentDTOToOutput(availableRoomsDTO.getAvailableStudent()))
                .build();
    }

    public List<AvailableRoomsDetailsDTO> fetchAllAvailableRooms() {
        List<AvailableRoomEntity> availableRoomEntities = availableRoomsRepository.findAll();
        return availableRoomEntities.stream()
                .map(entity -> convertResponseDTO(mapper.mapTo(entity)))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> checkRoomExists(String roomId) {
        Boolean check = availableRoomsRepository.existsAvailableRoomEntitiesByAvailableStudent_Room_RoomId(roomId);
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    public boolean checkRoomPresentInAvailableRoomsTable(String studentID) {
        return availableRoomsRepository.existsAvailableRoomEntitiesByAvailableStudent_StudentId(studentID);
    }

    public AvailableRoomsDetailsDTO registerRoomToBeAvailableForSwap(String studentId, String description) {
        StudentDTO student = studentService.findStudent(studentId);
        AvailableRoomsDTO build = AvailableRoomsDTO.builder()
                .studentId(studentId)
                .availableStudent(student)
                .description(description)
                .build();

        AvailableRoomEntity entity = mapper.mapFrom(build);
        AvailableRoomEntity savedEntity = availableRoomsRepository.save(entity);

        return convertResponseDTO(mapper.mapTo(savedEntity));


    }

    public List<AvailableRoomsDetailsDTO> fetchFilterRoomsByBuilding(String buildingName) {
        List<AvailableRoomEntity> filterRooms = availableRoomsRepository.
                findAvailableRoomEntitiesByAvailableStudent_Room_BuildingName(buildingName);
        System.out.println(filterRooms);
        return filterRooms.stream()
                .map(entity -> convertResponseDTO(mapper.mapTo(entity)))
                .collect(Collectors.toList());
    }

    public SwapRequestDTOOutput swapRequestRegister(String reqStudentID, String reqToStudentID) {
        boolean checkingAvailabilityOfRoomSwap = availableRoomsRepository
                .existsAvailableRoomEntitiesByAvailableStudent_StudentId(reqToStudentID);
        if (!checkingAvailabilityOfRoomSwap){
            throw new StudentNotFoundException(reqToStudentID);
        }

        Optional<StudentEntity> reqStudentEntity = studentRepository.findById(reqStudentID);
        Optional<StudentEntity> reqToStudentEntity = studentRepository.findById(reqToStudentID);
        if (reqStudentEntity.isEmpty() || reqToStudentEntity.isEmpty()) {
            throw new StudentNotFoundException(reqStudentID+" or "+reqStudentID);
        }


        SwapRequestDTO swapRequestDTO = registerSwapRequests.registerSwapRequests(reqStudentEntity.get(), reqToStudentEntity.get());
        System.out.println(swapRequestDTO);
        return registerSwapRequests.convertOutput(swapRequestDTO);
    }

    public void deleteAvailableRoomByID(String studentID) {
        availableRoomsRepository.deleteById(studentID);
    }

}
