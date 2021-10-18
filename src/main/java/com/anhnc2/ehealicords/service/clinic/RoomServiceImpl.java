package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.common.Room;
import com.anhnc2.ehealicords.data.common.RoomType;
import com.anhnc2.ehealicords.data.entity.RoomEntity;
import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.repository.RoomRepository;
import com.anhnc2.ehealicords.repository.RoomTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;

    @Override
    public void createRoomType(RoomType roomType) {
        roomTypeRepository.save(roomType.toEntity());
    }

    @Override
    public void updateRoomType(RoomType roomType) {
        RoomTypeEntity entity = roomTypeRepository.getById(roomType.getId());
        entity.setName(roomType.getName());
        entity.setDescription(roomType.getDescription());

        roomTypeRepository.save(entity);
    }

    @Override
    public List<RoomType> getAllRoomTypesInBranch(Integer branchId) {
        return roomTypeRepository.findAllByBranchId(branchId)
                .stream()
                .map(RoomType::new)
                .collect(Collectors.toList());
    }

    @Override
    public void createRoom(Room room) {
        roomRepository.save(room.toEntity());
    }

    @Override
    public void updateRoom(Room room) {
        RoomEntity entity = roomRepository.getById(room.getId());
        entity.setName(room.getName());
        entity.setDescription(room.getDescription());
        entity.setRoomTypeId(room.getRoomTypeId());

        roomRepository.save(entity);
    }

    @Override
    public List<Room> getAllRoomsInBranch(Integer branchId) {
        return roomRepository.findAllByBranchId(branchId)
                .stream()
                .map(Room::new)
                .collect(Collectors.toList());
    }

    @Override
    public PaginationResponse<List<Room>> getAllRoomsInBranch(Integer branchId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("roomTypeId", "id"));
        Page<RoomEntity> roomPage = roomRepository.findAllByBranchId(branchId, pageRequest);

        List<Room> rooms =
                roomPage.getContent().stream()
                        .map(Room::new)
                        .collect(Collectors.toList());

        long total = roomPage.getTotalElements();

        return PaginationResponse.<List<Room>>builder()
                .page(page)
                .pageSize(pageSize)
                .total(total)
                .totalPage((int) Math.ceil((double) total / pageSize))
                .items(rooms)
                .build();
    }

    @Override
    public List<Room> getAllRoomsOfRoomType(Integer branchId, Integer roomTypeId) {
        return roomRepository.findAllByRoomTypeIdAndBranchId(roomTypeId, branchId)
                .stream()
                .map(Room::new)
                .collect(Collectors.toList());
    }

    @Override
    public Room getRoomInformation(Integer id) {
        return new Room(roomRepository.getById(id));
    }
}
