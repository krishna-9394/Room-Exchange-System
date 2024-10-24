package com.example.Room_Exchange_System.mappers.impl;

import com.example.Room_Exchange_System.domain.DTO.RoomDTO;
import com.example.Room_Exchange_System.domain.Entity.RoomEntity;
import com.example.Room_Exchange_System.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper implements Mapper<RoomEntity, RoomDTO> {
    private final ModelMapper modelMapper;

    public RoomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoomDTO mapTo(RoomEntity roomEntity) {
        return modelMapper.map(roomEntity,RoomDTO.class);
    }

    @Override
    public RoomEntity mapFrom(RoomDTO roomDTO) {
        return modelMapper.map(roomDTO,RoomEntity.class);
    }
}
