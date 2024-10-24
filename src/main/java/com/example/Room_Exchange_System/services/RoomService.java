package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.RoomNotFoundException;
import com.example.Room_Exchange_System.domain.DTO.RoomDTO;
import com.example.Room_Exchange_System.domain.Entity.RoomEntity;
import com.example.Room_Exchange_System.mappers.impl.RoomMapper;
import com.example.Room_Exchange_System.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public List<RoomDTO> fetchAllRooms(){
        List<RoomEntity> rooms = roomRepository.findAll();
        return rooms.stream().map(roomEntity -> roomMapper.mapTo(roomEntity))
                .collect(Collectors.toList());
    }

    public RoomDTO findRoom(String roomId){
        Optional<RoomEntity> fetchedRoom = roomRepository.findById(roomId);
        if (fetchedRoom.isEmpty()){
            throw new RoomNotFoundException(roomId+" does not exist");
        }
        return fetchedRoom.stream()
                .findFirst()
                .map(roomEntity -> roomMapper.mapTo(roomEntity))
                .orElse(null);
    }

    public void deleteRoom(String roomId) {
        boolean check  = checkRoomExistence(roomId);
        if (!check){
            throw new RoomNotFoundException(roomId+" does not exist");
        }
        roomRepository.deleteById(roomId);
    }

    public boolean checkRoomExistence(String roomId) {
        return roomRepository.existsById(roomId);
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        roomDTO.setRoomId(roomDTO.getBuildingName()
                +roomDTO.getFloorNo()
                +roomDTO.getWing()
                +roomDTO.getRoomNumber());

        RoomEntity roomEntity = roomMapper.mapFrom(roomDTO);
        System.out.println(roomEntity);
        RoomEntity savedEntity = roomRepository.save(roomEntity);
        return roomMapper.mapTo(savedEntity);
    }
}
