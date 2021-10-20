package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.RoomCreationRequest;
import com.anhnc2.ehealicords.data.request.RoomTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.RoomDetailsResponse;
import com.anhnc2.ehealicords.data.response.RoomResponse;
import com.anhnc2.ehealicords.data.response.RoomTypeResponse;
import com.anhnc2.ehealicords.service.clinic.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/rooms")
@AllArgsConstructor
public class RoomApi {

    private final RoomService roomService;

    @PostMapping("/create-room-type")
    public HttpResponse<RoomTypeResponse> createRoomType(@RequestBody RoomTypeCreationRequest body) {
        RoomTypeResponse data = roomService.createRoomType(body);
        return HttpResponseImpl.<RoomTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(data)
                .build();
    }

    @GetMapping("/all-room-types-in-branch")
    public HttpResponse<List<RoomTypeResponse>> getAllRoomTypesInBranch() {
        List<RoomTypeResponse> result = roomService.getAllRoomTypesInBranch();
        return HttpResponseImpl.<List<RoomTypeResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room types of this branch.")
                .build();
    }

    @PutMapping("/update-room-type/{id}")
    public HttpResponse<RoomTypeResponse> updateRoomType(@PathVariable("id") Integer roomTypeId,
                                                         @RequestBody RoomTypeCreationRequest body) {
        RoomTypeResponse result = roomService.updateRoomType(roomTypeId, body);
        return HttpResponseImpl.<RoomTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .build();
    }

    @PostMapping("/create")
    public HttpResponse<RoomResponse> createRoom(@RequestBody RoomCreationRequest body) {
        RoomResponse result = roomService.createRoom(body);
        return HttpResponseImpl.<RoomResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .build();
    }

    @GetMapping("/all-in-branch")
    public HttpResponse<List<RoomDetailsResponse>> getAllRoomsInBranch() {
        List<RoomDetailsResponse> result = roomService.getAllRoomsInBranch();
        return HttpResponseImpl.<List<RoomDetailsResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room of this branch.")
                .build();
    }

    @PutMapping("/update/{id}")
    public HttpResponse<RoomResponse> updateRoom(@PathVariable("id") Integer roomId,
                                                 @RequestBody RoomCreationRequest body) {
        RoomResponse result = roomService.updateRoom(roomId, body);
        return HttpResponseImpl.<RoomResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .build();

    }

    @GetMapping("/all-in-branch/room-type/{tid}")
    public HttpResponse<List<RoomDetailsResponse>> getAllRoomsOfRoomType(@PathVariable("tid") Integer roomTypeId) {
        List<RoomDetailsResponse> result = roomService.getAllRoomsOfRoomType(roomTypeId);
        return HttpResponseImpl.<List<RoomDetailsResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room of this room type in this branch.")
                .build();
    }

//    @GetMapping("/all/paging")
//    public HttpResponse<PaginationResponse<List<Room>>> getAllRoomsInBranch(
//            @RequestParam("branchId") Integer branchId, @RequestParam("page") Integer page,
//            @RequestParam("pageSize") Integer pageSize) {
//        return HttpResponseImpl.success(
//                roomService.getAllRoomsInBranch(branchId, page, pageSize)
//        );
//    }
}
