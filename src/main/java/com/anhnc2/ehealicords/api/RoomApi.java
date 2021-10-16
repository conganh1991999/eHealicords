package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.RoomEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.RoomDetailResponse;
import com.anhnc2.ehealicords.service.clinic.RoomService;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// TODO: clinic

@RestController
@RequestMapping("api/protected/rooms")
@AllArgsConstructor
public class RoomApi {
    private final RoomService roomService;
    private final AppUserService service;
    private final SpecialistService specialistService;

    @GetMapping("/available")
    public HttpResponse<List<RoomEntity>> getRoomAvailable(
            @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam("times") @DateTimeFormat(iso = ISO.TIME) List<LocalTime> times) {
        long staffId = service.getCurrentUserId();
        SpecialistEntity specialist = null; // specialistService.getByStaffId(staffId);

        List<RoomEntity> roomEntities =
                roomService.getRoomAvailable(
                        date, times, specialist.getBranchId(), specialist.getMedialSpecialtyId());
        return HttpResponseImpl.<List<RoomEntity>>builder()
                .code(StatusCode.SUCCESS)
                .message("Lists all room available in shift and date for specialistDAO")
                .data(roomEntities)
                .build();
    }

    @GetMapping("/specialists/{specialistId}/available")
    public HttpResponse<List<RoomEntity>> getRoomAvailableBySpecialisId(
            @PathVariable Long specialistId,
            @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam("times") @DateTimeFormat(iso = ISO.TIME) List<LocalTime> times) {
        SpecialistEntity specialist = null; // specialistService.getBySpecialistId(specialistId);

        List<RoomEntity> roomEntities =
                roomService.getRoomAvailable(
                        date, times, specialist.getBranchId(), specialist.getMedialSpecialtyId());
        return HttpResponseImpl.<List<RoomEntity>>builder()
                .code(StatusCode.SUCCESS)
                .message("Lists all room available in shift and date of specialist")
                .data(roomEntities)
                .build();
    }

    @GetMapping("/{id}")
    public HttpResponse<RoomEntity> getRoomInfo(@PathVariable int id) {
        return HttpResponseImpl.success(roomService.getRoomInfo(id));
    }

    @GetMapping("")
    public HttpResponse<PaginationResponse<List<RoomDetailResponse>>> getRoomOfBranch(
            @RequestParam("branchId") int branchId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {
        return HttpResponseImpl.success(roomService.getRoomsOfBranch(branchId, page, pageSize));
    }

    @PutMapping("/{id}")
    public HttpResponse<Object> updateRoom(
            @PathVariable("id") int roomId, @RequestBody RoomEntity roomEntity) {
        roomEntity.setId(roomId);
        roomService.updateRoom(roomEntity);
        return HttpResponseImpl.success("OK");
    }

    @PostMapping("")
    public HttpResponse<Object> createRoom(@RequestBody RoomEntity roomEntity) {
        roomService.createRoom(roomEntity);
        return HttpResponseImpl.success("OK");
    }
}
