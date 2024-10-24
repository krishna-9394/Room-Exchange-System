package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.DuplicateResourceException;
import com.example.Room_Exchange_System.ExceptionHandling.StudentNotFoundException;
import com.example.Room_Exchange_System.domain.DTO.*;
import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import com.example.Room_Exchange_System.mappers.impl.StudentMapper;
import com.example.Room_Exchange_System.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RoomService roomService;
    private final AuthService authService;
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, RoomService roomService, AuthService authService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.roomService = roomService;
        this.authService = authService;

    }

    public StudentDetailsDTO mapStudentDTOToOutput(StudentDTO studentDTO) {
        return StudentDetailsDTO.builder()
                .contactNumber(studentDTO.getContactNumber())
                .gender(studentDTO.getGender())
                .studentId(studentDTO.getStudentId())
                .email(studentDTO.getAuthorization().getEmail())
                .name(studentDTO.getName())
                .room(studentDTO.getRoom())
                .build();
    }

    public StudentDetailsDTO createStudent(RegisterStudentDTO registerStudentDTO) {
        String email = registerStudentDTO.getEmail();
        String roomId = registerStudentDTO.getRoomId();
        AuthorizationDTO authorizationDTO = authService.findUser(email);
        RoomDTO room = roomService.findRoom(roomId);

        StudentDTO studentDTO = StudentDTO.builder()
                .studentId(registerStudentDTO.getStudentId())
                .contactNumber(registerStudentDTO.getContactNumber())
                .gender(registerStudentDTO.getGender())
                .name(registerStudentDTO.getName())
                .authorization(authorizationDTO)
                .room(room)
                .build();
        StudentEntity student = studentMapper.mapFrom(studentDTO);
        StudentEntity savedEntity = studentRepository.save(student);
        return mapStudentDTOToOutput(studentMapper.mapTo(savedEntity));
    }

    public List<StudentDetailsDTO> fetchAllStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        return studentEntities.stream()
                .map(studentEntity -> mapStudentDTOToOutput(studentMapper.mapTo(studentEntity)))
                .collect(Collectors.toList());
    }

    public StudentDTO findStudent(String studentId) {
        Optional<StudentEntity> entity = studentRepository.findById(studentId);
        if (entity.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        return entity.stream()
                .map(studentEntity -> studentMapper.mapTo(studentEntity))
                .findAny().orElse(null);
    }

    public StudentDetailsDTO findStudentByStudentID(String studentId) {
        Optional<StudentEntity> entity = studentRepository.findById(studentId);
        if (entity.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        return entity.stream()
                .map(studentEntity -> mapStudentDTOToOutput(studentMapper.mapTo(studentEntity)))
                .findAny().orElse(null);
    }

    public StudentDetailsDTO newStudentRegisteration(StudentDTO studentDTO) throws InvalidPropertiesFormatException {
        boolean exists = studentRepository.existsById(studentDTO.getStudentId());
        if (exists) {
            throw new DuplicateResourceException(studentDTO.getStudentId()+" already exists");
        }
        try {
            StudentEntity entity = studentMapper.mapFrom(studentDTO);
            StudentEntity savedEntity = studentRepository.save(entity);
            return mapStudentDTOToOutput(studentMapper.mapTo(savedEntity));
        }catch (Exception e) {
            throw new InvalidPropertiesFormatException("Invalid Student Parameters");
        }
    }


    public void deleteStudentById(String studentId) {
        studentRepository.deleteById(studentId);
    }


}
