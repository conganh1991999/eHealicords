package com.anhnc2.ehealicords.service.clinic;

//import com.anhnc2.ehealicords.data.entity.RoomEntity;
//import com.anhnc2.ehealicords.data.response.PaginationResponse;
//import com.anhnc2.ehealicords.data.response.RoomDetailResponse;
//import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
//import com.anhnc2.ehealicords.repository.RoomRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class RoomServiceImpl implements RoomService {
//    private final RoomRepository roomRepository;
//    private final MedicalSpecialtyRepository medicalSpecialtyRepository;
//
//    @Override
//    public List<RoomEntity> getRoomAvailable(
//            LocalDate date, List<LocalTime> times, int branchId, int specialtyId) {
//        return roomRepository.getRoomAvailable(date, times, branchId, specialtyId);
//    }
//
//    @Override
//    public RoomEntity getRoomInfo(int id) {
//        return roomRepository.findById(id).orElseThrow();
//    }
//
//    @Override
//    public PaginationResponse<List<RoomDetailResponse>> getRoomsOfBranch(
//            int branchId, int page, int pageSize) {
//
//        var pageRequest = PageRequest.of(page, pageSize, Sort.by("medicalSpecialtyId", "id"));
//        var roomPage = roomRepository.findAllByBranchId(branchId, pageRequest);
//        var rooms =
//                roomPage.getContent().stream()
//                        .map(
//                                room ->
//                                        RoomDetailResponse.fromDAO(
//                                                room, getSpecialtyName(room.getMedicalSpecialtyId())))
//                        .collect(Collectors.toList());
//        var total = roomPage.getTotalElements();
//        return PaginationResponse.<List<RoomDetailResponse>>builder()
//                .page(page)
//                .pageSize(pageSize)
//                .total(total)
//                .totalPage((int) Math.ceil((double) total / pageSize))
//                .items(rooms)
//                .build();
//    }
//
//    @Override
//    public void updateRoom(RoomEntity roomEntity) {
//        var updateRoom = roomRepository.findById(roomEntity.getId()).orElseThrow();
//
//        updateRoom.setName(roomEntity.getName());
//        updateRoom.setMedicalSpecialtyId(roomEntity.getMedicalSpecialtyId());
//        updateRoom.setCapacity(roomEntity.getCapacity());
//        roomRepository.saveAndFlush(updateRoom);
//    }
//
//    @Override
//    public void createRoom(RoomEntity roomEntity) {
//        roomEntity.setId(null);
//
//        roomRepository.saveAndFlush(roomEntity);
//    }
//
//}