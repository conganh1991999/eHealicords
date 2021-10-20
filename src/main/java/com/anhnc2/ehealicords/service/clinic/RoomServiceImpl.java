package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import com.anhnc2.ehealicords.data.request.RoomCreationRequest;
import com.anhnc2.ehealicords.data.request.RoomTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.RoomDetailsResponse;
import com.anhnc2.ehealicords.data.response.RoomResponse;
import com.anhnc2.ehealicords.data.response.RoomTypeResponse;
import com.anhnc2.ehealicords.repository.RoomRepository;
import com.anhnc2.ehealicords.repository.RoomTypeRepository;
import com.anhnc2.ehealicords.service.staff.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final StaffService staffService;

    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;

    @Override
    public RoomTypeResponse createRoomType(RoomTypeCreationRequest roomType) {
        RoomTypeEntity newRoomType = roomType.toEntity();

        newRoomType.setBranchId(staffService.getCurrentStaff().getBranchEntity().getId());

        return new RoomTypeResponse(roomTypeRepository.save(newRoomType));
    }

    @Override
    public List<RoomTypeResponse> getAllRoomTypesInBranch() {
        return roomTypeRepository.findAllByBranchId(staffService.getCurrentStaff().getBranchEntity().getId())
                .stream()
                .map(RoomTypeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public RoomTypeResponse updateRoomType(Integer roomTypeId, RoomTypeCreationRequest roomType) {
        RoomTypeEntity entity = roomTypeRepository.getById(roomTypeId);
        entity.setName(roomType.getName());
        entity.setDescription(roomType.getDescription());

        return new RoomTypeResponse(roomTypeRepository.save(entity));
    }

    @Override
    public RoomResponse createRoom(RoomCreationRequest room) {
        RoomEntity newRoom = room.toEntity();

        newRoom.setBranchId(staffService.getCurrentStaff().getBranchEntity().getId());

        return new RoomResponse(roomRepository.save(newRoom));
    }

    @Override
    public List<RoomDetailsResponse> getAllRoomsInBranch() {
        return roomRepository.findAllByBranchId(staffService.getCurrentStaff().getBranchEntity().getId())
                .stream()
                .map(RoomDetailsResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse updateRoom(Integer roomId, RoomCreationRequest room) {
        RoomEntity entity = roomRepository.getById(roomId);
        entity.setName(room.getName());
        entity.setDescription(room.getDescription());
        entity.setRoomTypeId(room.getRoomTypeId());

        return new RoomResponse(roomRepository.save(entity));
    }

    @Override
    public List<RoomDetailsResponse> getAllRoomsOfRoomType(Integer roomTypeId) {
        return roomRepository
                .findAllByRoomTypeIdAndBranchId(roomTypeId, staffService.getCurrentStaff().getBranchEntity().getId())
                .stream()
                .map(RoomDetailsResponse::new)
                .collect(Collectors.toList());
    }

//    @Override
//    public PaginationResponse<List<Room>> getAllRoomsInBranch(Integer branchId, Integer page, Integer pageSize) {
//        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("roomTypeId", "id"));
//        Page<RoomEntity> roomPage = roomRepository.findAllByBranchId(branchId, pageRequest);
//
//        List<Room> rooms =
//                roomPage.getContent().stream()
//                        .map(Room::new)
//                        .collect(Collectors.toList());
//
//        long total = roomPage.getTotalElements();
//
//        return PaginationResponse.<List<Room>>builder()
//                .page(page)
//                .pageSize(pageSize)
//                .total(total)
//                .totalPage((int) Math.ceil((double) total / pageSize))
//                .items(rooms)
//                .build();
//    }
}
