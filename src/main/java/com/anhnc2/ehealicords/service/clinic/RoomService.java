package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.common.Room;
import com.anhnc2.ehealicords.data.common.RoomType;
import com.anhnc2.ehealicords.data.response.PaginationResponse;

import java.util.List;

public interface RoomService {
    void createRoomType(RoomType roomType);

    void updateRoomType(RoomType roomType);

    List<RoomType> getAllRoomTypesInBranch(Integer branchId);

    void createRoom(Room room);

    void updateRoom(Room room);

    List<Room> getAllRoomsInBranch(Integer branchId);

    PaginationResponse<List<Room>> getAllRoomsInBranch(Integer branchId, Integer page, Integer pageSize);

    List<Room> getAllRoomsOfRoomType(Integer branchId, Integer roomTypeId);

    Room getRoomInformation(Integer id);
}
