package com.roland.movietheater_jdbc.service.RoomService;

import com.roland.movietheater_jdbc.model.Room;

import java.util.List;

public interface IRoomService {

    List<Room> findAllRooms(int cinemaId);

    Room createRoomInBranch(Room room) throws FailedToInsertRoomInCinemaBranchException;

    int deleteRoomInBranch(int cinemaId, int roomId) throws FailedToDeleteRoomInCinemaBranchException;

    Room updateRoomInBranch(int cinemaId, int roomId, Room room) throws FailedToUpdateRoomInCinemaBranchException;
}
