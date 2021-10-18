package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.Room;
import com.anhnc2.ehealicords.data.common.RoomType;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.service.clinic.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/rooms")
@AllArgsConstructor
public class RoomApi {

    private final RoomService roomService;

    @PostMapping("/create-room-type")
    public HttpResponse<Object> createRoomType(@RequestBody RoomType body) {
        roomService.createRoomType(body);
        return HttpResponseImpl.success("OK");
    }

    @PutMapping("/update-room-type")
    public HttpResponse<Object> updateRoomType(@RequestBody RoomType body) {
        roomService.updateRoomType(body);
        return HttpResponseImpl.success("OK");
    }

    @GetMapping("/all-room-types/branch/{id}")
    public HttpResponse<List<RoomType>> getAllRoomTypesInBranch(@PathVariable("id") Integer branchId) {
        List<RoomType> result = roomService.getAllRoomTypesInBranch(branchId);
        return HttpResponseImpl.<List<RoomType>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room types of this branch.")
                .build();
    }

    @PostMapping("/create")
    public HttpResponse<Object> createRoom(@RequestBody Room body) {
        roomService.createRoom(body);
        return HttpResponseImpl.success("OK");
    }

    @PutMapping("/update")
    public HttpResponse<Object> updateRoom(@RequestBody Room body) {
        roomService.updateRoom(body);
        return HttpResponseImpl.success("OK");
    }

    @GetMapping("/all/branch/{id}")
    public HttpResponse<List<Room>> getAllRoomsInBranch(@PathVariable("id") Integer branchId) {
        List<Room> result = roomService.getAllRoomsInBranch(branchId);
        return HttpResponseImpl.<List<Room>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room of this branch.")
                .build();
    }

    @GetMapping("/all/paging")
    public HttpResponse<PaginationResponse<List<Room>>> getAllRoomsInBranch(
            @RequestParam("branchId") Integer branchId, @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return HttpResponseImpl.success(
                roomService.getAllRoomsInBranch(branchId, page, pageSize)
        );
    }

    @GetMapping("/all/branch/{bid}/room-type/{tid}")
    public HttpResponse<List<Room>> getAllRoomsOfRoomType(@PathVariable("bid") Integer branchId,
                                                          @PathVariable("tid") Integer roomTypeId) {
        List<Room> result = roomService.getAllRoomsOfRoomType(branchId, roomTypeId);
        return HttpResponseImpl.<List<Room>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All room of this room type in this branch.")
                .build();
    }

    @GetMapping("/{id}")
    public HttpResponse<Room> getRoomInformation(@PathVariable("id") Integer id) {
        return HttpResponseImpl.success(roomService.getRoomInformation(id));
    }

}
