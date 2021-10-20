package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.RoomCreationRequest;
import com.anhnc2.ehealicords.data.request.RoomTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.RoomDetailsResponse;
import com.anhnc2.ehealicords.data.response.RoomResponse;
import com.anhnc2.ehealicords.data.response.RoomTypeResponse;

import java.util.List;

public interface RoomService {
    RoomTypeResponse createRoomType(RoomTypeCreationRequest roomType);

    List<RoomTypeResponse> getAllRoomTypesInBranch();

    RoomTypeResponse updateRoomType(Integer roomTypeId, RoomTypeCreationRequest roomType);

    RoomResponse createRoom(RoomCreationRequest room);

    List<RoomDetailsResponse> getAllRoomsInBranch();

    RoomResponse updateRoom(Integer roomId, RoomCreationRequest room);

    List<RoomDetailsResponse> getAllRoomsOfRoomType(Integer roomTypeId);

    // PaginationResponse<List<Room>> getAllRoomsInBranch(Integer branchId, Integer page, Integer pageSize);
}
