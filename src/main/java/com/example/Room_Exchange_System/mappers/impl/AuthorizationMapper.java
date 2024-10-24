package com.example.Room_Exchange_System.mappers.impl;

import com.example.Room_Exchange_System.domain.DTO.AuthorizationDTO;
import com.example.Room_Exchange_System.domain.Entity.AuthorizationEntity;
import com.example.Room_Exchange_System.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationMapper implements Mapper<AuthorizationEntity, AuthorizationDTO> {

    private ModelMapper modelMapper;
    AuthorizationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorizationDTO mapTo(AuthorizationEntity authorizationEntity) {
        return modelMapper.map(authorizationEntity,AuthorizationDTO.class);
    }

    @Override
    public AuthorizationEntity mapFrom(AuthorizationDTO authorizationDTO) {
        return modelMapper.map(authorizationDTO,AuthorizationEntity.class);
    }
}
