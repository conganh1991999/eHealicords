package com.anhnc2.ehealicords.api;

//import com.anhnc2.ehealicords.constant.StatusCode;
//import com.anhnc2.ehealicords.data.common.PresignResult;
//import com.anhnc2.ehealicords.data.common.User;
//import com.anhnc2.ehealicords.data.response.BalanceResult;
//import com.anhnc2.ehealicords.data.response.HttpResponse;
//import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
//import com.anhnc2.ehealicords.data.response.LiteUser;
//import com.anhnc2.ehealicords.data.response.LiteUserRelative;
//import com.anhnc2.ehealicords.data.response.PaginationResponse;
//import com.anhnc2.ehealicords.service.common.AppUserService;
//import com.anhnc2.ehealicords.service.user.UserService;
//import java.util.List;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/protected/user")
//@AllArgsConstructor
//public class UserApi {
//    private final UserService userService;
//    private final AppUserService appUserService;
//
//    @GetMapping("/all")
//    public HttpResponse<PaginationResponse<List<LiteUser>>> getPatientInfo(
//            @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
//        PaginationResponse<List<LiteUser>> result = userService.getAllUsers(page, pageSize);
//        return HttpResponseImpl.success(result);
//    }
//
//    @GetMapping("/{userId}")
//    public HttpResponse<User> getPatientInfoById(@PathVariable Long userId) {
//        return HttpResponseImpl.success(userService.getUserDetailById(userId));
//    }
//
//    @GetMapping("/{userId}/relatives")
//    public HttpResponse<List<LiteUserRelative>> getRelativePatientsByPatientId(
//            @PathVariable Long userId) {
//        return HttpResponseImpl.success(userService.getUserRelatives(userId));
//    }
//
//    @GetMapping
//    public HttpResponse<User> getPatientInfo() {
//        User result = userService.getCurrentPatientWithLocation();
//        return HttpResponseImpl.<User>builder().code(StatusCode.SUCCESS).data(result).build();
//    }
//
//    @GetMapping("/short")
//    public HttpResponse<User> getPatientShortInfo() {
//        User user = userService.getCurrentPatient();
//        return HttpResponseImpl.<User>builder().code(StatusCode.SUCCESS).data(user).build();
//    }
//
//    @GetMapping("/balance")
//    public HttpResponse<BalanceResult> getBalanceOfUser() {
//        BalanceResult result = userService.getBalanceOfUser(appUserService.getCurrentUserId());
//        return HttpResponseImpl.<BalanceResult>builder().code(StatusCode.SUCCESS).data(result).build();
//    }
//
//    @PutMapping
//    public HttpResponse<Object> updatePatientInfo(@RequestBody User userUpdateInfo) {
//        userService.updateInfo(appUserService.getCurrentUserId(), userUpdateInfo);
//        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
//    }
//
//    @PostMapping("/avatar/update/presign")
//    public HttpResponse<PresignResult> getAvatarPresign(@RequestParam("fileName") String fileName) {
//        PresignResult presignResult = userService.getAvatarUpdateUrl(fileName);
//        return HttpResponseImpl.<PresignResult>builder()
//                .code(StatusCode.SUCCESS)
//                .data(presignResult)
//                .build();
//    }
//
//    @PutMapping("/avatar/update")
//    public HttpResponse<Object> updateAvatar(@RequestParam("key") String key) {
//        userService.updateAvatar(key);
//        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
//    }
//}
