package com.example.Room_Exchange_System.controllers;

import com.example.Room_Exchange_System.domain.DTO.RegisterStudentDTO;
import com.example.Room_Exchange_System.domain.DTO.StudentDTO;
import com.example.Room_Exchange_System.domain.DTO.StudentDetailsDTO;
import com.example.Room_Exchange_System.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<StudentDetailsDTO> fetchAllStudents(){
        return studentService.fetchAllStudents();
    }

    @PostMapping("/students")
    public StudentDetailsDTO registerStudent(@RequestBody RegisterStudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PostMapping("/students/registerWhole")
    public StudentDetailsDTO registerStudent(@RequestBody StudentDTO studentDTO) throws InvalidPropertiesFormatException{
        return studentService.newStudentRegisteration(studentDTO);
    }

    @GetMapping("/students/{studentId}")
    public StudentDetailsDTO getStudent(@PathVariable String studentId) {
        return studentService.findStudentByStudentID(studentId);
    }

}
