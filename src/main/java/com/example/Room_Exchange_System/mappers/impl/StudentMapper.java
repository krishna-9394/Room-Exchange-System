package com.example.Room_Exchange_System.mappers.impl;
import com.example.Room_Exchange_System.domain.DTO.StudentDTO;
import com.example.Room_Exchange_System.domain.Entity.StudentEntity;
import com.example.Room_Exchange_System.mappers.Mapper;
import org.modelmapper.ModelMapper;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<StudentEntity, StudentDTO> {
    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDTO mapTo(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity,StudentDTO.class);
    }

    @Override
    public StudentEntity mapFrom(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO,StudentEntity.class);
    }
}
