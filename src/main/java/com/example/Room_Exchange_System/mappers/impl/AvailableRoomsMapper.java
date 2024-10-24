package com.example.Room_Exchange_System.mappers.impl;

import com.example.Room_Exchange_System.domain.DTO.AvailableRoomsDTO;
import com.example.Room_Exchange_System.domain.Entity.AvailableRoomEntity;
import com.example.Room_Exchange_System.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AvailableRoomsMapper implements Mapper<AvailableRoomEntity, AvailableRoomsDTO> {
    private final ModelMapper modelMapper;

    public AvailableRoomsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AvailableRoomsDTO mapTo(AvailableRoomEntity availableRoomEntity) {
        return modelMapper.map(availableRoomEntity,AvailableRoomsDTO.class);
    }

    @Override
    public AvailableRoomEntity mapFrom(AvailableRoomsDTO availableRoomsDTO) {
        return modelMapper.map(availableRoomsDTO,AvailableRoomEntity.class);
    }
}
