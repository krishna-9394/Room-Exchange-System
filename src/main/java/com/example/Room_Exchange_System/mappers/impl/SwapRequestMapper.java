package com.example.Room_Exchange_System.mappers.impl;

import com.example.Room_Exchange_System.domain.DTO.SwapRequestDTO;
import com.example.Room_Exchange_System.domain.Entity.SwapRequestEntity;
import com.example.Room_Exchange_System.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SwapRequestMapper implements Mapper<SwapRequestEntity, SwapRequestDTO> {
    private final ModelMapper modelMapper;

    public SwapRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SwapRequestDTO mapTo(SwapRequestEntity swapRequestEntity) {
        return modelMapper.map(swapRequestEntity,SwapRequestDTO.class);
    }

    @Override
    public SwapRequestEntity mapFrom(SwapRequestDTO swapRequestDTO) {
        return modelMapper.map(swapRequestDTO,SwapRequestEntity.class);
    }
}
